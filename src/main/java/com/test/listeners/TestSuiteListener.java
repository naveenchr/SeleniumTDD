package com.test.listeners;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlDefine;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;

import com.test.constants.CommonProperties;
import com.test.framework.ExcelDataProviderMap;
import com.test.framework.ExcelResultUpdate;

/************************************************************************************************************************
 * @Date : Jun 20, 2020
 * @Author : nachrist
 * @Description : Implementation for ISuiteListener to set XML configurations
 *              and load property values
 * @Version : 1.0
 ************************************************************************************************************************/
public class TestSuiteListener implements ISuiteListener {

	private static final Logger logger = LogManager.getLogger(TestSuiteListener.class);

	@Override
	public void onStart(ISuite suite) {

		logger.debug("On Suite Start");

		try {
			CommonProperties.loadProperties();
			ExcelDataProviderMap.fetchExcelDataProviderMap(suite.getName());
			setXmlValues(suite);
		} catch (Exception e) {
			logger.error("Error in Suite Start Block :{}", e);
		}

	}

	@Override
	public void onFinish(ISuite suite) {

		logger.debug("On Suite Finish");

		try {
			ExcelResultUpdate.excelDataUpdate(suite.getName());
		} catch (IOException e) {
			logger.error("Error in Suite Finish Block :{}", e);
		}

	}

	/************************************************************************************************************************
	 * @Date : Apr 25, 2020
	 * @Author : nachrist
	 * @Description : Method to setXml properties
	 * @Method : setXmlValues
	 * @Version : 1.0
	 * @param suite
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	public void setXmlValues(ISuite suite) {

		try {
			suite.getXmlSuite().setParallel(CommonProperties.getParallelRun());
			logger.debug("Parallel Run Type :{} ", suite.getXmlSuite().getParallel());

			suite.getXmlSuite().setThreadCount(CommonProperties.getThreadCount());
			logger.debug("Parallel Thread Count :{} ", suite.getXmlSuite().getThreadCount());

			suite.getXmlSuite().setDataProviderThreadCount(CommonProperties.getDataproviderThread());
			logger.debug("Data Provider Thread Count :{} ", suite.getXmlSuite().getDataProviderThreadCount());

		} catch (Exception e) {
			logger.error("Error in setting Suite XML properties :{}", e);
		}
	}

}
