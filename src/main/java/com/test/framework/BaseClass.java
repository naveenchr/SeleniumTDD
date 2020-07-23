package com.test.framework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.TestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.test.constants.CommonProperties;
import com.test.constants.SystemVariables;
import com.test.listeners.WebDriverListener;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

/************************************************************************************************************************
 * @Date : Mar 28, 2020
 * @Author : nachrist
 * @Description : Base class to keep all common methods
 * @Version : 2.0
 ************************************************************************************************************************/
public class BaseClass {

	private static final Logger logger = LogManager.getLogger(BaseClass.class);

	protected static HashMap<String, String> dataMap = new LinkedHashMap<>();
	protected static LinkedHashMap<Double, HashMap<String, String>> linkedMap = new LinkedHashMap<>();
	protected static ConcurrentHashMap<String, LinkedHashMap<Double, HashMap<String, String>>> nestedMap = new ConcurrentHashMap<>();

	protected static ConcurrentHashMap<String, List<Long>> executionStatusMap = new ConcurrentHashMap<>();
	private static final ThreadLocal<BaseClass> baseClassObj = new ThreadLocal<>();

	private static ExtentReports extentReports;
	private static final int EXPLICIT_TIMEOUT_SECONDS = 3;

	private WebDriver driver;
	private String methodName;
	private ExtentTest extentTest;
	private Double testSetNum;

