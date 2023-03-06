package com.offcn.question.dao;

import com.offcn.question.entity.TypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目-题目类型表
 * 
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 16:08:05
 */
@Mapper
public interface TypeDao extends BaseMapper<TypeEntity> {
	
}
