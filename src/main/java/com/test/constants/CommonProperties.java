package com.test.constants;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.xml.XmlSuite.ParallelMode;

import io.github.bonigarcia.wdm.config.DriverManagerType;

/************************************************************************************************************************
 * @Date : Jun 20, 2020
 * @Author : nachrist
 * @Description : Class to fetch data from properties file and from Jenkins and
 *              pass to getter methods
 * @Version : 1.0
 ************************************************************************************************************************/
public class CommonProperties {

	public enum PropertyEnum {

		BROWSER_TYPE("browserType"), PARALLEL_RUN("parallelRun"), THREAD_COUNT("threadCount"),
		DATAPROVIDER_THREAD("dataproviderThread"), HEADLESS_MODE("headlessMode"),
		TAKE_SCREENSHOTS("takePassScreenshot"), PRACTICE_URL("practiceUrl"), GOOGLE_URL("googleUrl"),
		FAIL_TEST_URL("failTestUrl");

		private String property;

		PropertyEnum(String systemProperty) {
			if (System.getProperty(systemProperty) != null) {
				this.property = System.getProperty(systemProperty);
			} else {
				this.property = properties.getProperty(systemProperty);
			}
		}

		@Override
		public String toString() {
			return property;
		}

		public Integer toInteger() {
			return Integer.valueOf(property);
		}

		public Boolean toBoolean() {
			return Boolean.valueOf(property);
		}

	}

	private static final Logger logger = LogManager.getLogger(CommonProperties.class);

	private static Properties properties;

	public static void loadProperties() {

		logger.debug("Data read from properties File");

		try (FileInputStream file = new FileInputStream(SystemVariables.MAIN_RESOURCES + "configuration.properties");) {

			properties = new Properties();
			properties.load(file);
			logger.debug("Data loaded to properties object");
		} catch (Exception e) {
			logger.debug("Error in fetching common properties :{}", e);
		}

	}

	public static DriverManagerType getBrowserType() {
		return DriverManagerType.valueOf(PropertyEnum.BROWSER_TYPE.toString().toUpperCase());
	}

	public static ParallelMode getParallelRun() {

		if (PropertyEnum.PARALLEL_RUN.toString().equalsIgnoreCase(ParallelMode.METHODS.name())) {
			return ParallelMode.METHODS;
		} else if (PropertyEnum.PARALLEL_RUN.toString().equalsIgnoreCase(ParallelMode.CLASSES.name())) {
			return ParallelMode.CLASSES;
		} else {
			return ParallelMode.NONE;
		}
	}

	public static Integer getThreadCount() {
		return PropertyEnum.THREAD_COUNT.toInteger();
	}

	public static Integer getDataproviderThread() {
		return PropertyEnum.DATAPROVIDER_THREAD.toInteger();
	}

	public static Boolean getHeadlessMode() {
		return PropertyEnum.HEADLESS_MODE.toBoolean();
	}

	public static String getPracticeUrl() {
		return PropertyEnum.PRACTICE_URL.toString();
	}

	public static Boolean getScreenshotFlag() {
		return PropertyEnum.TAKE_SCREENSHOTS.toBoolean();
	}

	public static String getGoogleUrl() {
		return PropertyEnum.GOOGLE_URL.toString();
	}

	public static String getFailTestUrl() {
		return PropertyEnum.FAIL_TEST_URL.toString();
	}

}
