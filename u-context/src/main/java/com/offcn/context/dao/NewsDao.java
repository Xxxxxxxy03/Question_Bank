package com.offcn.context.dao;

import com.offcn.context.entity.NewsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容-资讯表
 * 
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:38:40
 */
@Mapper
public interface NewsDao extends BaseMapper<NewsEntity> {
	
}
