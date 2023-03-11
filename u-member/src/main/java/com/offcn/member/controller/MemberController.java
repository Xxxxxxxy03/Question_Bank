package com.offcn.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.offcn.common.utils.JWTUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.offcn.member.entity.MemberEntity;
import com.offcn.member.service.MemberService;
import com.offcn.common.utils.PageUtils;
import com.offcn.common.utils.R;


/**
 * 会员-会员表
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:52:31
 */
@RestController
@RequestMapping("member/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    //读取指定时间范围内注册用户统计数据
    @RequestMapping("/countAccountCreate")
    public R countAccountCreate(String beginTime, String endTime) {
        List<Map<String, Object>> mapList = memberService.countByDateTime(beginTime, endTime);
        return R.ok().put("mapList", mapList);
    }

    //登录
    @PostMapping("/login")
    public R login(String username, String password) {
        MemberEntity memberEntity = memberService.login(username, password);

        //判断是否为空
        if (memberEntity != null) {
            String token = JWTUtil.generateToken(memberEntity.getUserName());

            //生成refreshToken
            String refreshToken = UUID.randomUUID().toString().replace("-", "");
            stringRedisTemplate.opsForHash().put(refreshToken, "token", token);
            stringRedisTemplate.opsForHash().put(refreshToken, "username", username);

            //设置token过期时间
            stringRedisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

            return R.ok().put("token",token).put("refreshToken", refreshToken);
        } else {
            return R.error("账号密码错误");
        }
    }


    //刷新token
    @PostMapping("/refreshtoken")
    public R refreshToken(String refreshToken) {
        //根据refreshToken从redis获取所属用户名
        String username = (String) stringRedisTemplate.boundHashOps(refreshToken).get("username");

        //判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            return R.error("刷新token失败");
        }

        //根据用户名生成新的token
        String token = JWTUtil.generateToken(username);

        //更新到redis
        stringRedisTemplate.boundHashOps(refreshToken).put("token", token);

        //设置token过期时间
        stringRedisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return R.ok("刷新令牌成功").put("token", token).put("refreshToken", refreshToken);
    }


}
