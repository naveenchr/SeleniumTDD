package com.test.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/************************************************************************************************************************
 * @Date : Jun 20, 2020
 * @Author : nachrist
 * @Description : IRetryAnalyzer implementation for automatic failed test case
 *              reruns
 * @Version : 1.0
 ************************************************************************************************************************/
public class FailureRetryListener implements IRetryAnalyzer {

	int rerunCounter = 1;

	@Override
	public boolean retry(ITestResult result) {

		if (!result.isSuccess() && rerunCounter <= 1) {
			rerunCounter++;
			return true;
		} else {
			return false;
		}
	}

}
