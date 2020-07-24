# Selenium TDD Framework

A framework that incorporates key features of Selenium and TestNG which can be used to create web-based automation scripts.

**Key Features**

* Supports method wise Parallel test execution
* Screenshots can be taken for Pass/Fail steps
* WebDriver manager based browser initiation
* Platform independent
* Integration with Extent Reports and Excel result updates
* Automatic failure reruns
* Integrated with sonarQube and java code coverage plugin for vulnerability analysis

Note : Utility class is a work in progress

# Table of contents
* [Installation](#installation)
* [Test Framework Design](#test-framework-design)
* [Test Folder Structure](#test-folder-structure)
* [Running the tests](#running-the-tests)
* [Creating new tests](#creating-new-tests)
* [Generating sonar and jacoco reports](#generating-sonar-and-jacoco-reports)
* [Built With](#built-with)
* [Contributing](#contributing)
* [License](#license)
* [Acknowledgments](#acknowledgments)
* [References](#references)

# Test Framework Design
![image](https://user-images.githubusercontent.com/11471191/85928673-6e349380-b8cc-11ea-88dd-7d78423259f8.png)

# Test Folder Structure
![image](https://user-images.githubusercontent.com/11471191/85921106-424aeb00-b897-11ea-86a2-c1d31a0e5b24.png)

# Installation

Clone the repo from GitHub using below command
```git
git clone https://github.com/naveenchr/AutoFrameWork.git
```
Clean and compile the maven project using below commands

```maven
mvn clean
mvn compile
```

# Running the tests

Start test execution from either command prompt or Jenkins

**From Command Prompt**

```maven
mvn clean test -DtestngXML=testng.xml -P Profile_1

```
Run with groups tag

```maven
mvn clean test -DtestngXML=testng.xml -Dgroups="Smoke Test" -P Profile_1
```

Manual failed run addition to automatic rerun
```maven
mvn test -P Profile_2
```

**From Jenkins**

```maven
mvn clean test -DparallelRun=$parallelMode -DthreadCount=$threadCount -DdataProviderThread=$dataProviderThread -DbrowserType=$browserType -DheadlessMode=$headlessMode -DtestngXML=$testngXML -Dgroups="Smoke Test" -P Profile_1

```

**Report Location**

test-output/ExtentReport.html

**Sample Report**

![image](https://user-images.githubusercontent.com/11471191/85928271-5c052600-b8c9-11ea-8b26-84ebd6079002.png)


# Creating new tests

## Test Class File

```java
package com.test.gui;

import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.test.framework.BaseClass;
import com.test.pageobjects.GoogleHomePage;

public class PassFailTest {

	private static final Logger logger = LogManager.getLogger(PassFailTest.class);

	@Test
	public void googleTest_TC001(HashMap<String, String> dataHashMap) {

		try {
			GoogleHomePage googleHomePage = new GoogleHomePage(dataHashMap);
			googleHomePage.searchTextBox();
		} catch (Exception e) {
			BaseClass.getBaseObj().testFailureException(e);
		}
	}
```
## Page Object File

```Java
package com.test.pageobjects;

import java.util.Map;
import org.openqa.selenium.By;
import com.test.framework.UtilityClass;

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
```

## Excel File
![image](https://user-images.githubusercontent.com/11471191/85922565-079a8000-b8a2-11ea-89d9-31f2f3c365a6.png)

# Generating sonar and jacoco reports

* Add sonar server URL in POM file
```XML
<sonar.host.url>http://localhost:9000</sonar.host.url>
```
* Execute the below maven command after the test run
```maven
mvn sonar:sonar
```

# Built With

* Selenium WebDriver
* TestNG
* Maven
* Git
* log4j
* ExtentReport

# Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update the tests as appropriate.

# License
[MIT](https://choosealicense.com/licenses/mit/)

# Acknowledgments

* [Pooja Doshi](https://github.com/poojadoshi7)
* [Vishnu M V](https://github.com/mvvishnu7)

# References

* Selenium WebDriver with Java -Basics to Advanced+Frameworks
[udemy](https://www.udemy.com/course/selenium-real-time-examplesinterview-questions)
