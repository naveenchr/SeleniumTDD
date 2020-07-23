package com.test.constants;

import java.io.File;

/************************************************************************************************************************
 * @Date : Jun 1, 2020
 * @Author : nachrist
 * @Description : Enum class for saving directory variables
 * @Version : 1.0
 ************************************************************************************************************************/
public enum SystemVariables {

	USER_DIR("user.dir"),
	MAIN_RESOURCES(SystemVariables.USER_DIR, "src" + File.separator + "main" + File.separator + "resources"),
	TEST_RESOURCES(SystemVariables.USER_DIR, "src" + File.separator + "test" + File.separator + "resources"),
	OUTPUT_FOLDER(SystemVariables.USER_DIR, "test-output"), USER_NAME("user.name"), OS_ARCH("os.arch"),
	OS_NAME("os.name"), OS_VERSION("os.version");

	String systemProperty;

	SystemVariables(String systemProperty) {
		this.systemProperty = System.getProperty(systemProperty);
	}

	SystemVariables(SystemVariables systemProperty, String dir) {
		this.systemProperty = systemProperty + File.separator + dir + File.separator;
	}

	@Override
	public String toString() {
		return systemProperty;

	}

}
