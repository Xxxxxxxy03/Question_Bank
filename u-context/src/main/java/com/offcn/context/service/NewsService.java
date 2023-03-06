package com.offcn.context.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.common.utils.PageUtils;
import com.offcn.context.entity.NewsEntity;

import java.util.Map;

/**
 * 内容-资讯表
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:38:40
 */
public interface NewsService extends IService<NewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

