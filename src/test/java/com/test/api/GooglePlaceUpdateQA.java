package com.test.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.test.gui.PassFailTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

/************************************************************************************************************************
 * @Date : Jun 1, 2020
 * @Author : nachrist
 * @Description : Test method to add a new location , update details , fetch and
 *              verify updated value and response.
 * @Version : 1.0
 ************************************************************************************************************************/
public class GooglePlaceUpdateQA {

	private static final Logger logger = LogManager.getLogger(PassFailTest.class);

	/************************************************************************************************************************
	 * @Date : Jun 1, 2020
	 * @Author : nachrist
	 * @Description : Fetch location
	 * @Method : locationUpdate
	 * @Version : 1.0
	 * @ReturnType : void /
	 ***********************************************************************************************************************/
	@Test
	public void locationUpdate() {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String body = "{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
				+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Rahul Shetty Academy\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n" + "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n" + "  ],\r\n" + "  \"website\": \"http://rahulshettyacademy.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n" + "}\r\n" + "";
		;

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(body).when().post("maps/api/place/add/json").then().extract().response().asString();
		
		logger.info("Response : "+response);

		JsonPath js = new JsonPath(response);

		String place = js.getString("place_id");
		logger.info("Place ID : " +place);

	}

}
