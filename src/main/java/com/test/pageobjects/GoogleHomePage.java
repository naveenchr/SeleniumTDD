package com.test.pageobjects;

import java.util.Map;

import org.openqa.selenium.By;

import com.test.framework.UtilityClass;

/************************************************************************************************************************
 * @Date : Mar 28, 2020
 * @Author : nachrist
 * @Description : Page object class for GoogleHomePage
 * @Version : 1.0
 ************************************************************************************************************************/
public class GoogleHomePage extends UtilityClass {

	private Map<String, String> map;

	public GoogleHomePage(Map<String, String> map) {
		this.map = map;
	}

	By searchTextBox = By.cssSelector("[type='text']");

	public void searchTextBox() {
		textField(searchTextBox, map.get("From Location"));
	}

}
