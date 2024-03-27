package com.qa.api.client;

import java.util.Map;
import java.util.Properties;

import com.qa.api.FrameworkException.APIException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*; 

public class restClient {	
	private boolean isAuthorizationHeaderAdded = false;

	private static RequestSpecBuilder specBuilder;
	
	private Properties prop;
	private String baseURI;
	
	public restClient(Properties prop, String baseURI){
		specBuilder = new RequestSpecBuilder();
		this.prop=prop;
		this.baseURI=baseURI;
	}

	private static void setRequestContentType(String contentType) {
		switch (contentType) {
		case "JSON":
			System.out.println("Content Type is JSON");
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "TEXT":
			System.out.println("Content Type is TEXT");
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "HTML":
			System.out.println("Content Type is HTML");
			specBuilder.setContentType(ContentType.HTML);
			break;
		case "MULTIPART":
			System.out.println("Content Type is MULTIPART");
			specBuilder.setContentType(ContentType.MULTIPART);
			break;
		case "URLENC":
			System.out.println("Content Type is URLENC");
			specBuilder.setContentType(ContentType.URLENC);
			break;
		case "XML":
			System.out.println("Content Type is XML");
			specBuilder.setContentType(ContentType.XML);
			break;
		default:
			System.out.println("No Content Type is passed");
			throw new APIException("Please pass the correct Content Type");
		}
	}

	private void addAuthHeader() {
		if(!isAuthorizationHeaderAdded) {
		specBuilder.addHeader("Authorization", prop.getProperty("tokenID"));
		isAuthorizationHeaderAdded = true;
		}
	}

	private RequestSpecification createRequestSpecification(boolean isAuth) {
		specBuilder.setBaseUri(baseURI);		
		if(isAuth) {
			addAuthHeader();
			}		
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpecification(Map<String, String> headersMap, boolean isAuth) {
		specBuilder.setBaseUri(baseURI);
		if(isAuth) {
			addAuthHeader();
			}	
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}
	
	private RequestSpecification createRequestSpecificationWithathParms(Map<String, String> pathParamsMap, boolean isAuth) {
		specBuilder.setBaseUri(baseURI);
		if(isAuth) {
			addAuthHeader();
			}	
		if (pathParamsMap != null) {
			specBuilder.addPathParams(pathParamsMap);
		}
		return specBuilder.build();
	}
	

	private RequestSpecification createRequestSpecification(Map<String, String> headersMap,
			Map<String, Object> queryParams, boolean isAuth) {
		specBuilder.setBaseUri(baseURI);
		if(isAuth) {
			addAuthHeader();
			}	
		if (queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}

	// RequestSpecification for Content Type
	private RequestSpecification createRequestSpecification(Object requestBody, String contentType,boolean isAuth ) {
		specBuilder.setBaseUri(baseURI);
		if(isAuth) {
			addAuthHeader();
		}	
		setRequestContentType(contentType);
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpecification(Object requestBody, String contentType,
		Map<String, String> headersMap, boolean isAuth) {
		specBuilder.setBaseUri(baseURI);
		if(isAuth) {
			addAuthHeader();
		}
		setRequestContentType(contentType);
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}
	
	public String getAccessTokenViaOAuth2(String serviceURL, String grantType, String client_id, String client_secret){
		RestAssured.baseURI = "https://test.api.amadeus.com";

		String accessToken = given().log().all()
				.contentType(ContentType.URLENC)
				.formParam("grant_type", grantType)
				.formParam("client_id", client_id)
				.formParam("client_secret", client_secret)
				.when()
				.post(serviceURL)
				.then()
				.assertThat().statusCode(200).extract().path("access_token");
		System.out.println("The Access Token is : " +accessToken);
		return accessToken;
	}

	// HTTP Method Utilities
	// ***************GET Calls*********************************
	public Response getMethod(String serviceURL, boolean log,boolean isAuth ) {
		if (log) {
			return given().spec(createRequestSpecification(isAuth)).log().all().when().get(serviceURL);
		}
		return given().spec(createRequestSpecification(isAuth)).when().get(serviceURL);
	}

	public Response getMethod(String serviceURL, Map<String, String> headersMap, boolean log,boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(headersMap,isAuth)).log().all().when().get(serviceURL);
		}
		return given().spec(createRequestSpecification(headersMap,isAuth)).when().get(serviceURL);
	}

	public Response getMethod(String serviceURL, Map<String, String> headersMap, Map<String, Object> queryParams,
			boolean log, boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(headersMap, queryParams,isAuth)).log().all().when().get(serviceURL);
		}
		return given().spec(createRequestSpecification(headersMap, queryParams,isAuth)).when().get(serviceURL);
	}	
	
	public Response getMethodWithPathParams(String serviceURL, Map<String, String> pathParamsMap, boolean log, boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecificationWithathParms(pathParamsMap,isAuth)).log().all().when().get(serviceURL);
		}
		return given().spec(createRequestSpecificationWithathParms(pathParamsMap,isAuth)).when().get(serviceURL);
	}

	// ***************POST Calls*********************************
	public Response postMethod(String serviceURL, Object requestBody, String contentType,
			Map<String, String> headersMap, boolean log,boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(requestBody, contentType, headersMap,isAuth)).log().all().when()
					.post(serviceURL);
		}
		return given().spec(createRequestSpecification(requestBody, contentType, headersMap,isAuth)).when().post(serviceURL);
	}

	public Response postMethod(String serviceURL, Object requestBody, String contentType, boolean log,boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(requestBody, contentType, isAuth)).log().all().when()
					.post(serviceURL);
		}
		return given().spec(createRequestSpecification(requestBody, contentType,isAuth)).when().post(serviceURL);
	}

	// ***************PUT Calls*********************************
	public Response putMethod(String serviceURL, Object requestBody, String contentType, Map<String, String> headersMap,
			boolean log, boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(requestBody, contentType, headersMap,isAuth)).log().all().when()
					.put(serviceURL);
		}
		return given().spec(createRequestSpecification(requestBody, contentType, headersMap,isAuth)).when().put(serviceURL);
	}

	public Response putMethod(String serviceURL, Object requestBody, String contentType, boolean log, boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(requestBody, contentType,isAuth)).log().all().when()
					.put(serviceURL);
		}
		return given().spec(createRequestSpecification(requestBody, contentType,isAuth)).when().put(serviceURL);
	}

	// ***************PATCH Calls*********************************
	public Response patchMethod(String serviceURL, Object requestBody, String contentType,
			Map<String, String> headersMap, boolean log, boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(requestBody, contentType, headersMap,isAuth)).log().all().when()
					.patch(serviceURL);
		}
		return given().spec(createRequestSpecification(requestBody, contentType, headersMap,isAuth)).when().patch(serviceURL);
	}

	public Response patchMethod(String serviceURL,Object requestBody, String contentType, boolean log, boolean isAuth) {
		if (log) {
			return given().spec(createRequestSpecification(requestBody,contentType,isAuth)).log().all()	
			.when().patch(serviceURL);
		}
		return given().spec(createRequestSpecification(requestBody,contentType,isAuth)).when().patch(serviceURL);
	}
		
	//***************DELETE Calls*********************************		
	
	public Response deleteMethod(String serviceURL,boolean log,boolean isAuth) {
		if (log) {
				return given().spec(createRequestSpecification(isAuth)).log().all()	
				.when().delete(serviceURL);
			}
		return given().spec(createRequestSpecification(isAuth)).when().delete(serviceURL);	
	}		
		
}


