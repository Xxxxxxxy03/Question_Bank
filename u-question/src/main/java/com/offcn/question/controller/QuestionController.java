package com.offcn.question.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.offcn.question.entity.TypeEntity;
import com.offcn.question.service.TypeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.offcn.question.entity.QuestionEntity;
import com.offcn.question.service.QuestionService;
import com.offcn.common.utils.PageUtils;
import com.offcn.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 16:08:05
 */
@RestController
@RequestMapping("question/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private TypeService typeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //("question:question:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = questionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("question:question:info")
    public R info(@PathVariable("id") Long id){
		QuestionEntity question = questionService.getById(id);

        return R.ok().put("question", question);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("question:question:save")
    public R save(@RequestBody QuestionEntity question){
		questionService.save(question);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("question:question:update")
    public R update(@RequestBody QuestionEntity question){
		questionService.updateById(question);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("question:question:delete")
    public R delete(@RequestBody Long[] ids){
		questionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    //导入调用方法
    @PostMapping("/upload")
    public R upload(MultipartFile file){
        Map result = questionService.importExcel(file);
        return R.ok().put("result",result.get("result")).put("msg",result.get("msg")).put("num",result.get("num"));
    }

    //导出调用方法
    @RequestMapping("/export")
    public void export(HttpServletResponse response){
        //调用服务  获取工作簿对象
        Workbook workbook = questionService.exportExcel();
        if(workbook != null){
            //生成一个excel文件名字
            String filename = "uxue_"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xls";

            //设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            //设置响应文档类型  以流的格式响应
            response.setContentType("application/octet-stream;charset=UTF-8");

            try {
                //获取响应对象，输出流
                ServletOutputStream outputStream = response.getOutputStream();
                //把工作簿对象，写入到输出流
                workbook.write(outputStream);
                //刷新流
                outputStream.flush();
                //关闭流
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                response.getWriter().print("download error");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获取按照题库分类的统计数据
    @RequestMapping("/countTypeQuestion")
    public R countTypeQuestion(){
        List<Map<String, Object>> mapList = questionService.countTypeQuestion();
        //遍历
        for (Map<String, Object> map : mapList) {

            //按照分类id，读取对应的分类数据
            Long typeId = (Long) map.get("TYPE");

            TypeEntity typeEntity = typeService.getById(typeId);
            //判断是否为空
            if(typeEntity != null){
                //封装分类名称到map
                map.put("name",typeEntity.getType());
            }
        }
        return R.ok().put("mapList",mapList);
    }

    @PostMapping("/test1")
    public R test001(@RequestBody TypeEntity typeEntity, HttpServletRequest request){
        String myhead = request.getHeader("myhead");
        System.out.println(myhead);
        return R.ok().put("id",typeEntity.getId()).put("name",typeEntity.getType());
    }

}
