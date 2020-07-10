package com.kuang;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriteTest {

    String PATH = "D:\\狂神说Java\\【狂神】小专题\\POI-EasyExcel\\代码\\Bilibili-狂神说java\\kuang-poi\\";

    @Test
    public void testWrite03() throws Exception {
        // 1、创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        // 2、创建一个工作表
        Sheet sheet = workbook.createSheet("狂神观众统计表");
        // 3、创建一个行  （1,1）
        Row row1 = sheet.createRow(0);
        // 4、创建一个单元格
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        // 第二行 (2,1)
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        // (2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        // 生成一张表（IO 流）  03 版本就是使用 xls结尾！
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "狂神观众统计表03.xls");
        // 输出
        workbook.write(fileOutputStream);
        // 关闭流
        fileOutputStream.close();

        System.out.println("狂神观众统计表03 生成完毕！");

    }

    @Test
    public void testWrite07() throws Exception {
        // 1、创建一个工作簿 07
        Workbook workbook = new XSSFWorkbook();
        // 2、创建一个工作表
        Sheet sheet = workbook.createSheet("狂神观众统计表");
        // 3、创建一个行  （1,1）
        Row row1 = sheet.createRow(0);
        // 4、创建一个单元格
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        // 第二行 (2,1)
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        // (2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        // 生成一张表（IO 流）  03 版本就是使用 xlsx结尾！
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "狂神观众统计表07.xlsx");
        // 输出
        workbook.write(fileOutputStream);
        // 关闭流
        fileOutputStream.close();

        System.out.println("狂神观众统计表03 生成完毕！");

    }

    @Test
    public void testWrite03BigData() throws IOException {
        // 时间
        long begin = System.currentTimeMillis();

        // 创建一个薄
        Workbook workbook = new HSSFWorkbook();
        // 创建表
        Sheet sheet = workbook.createSheet();
        // 写入数据
        for (int rowNum = 0; rowNum < 65537; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10 ; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream outputStream = new FileOutputStream(PATH + "testWrite03BigData.xls");
        workbook.write(outputStream);
        outputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) (end-begin)/1000);
    }

    // 耗时较长！ 优化，缓存
    @Test
    public void testWrite07BigData() throws IOException {
        // 时间
        long begin = System.currentTimeMillis();

        // 创建一个薄
        Workbook workbook = new XSSFWorkbook();
        // 创建表
        Sheet sheet = workbook.createSheet();
        // 写入数据
        for (int rowNum = 0; rowNum < 100000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10 ; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream outputStream = new FileOutputStream(PATH + "testWrite07BigData.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) (end-begin)/1000);
    }

    @Test
    public void testWrite07BigDataS() throws IOException {
        // 时间
        long begin = System.currentTimeMillis();

        // 创建一个薄
        Workbook workbook = new SXSSFWorkbook();
        // 创建表
        Sheet sheet = workbook.createSheet();
        // 写入数据
        for (int rowNum = 0; rowNum < 100000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10 ; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");
        FileOutputStream outputStream = new FileOutputStream(PATH + "testWrite07BigDataS.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        // 清除临时文件！
        ((SXSSFWorkbook) workbook).dispose();
        long end = System.currentTimeMillis();
        System.out.println((double) (end-begin)/1000);
    }


}
