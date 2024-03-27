package com.qa.api.testcases;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.Utilities.StringUtils;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;
import com.qa.api.pojo.UserPOJO;

public class APISchemaValidationTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop, baseURI);
	}

	@Test
	public void createUserTestSchemaValidation() {
		UserPOJO user = new UserPOJO("Tom", StringUtils.getRandomEmailGenerator(), "male", "active");

		rc.postMethod(GOREST_ENDPOINT, user, "JSON", false, true).then().log().all().assertThat()				
				.body(matchesJsonSchemaInClasspath("gorestcreateuserschema.json"));
	}

}
