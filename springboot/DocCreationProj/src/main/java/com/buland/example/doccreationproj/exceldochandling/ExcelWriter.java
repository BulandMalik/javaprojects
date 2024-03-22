package com.buland.example.doccreationproj.exceldochandling;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();

        // Create first sheet
        Sheet sheet1 = workbook.createSheet("Sheet 1");
        Row row1 = sheet1.createRow(0);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Hello from Sheet 1!");

        // Create second sheet
        Sheet sheet2 = workbook.createSheet("Sheet 2");
        Row row2 = sheet2.createRow(0);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("Hello from Sheet 2!");

        // Write the workbook content to a file
        try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel file created successfully.");
    }
}