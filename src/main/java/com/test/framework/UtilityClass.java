package com.test.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/************************************************************************************************************************
 * @Date : Jun 21, 2020
 * @Author : nachrist
 * @Description : Class to maintain utility functions used for POM
 * @Version : 1.0
 ************************************************************************************************************************/
public class UtilityClass {

	private WebDriver driver;
	private BaseClass baseLocalObj;

	private static final Logger logger = LogManager.getLogger(UtilityClass.class);

	/************************************************************************************************************************
	 * @Date : Mar 28, 2020
	 * @Author : nachrist
	 * @Description : Method to click on given Link
	 * @Method : clickLink
	 * @Version : 1.0
	 * @param driver
	 * @param by
	 * @param buttonName
	 * @param EXPLICIT_TIMEOUT_SECONDS
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void clickLink(By by) {

		baseLocalObj = BaseClass.getBaseObj();

		try {
			driver = baseLocalObj.getDriver();

			baseLocalObj.explicitWait(by);

			driver.findElement(by).click();
			logger.debug("Page Title :{}", driver.getTitle());
		} catch (Exception e) {
			baseLocalObj.testFailureException(e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 4, 2020
	 * @Author : nachrist
	 * @Description : Method to enter text to a text field
	 * @Method : textField
	 * @Version : 1.0
	 * @param driver
	 * @param by
	 * @param buttonName
	 * @param sendKeys
	 * @param EXPLICIT_TIMEOUT_SECONDS
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void textField(By by, String sendKeys) {

		try {
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();

			baseLocalObj.explicitWait(by);

			driver.findElement(by).sendKeys(sendKeys);
			logger.debug("Page Title :{} ", driver.getTitle());
			logger.debug("Inserted Text :{} ", driver.findElement(by).getAttribute("value"));
		} catch (Exception e) {
			baseLocalObj.testFailureException(e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 26, 2020
	 * @Author : nachrist
	 * @Description : Method to select from suggestive dropdown
	 * @Method : suggestiveDropdDown
	 * @Version : 1.0
	 * @param by
	 * @param sendKeys
	 * @param fieldName
	 * @param selection
	 * @param EXPLICIT_TIMEOUT_SECONDS
	 * @throws InterruptedException
	 * @ReturnType : void /
	 ***********************************************************************************************************************/

	public void suggestiveDropdDown(By by, String sendKeys, String selection) throws InterruptedException {

		baseLocalObj = BaseClass.getBaseObj();

		try {
			driver = baseLocalObj.getDriver();

			int selectionNum = Integer.parseInt(selection);
			WebElement element = driver.findElement(by);

			baseLocalObj.explicitWait(by);

			element.clear();
			element.sendKeys(sendKeys);
			logger.debug("Entered text :{} ", sendKeys);

			if (selectionNum > 1) {
				for (int i = 0; i < selectionNum; i++) {
					Thread.sleep(500);
					element.sendKeys(Keys.ARROW_DOWN);
				}
			}

			Thread.sleep(500);
			element.sendKeys(Keys.ENTER);
			logger.info("Selected Value :{}", element.getAttribute("value"));
		} catch (Exception e) {
			baseLocalObj.testFailureException(e);
		}

	}

}
