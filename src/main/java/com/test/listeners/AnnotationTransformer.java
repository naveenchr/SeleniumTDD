package com.test.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.test.framework.ExcelDataProviderMap;

/************************************************************************************************************************
 * @Date : Jun 20, 2020
 * @Author : nachrist
 * @Description : Annotation transformer to call iRetryAnalyzer for each @Test
 * @Version : 1.0
 ************************************************************************************************************************/
public class AnnotationTransformer implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(FailureRetryListener.class);
		annotation.setDataProviderClass(ExcelDataProviderMap.class);
		annotation.setDataProvider("Excel Map Provider");
	}
}
