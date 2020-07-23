package com.test.gui;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.Test;

import com.test.framework.BaseClass;
import com.test.pageobjects.GoogleHomePage;
import com.test.pageobjects.RahulShettyAcademy;

/************************************************************************************************************************
 * @Date : Apr 2, 2020
 * @Author : nachrist
 * @Description : Class to check parallel execution google booking site using
 *              data provider
 * @Version : 1.0
 ************************************************************************************************************************/
public class PassFailTest {

	private static final Logger logger = LogManager.getLogger(PassFailTest.class);

	/************************************************************************************************************************
	 * @Date : Apr 2, 2020
	 * @Author : nachrist
	 * @Description : google booking from excel data provider
	 * @Method : googleBookingTest1
	 * @Version : 1.0
	 * @param fromLocation
	 * @param toLocation
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	@Test(groups = "Smoke Test")
	public void googleTest_TC001(HashMap<String, String> dataHashMap) {

		try {

			GoogleHomePage googleHomePage = new GoogleHomePage(dataHashMap);
			googleHomePage.searchTextBox();
			logger.info(" Data map for validation :" + dataHashMap);

		} catch (Exception e) {
			BaseClass.getBaseObj().testFailureException(e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 2, 2020
	 * @Author : nachrist
	 * @Description : google booking from excel data provider
	 * @Method : googleBookingTest2
	 * @Version : 1.0
	 * @param fromLocation
	 * @param toLocation
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	@Test
	public void googleTest_TC002(HashMap<String, String> dataHashMap) {

		try {

			GoogleHomePage googleHomePage = new GoogleHomePage(dataHashMap);
			googleHomePage.searchTextBox();
			logger.info(" Data map for validation :" + dataHashMap);

		} catch (Exception e) {
			BaseClass.getBaseObj().testFailureException(e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 2, 2020
	 * @Author : nachrist
	 * @Description : google booking from excel data provider
	 * @Method : googleBookingTest2
	 * @Version : 1.0
	 * @param fromLocation
	 * @param toLocation
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	@Test
	public void googleTest_TC003(HashMap<String, String> dataHashMap) {

		try {

			GoogleHomePage googleHomePage = new GoogleHomePage(dataHashMap);
			googleHomePage.searchTextBox();
			logger.info(" Data map for validation :" + dataHashMap);

		} catch (Exception e) {
			BaseClass.getBaseObj().testFailureException(e);
		}
	}

	/************************************************************************************************************************
	 * @Date : Apr 2, 2020
	 * @Author : nachrist
	 * @Description : Method to click mentorship button in training page
	 * @Method : mentorTest
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	@Test
	public void failureTest(HashMap<String, String> dataHashMap) {

		try {
			RahulShettyAcademy rahulShettyAcademy = new RahulShettyAcademy();
			rahulShettyAcademy.mentorshipLink();

		} catch (Exception e) {
			BaseClass.getBaseObj().testFailureException(e);
		}
	}

}
