package com.test.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.test.constants.SystemVariables;

/************************************************************************************************************************
 * @Date : Apr 2, 2020
 * @Author : nachrist
 * @Description : Class for extracting data from excel and passing to method as
 *              data provider using Maps
 * @Version : 1.0
 ************************************************************************************************************************/

public class ExcelDataProviderMap extends BaseClass {

	private static final Logger logger = LogManager.getLogger(ExcelDataProviderMap.class);

	private static String prevTestID = null;
	private static double prevTestSet = 0.0;

	/************************************************************************************************************************
	 * @throws IOException
	 * @Date : Apr 4, 2020
	 * @Author : nachrist
	 * @Description : Method to fetch data from excel to single LinkedHashMap object
	 * @Method : excelDataProviderClass
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/

	public static void fetchExcelDataProviderMap(String suiteName) throws IOException {

		try (

				FileInputStream fileIn = new FileInputStream(SystemVariables.TEST_RESOURCES + "TestData.xlsx");

				XSSFWorkbook workBook = new XSSFWorkbook(fileIn);) {

			logger.info("Data Load From Excel Started");

			workBook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);

			for (Sheet sheet : workBook) {

				logger.debug("Sheet Name :{}", sheet.getSheetName());
				logger.debug("Sheet Name :{}", suiteName);

				if (suiteName.contains(sheet.getSheetName())) {

					loadSheetData(sheet);

				}

			}

			linkedMap.put(prevTestSet, dataMap);
			logger.debug("Linked Map Update Last:{} ", linkedMap);
			nestedMap.put(prevTestID, linkedMap);
			logger.debug("Previous Test ID :{} ", prevTestID);
			logger.debug("LinkedMap :{} ", linkedMap);
			logger.debug("Nested Map :{} ", nestedMap);

			logger.info("Data Load From Excel Completed");
		}

		catch (FileNotFoundException e) {
			logger.error("File not found :{}", e);
		} catch (Exception e) {
			logger.error("Data Load Error :{}", e);

		}
	}

	/************************************************************************************************************************
	 * @Date : Apr 24, 2020
	 * @Author : nachrist
	 * @Description : To load all sheet data
	 * @Method : loadSheetData
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public static void loadSheetData(Sheet sheet) {

		double testSet;
		String testID = null;
		String attribute = null;
		String value = null;

		Iterator<Row> rows = sheet.rowIterator();

		rows.next();

		while (rows.hasNext()) {

			Row row = rows.next();
			testID = row.getCell(0).getStringCellValue();
			testSet = row.getCell(1).getNumericCellValue();
			attribute = row.getCell(2).getStringCellValue();

			if (row.getCell(3).getCellType() == CellType.STRING) {
				value = row.getCell(3).getStringCellValue();
			} else if (row.getCell(3).getCellType() == CellType.NUMERIC) {
				value = NumberToTextConverter.toText(row.getCell(3).getNumericCellValue());
			}

			logger.debug("Test ID in Data Sheet :{} ", testID);
			logger.debug("Test Set No. :{} ", testSet);
			logger.debug("Test Attribute :{} ", attribute);

			logger.debug("Previous Test ID :{}", prevTestID);
			if ((prevTestID == null || testID.equals(prevTestID))) {
				prevTestID = testID;
				logger.debug("Previous Test Set :{} ", prevTestSet);

				if (prevTestSet == 0.0 || testSet == prevTestSet) {
					dataMap.put(attribute, value);
					logger.debug("List :{}", dataMap);

					prevTestSet = testSet;

				} else if (testSet != prevTestSet) {

					linkedMap.put(prevTestSet, dataMap);
					logger.debug("Linked Map :{}", linkedMap);

					prevTestSet = testSet;
					dataMap = new LinkedHashMap<String, String>();
					dataMap.put(attribute, value);
					logger.debug("List :{} ", dataMap);

				}
				logger.debug("Previous TestSet :{}", prevTestSet);

			} else if (!testID.equals(prevTestID) && testSet != 0.0) {

				linkedMap.put(prevTestSet, dataMap);
				logger.debug("Linked Map in Else If:{} ", linkedMap);

				linkedMap.remove(0.0);

				nestedMap.put(prevTestID, linkedMap);
				logger.debug("Previous Test ID :{}", prevTestID);
				logger.debug(" LinkedMap :{} ", linkedMap);
				logger.debug("Nested Map :{} ", nestedMap);

				prevTestID = testID;
				prevTestSet = testSet;

				dataMap = new HashMap<String, String>();
				linkedMap = new LinkedHashMap<Double, HashMap<String, String>>();
				dataMap.put(attribute, value);
				logger.debug("List :{} ", dataMap);

			}
			logger.debug("Previous Test ID :{} ", prevTestID);

		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 4, 2020
	 * @Author : nachrist
	 * @Description : Data provider method which uses the data hash map to iterate
	 *              through the data sets
	 * @Method : dataProviderMethod
	 * @Version : 1.0
	 * @param m
	 * @return
	 * @throws IOException
	 * @ReturnType : Object[][] /
	 ***********************************************************************************************************************/
	@DataProvider(name = "Excel Map Provider", parallel = true)
	public Object[][] dataProviderMethod(Method m) {

		int testSetNum;
		int j = 0;
		String methodName = null;
		Object[][] testData = null;
		LinkedHashMap<Double, HashMap<String, String>> testDataMap;

		try {

			methodName = m.getName();
			logger.debug("Method Name :{} ", methodName);
			testDataMap = nestedMap.get(methodName);
			logger.debug("Test Data Map :{}", testDataMap);

			testSetNum = testDataMap.size();
			logger.debug("Test Set Count :{} ", testSetNum);

			testData = new Object[testSetNum][1];

			for (Map.Entry<Double, HashMap<String, String>> entry : testDataMap.entrySet()) {

				logger.debug("Test Set Num  :{} ", entry.getKey());
				testData[j][0] = testDataMap.get(entry.getKey());
				logger.debug("Test Parameter  :{} ", testDataMap.get(entry.getKey()));

				j++;

			}

		} catch (Exception e) {
			testFailureException(e);
		}
		return testData;

	}
}
