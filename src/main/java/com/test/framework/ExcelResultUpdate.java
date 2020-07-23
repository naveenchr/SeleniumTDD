package com.test.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.test.constants.SystemVariables;

public class ExcelResultUpdate extends BaseClass {

	private static final Logger logger = LogManager.getLogger(ExcelResultUpdate.class);

	/************************************************************************************************************************
	 * @Date : Apr 24, 2020
	 * @Author : nachrist
	 * @Description : Method to update test result to excel
	 * @Method : excelDataUpdate
	 * @Version : 1.0
	 * @throws IOException
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public static void excelDataUpdate(String suiteName) throws IOException {

		try (

				FileInputStream fileIn = new FileInputStream(SystemVariables.TEST_RESOURCES + "TestData.xlsx");

				XSSFWorkbook workBook = new XSSFWorkbook(fileIn);

				FileOutputStream fileOut = new FileOutputStream(SystemVariables.TEST_RESOURCES + "TestData.xlsx");

		) {

			workBook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);

			for (Sheet sheet : workBook) {

				logger.debug("Sheet Name :{}", sheet.getSheetName());

				if (suiteName.contains(sheet.getSheetName())) {
					updateSheetData(sheet);
				}

			}

			workBook.write(fileOut);
			logger.debug("Updated to Work Book");

		} catch (Exception e) {
			logger.error("Excel Data Update Error :{}", e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 24, 2020
	 * @Author : nachrist
	 * @Description : Method to update sheet data
	 * @Method : updateSheetData
	 * @Version : 1.0
	 * @param sheet
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public static void updateSheetData(Sheet sheet) {

		String testCaseID = null;
		double testRunStatus;
		double testSetID;
		long tcStartTime;
		long tcEndTime;

		Cell cell;

		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

		Iterator<Row> rows = sheet.rowIterator();

		rows.next();

		while (rows.hasNext()) {

			Row row = rows.next();

			testCaseID = row.getCell(0).getStringCellValue();
			logger.debug("Test Case ID :{} ", testCaseID);

			testSetID = row.getCell(1).getNumericCellValue();
			if (testSetID == 0.0) {
				testSetID = 1.0;
			}
			logger.debug("Test Set ID :{} ", testSetID);

			StringBuilder bld = new StringBuilder();
			bld.append(testCaseID);
			bld.append("_");
			bld.append(testSetID);

			testCaseID = bld.toString();

			if (executionStatusMap.get(testCaseID) != null) {

				tcStartTime = executionStatusMap.get(testCaseID).get(0);
				logger.debug("Test Start Time :{} ", tcStartTime);
				cell = row.getCell(5);
				cell.setCellValue(dateFormat.format((new Date(tcStartTime))));
				logger.debug("Test Start Time Updated as :{} ", tcStartTime);

				tcEndTime = executionStatusMap.get(testCaseID).get(1);
				logger.debug("Test End Time  :{} ", tcEndTime);
				cell = row.getCell(6);
				cell.setCellValue(dateFormat.format((new Date(tcEndTime))));
				logger.debug("Test End Time Updated as :{} ", tcEndTime);

				testRunStatus = executionStatusMap.get(testCaseID).get(2);
				logger.debug("Test run Status  :{} ", testRunStatus);
				cell = row.getCell(7);
				if (testRunStatus == 1.0) {
					cell.setCellValue("PASSED");
				} else {
					cell.setCellValue("FAILED");
				}
				logger.debug("Test Run Status Updated as :{} ", testRunStatus);
			}
		}

	}

}
