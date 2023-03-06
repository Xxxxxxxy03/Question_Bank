package com.offcn.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.common.utils.PageUtils;
import com.offcn.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员-会员表
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:52:31
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

