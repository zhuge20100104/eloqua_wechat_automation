package com.oracle.auto.web.utility;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by vemurl on 4/17/15.
 */
public class ExcelUtils {

    private static final String RED_HEX_ARGB="FFFFC7CE";
    private static final String YELLOW_HEX_ARGB ="FFFFECA0" ;

    private String excelFilePath;

    public ExcelUtils(String excelFilePath) {
        setExcelFilePath(excelFilePath);
    }

    public static ExcelUtils getInstance(String excelFilePath) {
        return new ExcelUtils(excelFilePath);
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    private void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    private XSSFCell getCell(int rowIndex, int colIndex) {
        try {
            FileInputStream fis = new FileInputStream(excelFilePath);
            XSSFWorkbook workBook = new XSSFWorkbook(fis);

            XSSFSheet sheet = workBook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                int rowNum = row.getRowNum();
                System.out.println("Row No.: " + rowNum);
                if (rowNum == rowIndex) {
                    Iterator cells = row.cellIterator();
                    while (cells.hasNext()) {
                        XSSFCell cell = (XSSFCell) cells.next();
                        int colNum = cell.getColumnIndex();
                        System.out.println("Cell No.: " + colNum);
                        if (colNum == colIndex) {
                            return cell;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getValueInCell(int rowIndex, int colIndex) {
        return getCell(rowIndex, colIndex).getRichStringCellValue().getString();
    }

    private String readCellColour(int rowIndex, int colIndex) {
        return getCell(rowIndex, colIndex).getCellStyle().getFillForegroundColorColor().getARGBHex();
    }

    public boolean isCellColorRed(int rowIndex, int colIndex) {
        return readCellColour(rowIndex, colIndex).equals(RED_HEX_ARGB);
    }

    public boolean isCellColorYellow(int rowIndex, int colIndex) {
        return readCellColour(rowIndex, colIndex).equals(YELLOW_HEX_ARGB);
    }
}


