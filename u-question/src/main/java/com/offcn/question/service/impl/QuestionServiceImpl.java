package com.offcn.question.service.impl;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.common.utils.PageUtils;
import com.offcn.common.utils.Query;

import com.offcn.question.dao.QuestionDao;
import com.offcn.question.entity.QuestionEntity;
import com.offcn.question.service.QuestionService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询关键字
        String key = (String) params.get("key");

        //获取前端传入分类编号
        String typeid = (String) params.get("typeid");
        //创建查询条件对象
        QueryWrapper<QuestionEntity> qw = new QueryWrapper<>();

        //判断查询关键字是否为空
        if (!StringUtils.isEmpty(key)) {
            //设置查询条件
            qw.eq("id", key).or().like("title", key);
        }
        //判断typeid是否为空
        if(typeid != null){
            //设置查询条件，按照分类编号
            qw.eq("type",typeid);
        }

        IPage<QuestionEntity> page = this.page(
                new Query<QuestionEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

    /**
     * 导入方法
     *
     * @param file 是springboot封装上传文件对象
     * @return
     */
    @Override
    public Map importExcel(MultipartFile file) {

        //创建map返回封装数据
        HashMap result = new HashMap<>();
        //获取上传文件的原始名称
        String filename = file.getOriginalFilename();
        //获取文件扩展名
        String extName = filename.substring(0, filename.lastIndexOf(".") + 1);
        //判断文件扩展名是否是excel的  xls xlsx
        if(! extName.equals("xls") && extName.equals("xlsx")){
            result.put("result",false);
            result.put("msg","文件扩展名不正确，导入失败");
            result.put("num",0);//如果导入成功，数据条数
            return result;
        }

        try {
            //扩展名正确，创建工作簿对象
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            //获取工作表  导入模板要求工作表名称固定
            Sheet sheet = workbook.getSheetAt(0);
            //获取行
            int rowNum = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowNum; i++) {

                //读取每行内容
                Row row = sheet.getRow(i);

                //创建题目实体对象
                QuestionEntity questionEntity = new QuestionEntity();
                //获取每行里面各个单元格
                String title = row.getCell(0).getStringCellValue();

                questionEntity.setTitle(title);
                questionEntity.setAnswer(row.getCell(1).getStringCellValue());
                questionEntity.setLevel((int)row.getCell(2).getNumericCellValue());
                questionEntity.setDisplayOrder((int)row.getCell(3).getNumericCellValue());
                questionEntity.setSubTitle(row.getCell(4).getStringCellValue());
                questionEntity.setType((long)row.getCell(5).getNumericCellValue());
                questionEntity.setEnable((int)row.getCell(6).getNumericCellValue());
                this.save(questionEntity);
            }

            //准备响应数据
            result.put("result",true);
            result.put("msg","导入成功");
            result.put("num",rowNum -1);//如果导入成功，数据条数
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            result.put("result",false);
            result.put("msg","获取文件上传失败");
            result.put("num",0);//如果导入成功，数据条数
            return result;
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            result.put("result",false);
            result.put("msg","导入文件数据失败");
            result.put("num",0);//如果导入成功，数据条数
            return result;
        }
    }

    //导出
    @Override
    public Workbook exportExcel() {
        //创建工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作表
        HSSFSheet sheet = workbook.createSheet("导出数据工作表");

        HSSFRow row = sheet.createRow(0);

        //设置第一行标题单元格
        row.createCell(0).setCellValue("题目标题");
        row.createCell(1).setCellValue("题目解答");
        row.createCell(2).setCellValue("题目难度等级");
        row.createCell(3).setCellValue("排序");
        row.createCell(4).setCellValue("副标题");
        row.createCell(5).setCellValue("题目类型");
        row.createCell(6).setCellValue("是否显示");

        //读取全部题目数据
        List<QuestionEntity> list = this.list();
        int rowNum=1;
        //循环遍历题目数据集合
        for (QuestionEntity qs : list) {
            //在工作表上创建行
            HSSFRow row1 = sheet.createRow(rowNum);

            //设置每行数据内容
            row1.createCell(0).setCellValue(qs.getTitle());
            row1.createCell(1).setCellValue(qs.getAnswer());
            row1.createCell(2).setCellValue(qs.getLevel());
            row1.createCell(3).setCellValue(qs.getDisplayOrder());
            row1.createCell(4).setCellValue(qs.getSubTitle());
            row1.createCell(5).setCellValue(qs.getType());
            row1.createCell(6).setCellValue(qs.getEnable());

            //把行号累加1
            rowNum++;
        }
        return workbook;
    }

    //统计分类题目数量
    @Override
    public List<Map<String, Object>> countTypeQuestion() {
        //创建封装查询对象及封装查询条件
        QueryWrapper<QuestionEntity> qw = new QueryWrapper<QuestionEntity>().select("TYPE,count(TYPE) as num").groupBy("TYPE");

        //查询
        List<Map<String, Object>> mapList = questionDao.selectMaps(qw);
        return mapList;
    }
}