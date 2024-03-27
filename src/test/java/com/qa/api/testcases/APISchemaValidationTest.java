package com.qa.api.testcases;

import org.testng.annotations.BeforeMethod;

import com.qa.api.base.BaseTest;
import com.qa.api.client.restClient;

public class APISchemaValidationTest extends BaseTest   {
	
	@BeforeMethod
	public void getUserSetup() {
		rc = new restClient(prop, baseURI);
	}

}
