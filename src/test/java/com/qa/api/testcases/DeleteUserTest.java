package com.qa.api.testcases;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.Constants.APIHTTPStatusCodes.APIHttpStatus;
import com.qa.api.Utilities.JsonPathUtil;
import com.qa.api.Utilities.StringUtils;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;
import com.qa.api.pojo.UserPOJO;

import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop, baseURI);
	} 

	@DataProvider
	public Object[][] getCreateUserData() {
		return new Object[][] { { "UserTesting8", "female", "active" }, { "UserTesting9", "female", "active" } };
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

		// Delete the above User ID through DELETE call
		Response deleteRes = rc.deleteMethod(GOREST_ENDPOINT + "/" + userID, true, true) ;			
		deleteRes.then().assertThat().statusCode(APIHttpStatus.NO_CONTENT_204.getCode());
		
		//Validate the Deletion by using get call
		Response getRes = rc.getMethod(GOREST_ENDPOINT + "/" + userID, true, true);
		getRes.then().assertThat().statusCode(APIHttpStatus.NOT_FOUND_404.getCode());
		
		//Validate the body
		JsonPathUtil js = new JsonPathUtil();
		String message = js.read(getRes, "$.message");
		Assert.assertEquals(message, "Resource not found");
	}
}
