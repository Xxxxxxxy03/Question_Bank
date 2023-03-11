package com.offcn;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestExcel {

    //使用poi创建生成excel 老版本
    @Test
    public void testCreateOldExcel() throws Exception {

        //创建新的Excel 工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建新的工作表
        Sheet sheet = workbook.createSheet("Sheet1");

        //在工作表上创建行  0 第一行
        Row row = sheet.createRow(0);

        //在行对象上创建单元格
        Cell cell = row.createCell(0);

        //操作单元格写入内容
        cell.setCellValue("Hello java0802");

        //把工作簿对象转存到磁盘，存储为一个excel文件
        //创建一个输出流
        FileOutputStream outputStream = new FileOutputStream("E:\\excel\\demo1.xls");

        workbook.write(outputStream);

        outputStream.close();

    }

    @Test
    public void testReadExcel() throws Exception {
        FileInputStream inputStream = new FileInputStream("E:\\excel\\demo1.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        //获取指定单元格
        HSSFCell cell = row.getCell(0);
        //获取内容
        String value = cell.getStringCellValue();
        System.out.println("读取到excel文件内容："+value);
        inputStream.close();
    }
}
