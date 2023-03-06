package com.offcn.member.dao;

import com.offcn.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员-会员表
 * 
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:52:31
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
