package com.qa.api.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.api.Constants.APIHTTPStatusCodes.APIHttpStatus;
import com.qa.api.Utilities.JsonPathUtil;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;

import io.restassured.response.Response;

public class GetFlightBookingTest extends BaseTest {
	private static String accessToken;

	@Parameters({ "baseURI", "grantType", "client_id", "client_secret" })
	@BeforeMethod
	public void getFlightAPISetup(String baseURI, String grantType, String client_id, String client_secret) {
		rc = new restClient(prop, baseURI);
		accessToken = rc.getAccessTokenViaOAuth2(AMADEUS_TOKEN_ENDPOINT, grantType, client_id, client_secret);
	}

	@Test
	public void getFlightDestinations() {
		Map<String, Object> flightParams = new HashMap<String, Object>();		
		flightParams.put("origin", "PAR");
		flightParams.put("maxPrice", 200);
		
		Map<String, String> headersMap = new HashMap<String,String>();	
		headersMap.put("Authorization", "Bearer " + accessToken);
		
		Response getRes= rc.getMethod(AMADEUS_FLIGHTBOOKING_ENDPOINT, headersMap, flightParams, true, false);
		getRes.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	
		JsonPathUtil js = new JsonPathUtil();
		String type =js.read(getRes, "data[0].type");		 
		System.out.println("The Type is : " + type);
		Assert.assertEquals("flight-destination", type); 
	}

}
