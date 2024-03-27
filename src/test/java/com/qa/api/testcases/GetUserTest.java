package com.qa.api.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.Constants.APIHTTPStatusCodes.APIHttpStatus;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest {	
	
	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop,baseURI);
	}
	
	@Test(priority=2)
	public void getAllUsersTest() {		
		rc.getMethod(GOREST_ENDPOINT, true, true)
			.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}	
	
	@Test(priority=1)
	public void getASpecificUser_With_QueryParams() {		
		Map<String, Object> queryParams = new HashMap<String, Object>();
		
		queryParams.put("name", "UserTesting");
		queryParams.put("status", "active");
		rc.getMethod(GOREST_ENDPOINT, null, queryParams, true,true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());		
	}

}
