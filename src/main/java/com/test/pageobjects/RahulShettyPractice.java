package com.test.pageobjects;

import java.util.Map;

import org.openqa.selenium.By;

import com.test.framework.UtilityClass;

/************************************************************************************************************************
 * @Date : Apr 25, 2020
 * @Author : nachrist
 * @Description : Rahul sheety practice page for selenium
 * @Version : 1.0
 ************************************************************************************************************************/
public class RahulShettyPractice extends UtilityClass {

	private Map<String, String> map;

	By suggDropDown = By.xpath("//*[@id='autocomplete']");

	public RahulShettyPractice(Map<String, String> map) {
		this.map = map;
	}

	public void clickRadioButton(int buttonNum) {
		clickLink(By.cssSelector("[value='radio" + buttonNum + "']"));
	}

	public void selectDropDownValue() {
		// TODO Auto-generated method stub

	}

	public void selectCheckBoxValue() {
		// TODO Auto-generated method stub

	}

	public void mouseHoverSelection() {
		// TODO Auto-generated method stub

	}

	public void checkHiddenElement() {
		// TODO Auto-generated method stub

	}

	public void switchWindowCheck() {
		// TODO Auto-generated method stub

	}

	public void switchTabCheck() {
		// TODO Auto-generated method stub

	}

	public void fetchTableData() {
		// TODO Auto-generated method stub

	}

	public void checkiFrame() {
		// TODO Auto-generated method stub

	}

	public void javascriptCheck() {
		// TODO Auto-generated method stub

	}

	public void selectSuggestiveDropDown() throws InterruptedException {
		suggestiveDropdDown(suggDropDown, map.get("countryCode"), map.get("countrySelectionNum"));

	}

	public void enterText() {
		// TODO Auto-generated method stub

	}
}
