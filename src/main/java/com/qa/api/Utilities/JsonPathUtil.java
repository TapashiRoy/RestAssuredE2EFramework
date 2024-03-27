package com.qa.api.Utilities;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.api.FrameworkException.APIException;

import io.restassured.response.Response;

public class JsonPathUtil {

	private String getJsonResponseAsString(Response response) {
        return response.getBody().asString();
	}
	
	public <T> T read(Response response, String jsonPath) {
		String jsonResponse =  getJsonResponseAsString(response);
        try {
        	return JsonPath.read(jsonResponse, jsonPath);
        }
        catch(PathNotFoundException e) {
        	e.printStackTrace();
        	throw new APIException(jsonPath + "not found...");
        }
	}
	
	public <T> List<T> readList(Response response, String jsonPath) {
		String jsonResponse =  getJsonResponseAsString(response);
        try {
        	return JsonPath.read(jsonResponse, jsonPath);
        }
        catch(PathNotFoundException e) {
        	e.printStackTrace();
        	throw new APIException(jsonPath + "not found...");
        }
	}
	
	
	public <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) {
		String jsonResponse =  getJsonResponseAsString(response);
        try {
        	return JsonPath.read(jsonResponse, jsonPath);
        }
        catch(PathNotFoundException e) {
        	e.printStackTrace();
        	throw new APIException(jsonPath + "not found...");
        }
	}
}
