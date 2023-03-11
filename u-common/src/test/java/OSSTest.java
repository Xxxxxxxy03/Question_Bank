import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OSSTest {

    /**
     * OSS 使用步骤 阿里云
     * 1）、引入SDK
     * 2）、配置好相应的属性
     */
    public static void main(String[] args)throws IOException {
        // 配置Endpoint
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 账号
        String accessKeyId = "LTAI5tL2SfXwaHL7UB5mt4et";
        //密码

        String accessKeySecret = "dRhRpb3X9xuZ0vQOSuph7CbntYKTuo";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = new FileInputStream(new File("E:\\background\\test.jpg"));
//要存放的存储桶名称，存储文件名 ，文件输入流
        ossClient.putObject("xy-2023", "pic/008.jpg", inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("测试完成");
    }
}