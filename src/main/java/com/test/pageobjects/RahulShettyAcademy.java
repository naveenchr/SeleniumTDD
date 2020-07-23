package com.test.pageobjects;

import org.openqa.selenium.By;

import com.test.framework.UtilityClass;

/************************************************************************************************************************
 * @Date : Mar 28, 2020
 * @Author : nachrist
 * @Description : Page object class for RahulShettyAcademy
 * @Version : 1.0
 ************************************************************************************************************************/
public class RahulShettyAcademy extends UtilityClass {

	By courseLink = By.linkText("Course");
	By mentorshipLink = By.linkText("Mentorshi");

	public void courseLink() {
		clickLink(courseLink);
	}

	public void mentorshipLink() {
		clickLink(mentorshipLink);
	}

}
