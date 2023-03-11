package com.offcn.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.common.utils.PageUtils;
import com.offcn.question.entity.QuestionEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    /**
     * 导入方法
     * @param file 是springboot封装上传文件对象
     * @return
     */
    Map importExcel(MultipartFile file);

    //导出方法
    Workbook exportExcel();

    //统计分类题目数量
    List<Map<String,Object>> countTypeQuestion();
}

