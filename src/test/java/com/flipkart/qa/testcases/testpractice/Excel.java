package com.flipkart.qa.testcases.testpractice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public Object[][] getExcelData(String excelPath, String sheetName) {

		try {
			Object dataSets[][] = null;
			FileInputStream file = new FileInputStream(new File(excelPath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int totalRow = sheet.getLastRowNum();
			int totalColumn = sheet.getRow(0).getLastCellNum();
			dataSets = new Object[totalRow][totalColumn - 1];
			Iterator<Row> rowItrator = sheet.iterator();
			int i = 0;
			while (rowItrator.hasNext()) {
				Row row = rowItrator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				int j = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getStringCellValue().contains("Start")) {
						i = 0;
						break;
					}
					switch (cell.getCellTypeEnum()) {
					case STRING:
						dataSets[i - 1][j++] = cell.getStringCellValue();
						break;
					case BOOLEAN:
						dataSets[i - 1][j++] = cell.getBooleanCellValue();
						break;
					case NUMERIC:
						dataSets[i - 1][j++] = cell.getNumericCellValue();
					case FORMULA:
						dataSets[i - 1][j++] = cell.getCellFormula();
						break;
					default:
						System.out.println("No matching enum data type found ");
						break;
					}
				}
				i++;
			}
			return dataSets;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void updateResult(String excelPath, String sheetName, String testCaseName, String testStatus) {
		try {
			FileInputStream file = new FileInputStream(new File(excelPath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int totalRows = sheet.getLastRowNum() + 1;
			for (int i = 1; i < totalRows; i++) {
				XSSFRow cellRow = sheet.getRow(i);
				String cellType = cellRow.getCell(0).getStringCellValue();
				if (cellType.contains(testCaseName)) {
					cellRow.createCell(1).setCellValue(testStatus);
					file.close();
					// log.info("Result is updated");
					FileOutputStream out = new FileOutputStream(new File(excelPath));
					workbook.write(out);
					out.close();
				}
			}

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		Excel excel = new Excel();
		String excelLocation = "C:\\Users\\HousingMan\\Desktop\\testData.xlsx";
		excel.excelcheck(excelLocation, "LoginData");

		/*
		 * Object[][] data = excel.getExcelData(excelLocation, "LoginData");
		 * System.out.println(data[0][0] + " " + data[0][1] + " " + data[0][2]);
		 * System.out.println(data[1][0] + " " + data[1][1] + " " + data[1][2]);
		 * System.out.println(data[2][0] + " " + data[2][1] + " " + data[2][2]);
		 */

		/*
		 * excelReaderHelper.updateResult(excelLocation, "TestScripts", "Login",
		 * "PASS"); excelReaderHelper.updateResult(excelLocation, "TestScripts",
		 * "Registration", "Fail"); excelReaderHelper.updateResult(excelLocation,
		 * "TestScripts", "Add to cart", "PASS");
		 */
	}

	public void excelcheck(String excelPath, String sheetName) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(excelPath);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet(sheetName);

			int row = sheet.getLastRowNum();
			int col = sheet.getRow(0).getLastCellNum();
			System.out.println("Total Row is " + row + " Total Col is " + col);
			/*
			 * for (int i = 0; i < row; i++) { for (int j = 0; j < col; j++) {
			 * System.out.print(sheet.getRow(i + 1).getCell(j).toString()+" "); }
			 * System.out.println(); }
			 */

			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) {
				Row rw = itr.next();
				Iterator<Cell> celliterator = rw.cellIterator();
				while (celliterator.hasNext()) {
					Cell cell = celliterator.next();
					if (cell.getStringCellValue().contains("Start")) {
						break;
					}
					switch (cell.getCellType()) {
					case STRING:
						System.out.print(cell.getStringCellValue() + " ");
						break;
					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + " ");
						break;
					case NUMERIC:
						System.out.print(cell.getNumericCellValue() + " ");
						break;
					case FORMULA:
						System.out.print(cell.getCellFormula() + " ");
						break;
					default:
						System.out.println("No matching enum data type found ");
						break;
					}
				}
				System.out.println();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
