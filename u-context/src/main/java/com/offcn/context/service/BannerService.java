package com.offcn.context.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.common.utils.PageUtils;
import com.offcn.context.entity.BannerEntity;

import java.util.Map;

/**
 * 内容-横幅广告表
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:38:40
 */
public interface BannerService extends IService<BannerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

