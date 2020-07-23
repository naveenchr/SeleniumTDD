package com.test.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.test.framework.BaseClass;

public class WebDriverListener implements WebDriverEventListener {

	private static final Logger logger = LogManager.getLogger(WebDriverListener.class);

	@Override
	public void beforeAlertAccept(WebDriver driver) {
		logger.trace("Before Accepting Alert");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterAlertAccept(WebDriver driver) {
		logger.trace("After Accepting Alert");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {
		logger.trace("After Dismissing Alert");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {
		logger.trace("Before Dismissing Alert");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		logger.trace("Before Navigating to : {}", url);
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		logger.trace("After Navigating to : {}", url);
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		logger.trace("Before Navigating Back");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		logger.trace("After Navigating Back");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		logger.trace("Before Navigating Forward");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		logger.trace("After Navigating Forward");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		logger.trace("Before Navigating and Refreshing");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		logger.trace("After Navigating and Refreshing");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		logger.trace("Before finding element");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		logger.trace("After finding element");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		logger.trace("Before clicking element ");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		logger.trace("After clicking element ");
		BaseClass.getBaseObj().takeScreenshot();

	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		logger.trace("Before changing value of element ");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		logger.trace("After changing value of element ");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		logger.trace("Before executing script : {}", script);
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		logger.trace("After executing script : {}", script);
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		logger.trace("Before swtiching to window : {}", windowName);
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		logger.trace("After swtiching to window : {}", windowName);
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		logger.trace("Exception in WebDriver Listener");
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		logger.trace("Before getting screenshot");
	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		logger.trace("After getting screenshot");
	}

	@Override
	public void beforeGetText(WebElement element, WebDriver driver) {
		logger.trace("Before getting value of element ");
		BaseClass.getBaseObj().takeScreenshot();
	}

	@Override
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		logger.trace("After getting value of element ");
		BaseClass.getBaseObj().takeScreenshot();
	}

}
