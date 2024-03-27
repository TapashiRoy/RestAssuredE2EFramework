package com.qa.api.testcases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.Constants.APIHTTPStatusCodes.APIHttpStatus;
import com.qa.api.Utilities.JsonPathUtil;
import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;

import io.restassured.response.Response;

public class GetCircuitDataTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop, baseURI);
	}

	@Test
	public void getCircuitData() {
		Map<String, String> pathParams = new HashMap<String, String>();
		pathParams.put("year", "2017");

		Response getRes = rc.getMethodWithPathParams(CIRCUIT_ENDPOINT + "/{year}/circuits.json", pathParams, true,
				false);
		getRes.then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode());

		JsonPathUtil js = new JsonPathUtil();

		String year = js.read(getRes, "$.MRData.CircuitTable.season");
		System.out.println("The circuit Year is : " + year);
		Assert.assertEquals(year, "2017");

		String series = js.read(getRes, "$.MRData.series");
		System.out.println("Series : " + series);
		Assert.assertEquals(series, "f1");

		// List of all the countries
		List<String> countryList = js.read(getRes, "$..Circuits..country");
		int number = countryList.size();
		System.out.println("Number of countries are: " + number);
		for (int i = 0; i < number; i++) {
			System.out.println(countryList.get(i));
		}

		List<String> circuitIds = js.read(getRes, "$..Circuits[*].circuitId");
		int count = circuitIds.size();
		System.out.println("The circuit ID is : " + count);
		Assert.assertEquals(count, 20);

		// Find the MRData CircuitURL
		String circuitUrl = js.read(getRes, "$.MRData.url");
		System.out.println("The MRData Circuit URL is : " + circuitUrl);		

		// Find the Season
		String season = js.read(getRes, "$.MRData.CircuitTable.season");
		System.out.println("The Circuit Season is : " + season);
		Assert.assertEquals("2017", season);
	}
}
