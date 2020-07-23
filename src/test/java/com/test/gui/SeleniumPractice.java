package com.test.gui;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.test.framework.BaseClass;
import com.test.framework.ExcelDataProviderMap;
import com.test.pageobjects.RahulShettyPractice;

public class SeleniumPractice extends BaseClass {

	@Test(dataProvider = "Excel Map Provider", dataProviderClass = ExcelDataProviderMap.class)
	public void seleniumTest(HashMap<String, String> hashMap) {

		try {

			RahulShettyPractice rahulShettyPractice = new RahulShettyPractice(hashMap);
			rahulShettyPractice.clickRadioButton(2);
			rahulShettyPractice.selectSuggestiveDropDown();
			rahulShettyPractice.enterText();
			rahulShettyPractice.selectDropDownValue();
			rahulShettyPractice.selectCheckBoxValue();
			rahulShettyPractice.mouseHoverSelection();
			rahulShettyPractice.checkHiddenElement();
			rahulShettyPractice.switchWindowCheck();
			rahulShettyPractice.switchTabCheck();
			rahulShettyPractice.fetchTableData();
			rahulShettyPractice.checkiFrame();
			rahulShettyPractice.javascriptCheck();
			//calendar

		} catch (Exception e) {
			testFailureException(e);
		}
	}
}