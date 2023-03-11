package com.offcn.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.common.utils.PageUtils;
import com.offcn.question.entity.TypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 题目-题目类型表
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 16:08:05
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //获取全部分类
    List<TypeEntity> findAll();
}

