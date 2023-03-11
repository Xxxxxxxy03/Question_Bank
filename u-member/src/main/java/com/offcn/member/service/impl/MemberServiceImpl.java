package com.offcn.member.service.impl;

import com.offcn.common.utils.R;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.common.utils.PageUtils;
import com.offcn.common.utils.Query;

import com.offcn.member.dao.MemberDao;
import com.offcn.member.entity.MemberEntity;
import com.offcn.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> countByDateTime(String beginTime, String endTime) {

        //创建查询条件封装对象及封装条件
        QueryWrapper<MemberEntity> qw = new QueryWrapper<MemberEntity>().select("DATE_FORMAT(create_time,'%Y-%m-%d') AS NAME ,COUNT(id) AS VALUE").between("create_time", beginTime + " 00:00:00", endTime + " 23:59:59").groupBy("NAME");
        //按照日期时间范围进行过滤
        return this.baseMapper.selectMaps(qw);
    }

    //登录方法
    @Override
    public MemberEntity login(String username, String password) {
        //创建查询对象并封装查询条件
        MemberEntity memberEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("user_name", username).eq("PASSWORD", password));
        return memberEntity;

    }

}