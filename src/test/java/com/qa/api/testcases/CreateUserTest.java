package com.qa.api.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.Constants.APIConstants;
import com.qa.api.Constants.APIHTTPStatusCodes.APIHttpStatus;
import com.qa.api.Utilities.ExcelUtil;
import com.qa.api.Utilities.StringUtils;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;
import com.qa.api.pojo.UserPOJO;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop, baseURI);
	}

	@DataProvider
	public Object[][] getCreateUserData() {
		return new Object[][] { { "UserTesting1", "female", "active" }, { "UserTesting2", "female", "active" } };
	}

	@Test(dataProvider = "getCreateUserData")
	public void createUserTestUsingDataProvider(String name, String gender, String status) {
		UserPOJO user = UserPOJO.builder().name(name).email(StringUtils.getRandomEmailGenerator()).gender(gender)
				.status(status).build();

		Response postRes = rc.postMethod(GOREST_ENDPOINT, user, "JSON", null, true, true);
		Integer userID = postRes.then().assertThat().statusCode(APIHttpStatus.CREATED_201.getCode()).and()
				.body("name", equalToIgnoringCase(name)).and().body("gender", equalTo(gender)).and()
				.body("status", equalTo(status)).extract().path("id");
		System.out.println("User ID is: " + userID);

		// Validate the above User ID through GET call
		Response getRes = rc.getMethod(GOREST_ENDPOINT + "/" + userID, true, true);
		getRes.then().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

	@DataProvider
	public Object[][] getcreateUserDataviaExcel() {
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);		
	}

	@Test(dataProvider = "getcreateUserDataviaExcel")
	public void createUserTestUsingExcel(String name, String gender, String status) {
		UserPOJO user = new UserPOJO(name, StringUtils.getRandomEmailGenerator(),gender,status );		

		Response postRes = rc.postMethod(GOREST_ENDPOINT, user, "JSON", null, true, true);
		Integer userID = postRes.then().assertThat().statusCode(APIHttpStatus.CREATED_201.getCode()).and()
				.body("name", equalToIgnoringCase(name)).and().body("gender", equalTo(gender)).and()
				.body("status", equalTo(status)).extract().path("id");
		System.out.println("User ID is: " + userID);

		// Validate the above User ID through GET call
		Response getRes = rc.getMethod(GOREST_ENDPOINT + "/" + userID, true, true);
		getRes.then().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

}
