package com.qa.api.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import com.qa.api.Configuration.Configuration;
import com.qa.api.client.restClient;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureResultsWriter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {
	protected Configuration config;
	protected Properties prop;
	protected restClient rc;
	protected String baseURI;
	
	//Service URLs:
	public static final String GOREST_ENDPOINT = "/public/v2/users";
	public static final String CIRCUIT_ENDPOINT = "/api/f1";
	public static final String AMADEUS_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
	public static final String AMADEUS_FLIGHTBOOKING_ENDPOINT = "/v1/shopping/flight-destinations";
	public static final String REQRES_ENDPOINT = "/api/users";	
	
	@Parameters({"baseURI"})
	@BeforeTest
	public void setUp(String baseURI) {
		RestAssured.filters(new AllureRestAssured());			
		config = new Configuration();
		prop = config.initProperties();				
		this.baseURI = baseURI;		
	}
	
	

}