	/************************************************************************************************************************
	 * @Date : Apr 25, 2020
	 * @Author : nachrist
	 * @Description : Method to fetch url for test either from properties file or excel
	 * @Method : getUrl
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void fetchUrl() {

		try {
			String method = methodName;
			logger.debug("Method Name :{}", method);

			if (method.toUpperCase().contains("SELENIUM")) {
				method = CommonProperties.getPracticeUrl();
			} else if (method.toUpperCase().contains("GOOGLE")) {
				method = CommonProperties.getGoogleUrl();
			} else if (method.toUpperCase().contains("FAIL")) {
				method = CommonProperties.getFailTestUrl();
			} else {
				method = nestedMap.get(method).get(1.0).get("URL");
			}

			logger.debug("Test Url : {}", method);

			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.get(method);
		} catch (

		Exception e) {
			testFailureException(e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Mar 28, 2020
	 * @Author : nachrist
	 * @Description : Method to open browser instance based on common properties
	 * @Method : browserSelection
	 * @Version : 1.0
	 * @return
	 * @throws IOException
	 * @ReturnType : WebDriver /
	 ***********************************************************************************************************************/
	public void browserSelection() {

		DriverManagerType browserType;
		boolean headlessMode;

		try {

			browserType = CommonProperties.getBrowserType();
			logger.debug("Browser Name :{} ", browserType);

			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			headlessMode = CommonProperties.getHeadlessMode();
			logger.debug("Headless Mode :{} ", headlessMode);
			options.setHeadless(headlessMode);

			WebDriverManager.getInstance(browserType).setup();

			if (browserType.equals(DriverManagerType.CHROME)) {
				driver = new ChromeDriver(options);
				logger.info("Chrome instance started");
			} else if (browserType.equals(DriverManagerType.FIREFOX)) {
				driver = new FirefoxDriver();
				logger.info("Firefox instance started");
			} else {
				driver = new ChromeDriver();
				logger.info("Default browser chrome instance started");
			}

			if (CommonProperties.getScreenshotFlag().equals(true)) {
				EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
				WebDriverListener webDriverListener = new WebDriverListener();
				eventDriver.register(webDriverListener);
				driver = eventDriver;
			}
			logger.debug("Take screenshots for all Steps :{} ", CommonProperties.getScreenshotFlag());

		} catch (Exception e) {
			testFailureException(e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Mar 28, 2020
	 * @Author : nachrist
	 * @Description : Tear down method and updates to be executed after each test
	 *              suite
	 * @Method : saveTestRunDetails
	 * @Version : 2.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void saveTestRunDetails(ITestResult result) {

		String testCaseID;
		Double testSetNumber;
		long tcStartTime;
		long tcEndTime;
		boolean executionStatus;

		tcStartTime = result.getStartMillis();
		logger.debug("Test Start Time :{} ", tcStartTime);

		testCaseID = result.getMethod().getMethodName();
		logger.debug("Method Name :{} ", testCaseID);

		testSetNumber = ((TestResult) result).getParameterIndex() + 1.0;
		logger.debug("Test Set ID :{} ", testSetNumber);

		tcEndTime = result.getEndMillis();
		logger.debug("Test End Time :{} ", tcEndTime);

		executionStatus = result.isSuccess();
		logger.debug("Test Execution Passed :{} ", executionStatus);

		StringBuilder bld = new StringBuilder();
		bld.append(testCaseID);
		bld.append("_");
		bld.append(testSetNumber);

		testCaseID = bld.toString();

		logger.debug(testCaseID);

		List<Long> testRunInfo = new ArrayList<>();
		testRunInfo.add(tcStartTime);
		testRunInfo.add(tcEndTime);
		if (executionStatus) {
			testRunInfo.add(1L);
		} else {
			testRunInfo.add(0L);
		}

		logger.debug("testRunInfo :{} ", testRunInfo);

		logger.debug("testCaseID_testSetNum :{} ", testCaseID);
		executionStatusMap.put(testCaseID, testRunInfo);
		logger.debug("Execution Status Map :{}", executionStatusMap);

		logger.info("Saved test Start Time :{} ", tcStartTime);
		logger.info("End Time :{} ", tcEndTime);

	}

	/************************************************************************************************************************
	 * @Date : Mar 28, 2020
	 * @Author : nachrist
	 * @Description : Method to close any open browsers
	 * @Method : closeBrowsers
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void closeBrowsers() {

		if (driver != null) {
			driver.quit();
			logger.info("All browser instances are closed");
		} else {
			logger.debug("No browser instances are open");
		}

	}

	/************************************************************************************************************************
	 * @Date : Mar 28, 2020
	 * @Author : nachrist
	 * @Description :
	 * @Method : testFailureException
	 * @Version : 1.0
	 * @param e
	 * @param methodName
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void testFailureException(Exception e) {

		logger.error("Error Log :{} ", e);

		takeScreenshot();
		closeBrowsers();
		extentTest.log(Status.FAIL, e);
		Assert.assertTrue(false);
	}

	/************************************************************************************************************************
	 * @Date : Apr 26, 2020
	 * @Author : nachrist
	 * @Description : Method to wait for an element till its visible
	 * @Method : waitForElement
	 * @Version : 1.0
	 * @param driver
	 * @param by
	 * @param EXPLICIT_TIMEOUT_SECONDS
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void explicitWait(By by) {

		WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT_SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated((by)));

	}

	/************************************************************************************************************************
	 * @param result
	 * @throws IOException
	 * @Date : Mar 28, 2020
	 * @Author : nachrist
	 * @Description : Method to take screenshot during failures
	 * @Method : takeFailureScreenshot
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void takeScreenshot() {

		String screenshotName;
		String screenshotPath;

		screenshotName = methodName + "_" + System.currentTimeMillis() + ".jpg";

		try {

			TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
			File srcFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
			logger.debug("Taken Screenshot for Step");

			File tgtFile = new File(SystemVariables.OUTPUT_FOLDER + "Screenshots" + File.separator, screenshotName);
			FileUtils.copyFile(srcFile, tgtFile);
			logger.debug("Saved screenshot to results with Name :{} ", screenshotName);

			Reporter.log("<br><img src='Screenshots/" + screenshotName + ".jpg' height='400' width='400'/><br>");

			screenshotPath = SystemVariables.OUTPUT_FOLDER + "Screenshots" + File.separator + screenshotName;
			logger.debug("Screenshot Path :{} ", screenshotPath);

			getExtentTest().addScreenCaptureFromPath(screenshotPath);

		} catch (Exception e) {
			logger.error("Error in Capturing Screenshot for :{} ", driver.getTitle());
		}

	}

	/************************************************************************************************************************
	 * @Date : Jun 20, 2020
	 * @Author : nachrist
	 * @Description : Configuration setups for extent report
	 * @Method : configureExtentReports
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public static void configureExtentReports() {

		try {
			String outputDirectory = SystemVariables.OUTPUT_FOLDER + "ExtentReport.html";

			ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(outputDirectory);
			extentSparkReporter.config().setReportName("Extend Report Test");
			extentSparkReporter.config().setDocumentTitle("Test Execution");
			extentSparkReporter.config().enableOfflineMode(false);
			extentSparkReporter.config().setTheme(Theme.DARK);

			extentReports = new ExtentReports();
			extentReports.attachReporter(extentSparkReporter);
			extentReports.setSystemInfo(SystemVariables.USER_NAME.name(), SystemVariables.USER_NAME.toString());
			extentReports.setSystemInfo(CommonProperties.PropertyEnum.BROWSER_TYPE.name(),
					CommonProperties.getBrowserType().toString());
			extentReports.setSystemInfo(SystemVariables.OS_VERSION.name(), SystemVariables.OS_VERSION.toString());
			extentReports.setSystemInfo(SystemVariables.OS_NAME.name(), SystemVariables.OS_NAME.toString());

		} catch (Exception e) {
			logger.error("Error in configuring extent report settings :{} ", e);
		}
	}

	/************************************************************************************************************************
	 * @Date : Jun 27, 2020
	 * @Author : nachrist
	 * @Description : Getter setter methods to set driver , test , report , method objects 
	 * @Method : GettersSetters
	 * @Version : 1.0
	 * @param method
	 * @ReturnType : void
	/***********************************************************************************************************************/
	
	public void setMethodName(String method) {
		this.methodName = method;
	}

	public void setTestSetNum(Double testSetNum) {
		this.testSetNum = testSetNum;
	}

	public ExtentTest getExtentTest() {
		return extentTest;
	}

	public void setExtentTest() {
		this.extentTest = extentReports.createTest(methodName + "_" + testSetNum);
	}

	public static ExtentReports getExtentReports() {
		return extentReports;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static void setBaseObj(BaseClass baseObj) {
		baseClassObj.set(baseObj);
	}

	public static BaseClass getBaseObj() {
		return baseClassObj.get();
	}

	public static void removeBaseObj() {
		baseClassObj.remove();
	}

}
