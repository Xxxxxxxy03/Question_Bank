package com.offcn.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.common.utils.PageUtils;
import com.offcn.question.entity.QuestionEntity;

import java.util.Map;

/**
 * 
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 16:08:05
 */
public interface QuestionService extends IService<QuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

