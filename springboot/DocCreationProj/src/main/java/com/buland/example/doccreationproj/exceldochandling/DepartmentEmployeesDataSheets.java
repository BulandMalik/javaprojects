package com.buland.example.doccreationproj.exceldochandling;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class DepartmentEmployeesDataSheets {

    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();

        // Create first sheet (Department)
        Sheet departmentSheet = workbook.createSheet("Department");

        // Create column headers for Department sheet
        Row departmentHeaderRow = departmentSheet.createRow(0);
        departmentHeaderRow.createCell(0).setCellValue("Id");
        departmentHeaderRow.createCell(1).setCellValue("Name");
        departmentHeaderRow.createCell(2).setCellValue("Address");

        // Add two departments to the Department sheet
        addDepartment(departmentSheet, 1, "HR", "123 Main St");
        addDepartment(departmentSheet, 2, "IT", "456 Tech Ave");

        // Create second sheet (Employee)
        Sheet employeeSheet = workbook.createSheet("Employee");

        // Create column headers for Employee sheet
        Row employeeHeaderRow = employeeSheet.createRow(0);
        employeeHeaderRow.createCell(0).setCellValue("employee_id");
        employeeHeaderRow.createCell(1).setCellValue("employee_name");
        employeeHeaderRow.createCell(2).setCellValue("employee_age");
        employeeHeaderRow.createCell(3).setCellValue("department_id");

        // Add three employees to the Employee sheet
        addEmployee(employeeSheet, 1, "John Doe", 30, 1);
        addEmployee(employeeSheet, 2, "Jane Smith", 25, 1);
        addEmployee(employeeSheet, 3, "Mike Johnson", 35, 2);

        // Write the workbook content to a file
        try (FileOutputStream outputStream = new FileOutputStream("department-employee-sheet.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel file created successfully.");
    }

    private static void addDepartment(Sheet sheet, int id, String name, String address) {
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount + 1);
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(address);
    }

    private static void addEmployee(Sheet sheet, int id, String name, int age, int departmentId) {
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount + 1);
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(age);
        row.createCell(3).setCellValue(departmentId);
    }
}