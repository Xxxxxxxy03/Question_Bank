package com.offcn.uoss.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/thirdparty/v1/admin/oss")
public class OssController {

    //注入连接到阿里云存储的客户端对象
    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;//账号

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;//oss接入端点服务器地址

    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;//存储桶名称

    //获取oss存储服务器上传签名
    @GetMapping("/getPolicy")
    public Map<String, String> getPolicy() {

        //创建host名称
        String host = "https://" + bucket + "." + endpoint;
        //使用当前日期时间对象生成目录
        String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = formatDate + "/";

        Map<String, String> respMap = new LinkedHashMap<String, String>();

        try {
            long expireTime = 30;//签名有限制,单位是秒
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            //签名参数对象
            PolicyConditions policyConds = new PolicyConditions();
            //改签名允许上传文件的最大值，单位为字节
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);

            //签名允许操作目录
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            //调用oss客户端对象发出获取签名请求
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            //把明文签名转换成字节数组
            byte[] binaryData = postPolicy.getBytes("utf-8");
            //把字节数组做base64编码
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            //把base64编码后签名重新向oss服务器发出一个请求，获取签证
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            //封装accessId、签名等信息返回
            respMap.put("accessId", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return respMap;
    }
}
