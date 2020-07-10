package com.kuang;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

public class ExcelReadTest {

    String PATH = "D:\\狂神说Java\\【狂神】小专题\\POI-EasyExcel\\代码\\Bilibili-狂神说java\\kuang-poi\\";

    @Test
    public void testRead03() throws Exception {

        // 获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "kuang-poi狂神观众统计表03.xls");

        // 1、创建一个工作簿。 使用excel能操作的这边他都可以操作！
        Workbook workbook = new HSSFWorkbook(inputStream);
        // 2、得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3、得到行
        Row row = sheet.getRow(0);
        // 4、得到列
        Cell cell = row.getCell(1);

        // 读取值的时候，一定需要注意类型！
        // getStringCellValue 字符串类型
//        System.out.println(cell.getStringCellValue());
        System.out.println(cell.getNumericCellValue());
        inputStream.close();
    }

    @Test
    public void testRead07() throws Exception {

        // 获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "kuang-poi狂神观众统计表07.xlsx");

        // 1、创建一个工作簿。 使用excel能操作的这边他都可以操作！
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 2、得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3、得到行
        Row row = sheet.getRow(0);
        // 4、得到列
        Cell cell = row.getCell(1);

        // 读取值的时候，一定需要注意类型！
        // getStringCellValue 字符串类型
//        System.out.println(cell.getStringCellValue());
        System.out.println(cell.getNumericCellValue());
        inputStream.close();
    }

    @Test
    public void testCellType() throws Exception {
        // 获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "明细表.xls");

        // 创建一个工作簿。 使用excel能操作的这边他都可以操作！
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        // 获取标题内容
        Row rowTitle = sheet.getRow(0);
        if (rowTitle!=null) {
            // 一定要掌握
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitle.getCell(cellNum);
                if (cell!=null){
                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + " | ");
                }
            }
            System.out.println();
        }

        // 获取表中的内容
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount ; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData!=null){
                // 读取列
                int cellCount = rowTitle.getPhysicalNumberOfCells();
                for (int cellNum = 0; cellNum < cellCount ; cellNum++) {
                    System.out.print("[" +(rowNum+1) + "-" + (cellNum+1) + "]");

                    Cell cell = rowData.getCell(cellNum);
                    // 匹配列的数据类型
                    if (cell!=null) {
                        int cellType = cell.getCellType();
                        String cellValue = "";

                        switch (cellType) {
                            case HSSFCell.CELL_TYPE_STRING: // 字符串
                                System.out.print("【String】");
                                cellValue = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔
                                System.out.print("【BOOLEAN】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK: // 空
                                System.out.print("【BLANK】");
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC: // 数字（日期、普通数字）
                                System.out.print("【NUMERIC】");
                                if (HSSFDateUtil.isCellDateFormatted(cell)){ // 日期
                                    System.out.print("【日期】");
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
                                }else {
                                    // 不是日期格式，防止数字过长！
                                    System.out.print("【转换为字符串输出】");
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                System.out.print("【数据类型错误】");
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }
        inputStream.close();
    }

//    @EasyTest
//    public void testCellType(FileInputStream inputStream) throws Exception {
//        // 获取文件流
//
//        // 创建一个工作簿。 使用excel能操作的这边他都可以操作！
//        Workbook workbook = new HSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//        // 获取标题内容
//        Row rowTitle = sheet.getRow(0);
//        if (rowTitle!=null) {
//            // 一定要掌握
//            int cellCount = rowTitle.getPhysicalNumberOfCells();
//            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
//                Cell cell = rowTitle.getCell(cellNum);
//                if (cell!=null){
//                    int cellType = cell.getCellType();
//                    String cellValue = cell.getStringCellValue();
//                    System.out.print(cellValue + " | ");
//                }
//            }
//            System.out.println();
//        }
//
//        // 获取表中的内容
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        for (int rowNum = 1; rowNum < rowCount ; rowNum++) {
//            Row rowData = sheet.getRow(rowNum);
//            if (rowData!=null){
//                // 读取列
//                int cellCount = rowTitle.getPhysicalNumberOfCells();
//                for (int cellNum = 0; cellNum < cellCount ; cellNum++) {
//                    System.out.print("[" +(rowNum+1) + "-" + (cellNum+1) + "]");
//
//                    Cell cell = rowData.getCell(cellNum);
//                    // 匹配列的数据类型
//                    if (cell!=null) {
//                        int cellType = cell.getCellType();
//                        String cellValue = "";
//
//                        switch (cellType) {
//                            case HSSFCell.CELL_TYPE_STRING: // 字符串
//                                System.out.print("【String】");
//                                cellValue = cell.getStringCellValue();
//                                break;
//                            case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔
//                                System.out.print("【BOOLEAN】");
//                                cellValue = String.valueOf(cell.getBooleanCellValue());
//                                break;
//                            case HSSFCell.CELL_TYPE_BLANK: // 空
//                                System.out.print("【BLANK】");
//                                break;
//                            case HSSFCell.CELL_TYPE_NUMERIC: // 数字（日期、普通数字）
//                                System.out.print("【NUMERIC】");
//                                if (HSSFDateUtil.isCellDateFormatted(cell)){ // 日期
//                                    System.out.print("【日期】");
//                                    Date date = cell.getDateCellValue();
//                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
//                                }else {
//                                    // 不是日期格式，防止数字过长！
//                                    System.out.print("【转换为字符串输出】");
//                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                                    cellValue = cell.toString();
//                                }
//                                break;
//                            case HSSFCell.CELL_TYPE_ERROR:
//                                System.out.print("【数据类型错误】");
//                                break;
//                        }
//                        System.out.println(cellValue);
//                    }
//                }
//            }
//        }
//        inputStream.close();
//    }

    @Test
    public void testFormula() throws Exception {
        FileInputStream inputStream = new FileInputStream(PATH + "公式.xls");
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(4);
        Cell cell = row.getCell(0);

        // 拿到计算公司 eval
        FormulaEvaluator FormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook)workbook);

        // 输出单元格的内容
        int cellType = cell.getCellType();
        switch (cellType){
            case Cell.CELL_TYPE_FORMULA: // 公式
                String formula = cell.getCellFormula();
                System.out.println(formula);

                // 计算
                CellValue evaluate = FormulaEvaluator.evaluate(cell);
                String cellValue = evaluate.formatAsString();
                System.out.println(cellValue);
                break;
        }

    }


}
