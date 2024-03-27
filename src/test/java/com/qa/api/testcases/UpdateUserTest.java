package com.qa.api.testcases;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.qa.api.Constants.APIHTTPStatusCodes.APIHttpStatus;
import com.qa.api.Utilities.StringUtils;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;
import com.qa.api.pojo.UserPOJO;

import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop, baseURI);
	}

	public Object[][] createUserData() {
		return new Object[][] { { "UserTesting5", "female", "active" } };
	}

	public Object[][] UpdateUserData() {
		return new Object[][] { { "UserTesting5Updated", "female", "inactive" } };
	}
	
	@DataProvider
	public Object[][] testData(){
		List<Object[]> result = Lists.newArrayList();
		  result.addAll(Arrays.asList(createUserData()));
		  result.addAll(Arrays.asList(UpdateUserData()));
		  return result.toArray(new Object[result.size()][]);
	}

	@Test(dataProvider = "testData")
	public void createUserTestUsingDataProvider(String name, String gender, String status) {
		UserPOJO user = UserPOJO.builder().name(name).email(StringUtils.getRandomEmailGenerator()).gender(gender)
				.status(status).build();

		Response postRes = rc.postMethod(GOREST_ENDPOINT, user, "JSON", null, true, true);
		Integer userID = postRes.then().assertThat().statusCode(APIHttpStatus.CREATED_201.getCode()).and()
				.body("name", equalToIgnoringCase(name)).and().body("gender", equalTo(gender)).and()
				.body("status", equalTo(status)).extract().path("id");
		System.out.println("User ID is: " + userID);

		// Update the above User ID through PUT call
		UserPOJO userUpd = UserPOJO.builder().name(name).email(StringUtils.getRandomEmailGenerator())
				.gender(gender).status(status).build();
		Response updRes = rc.putMethod(GOREST_ENDPOINT + "/" + userID, userUpd, "JSON", null, true, true);

		updRes.then().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

}
