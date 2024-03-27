package com.qa.api.Utilities;

import java.util.List;
import java.util.Map;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLPathUtil {
	private XmlPath getXmlPath(Response response) {
		String rs = response.body().asString();
		return new XmlPath(rs);
	}

	public <T> T read(Response response, String xmlPath) {
		XmlPath xmlpath = getXmlPath(response);
		return xmlpath.get(xmlPath);
	}

	public <T> List<T> readList(Response response, String xmlPath) {
		XmlPath xmlpath = getXmlPath(response);		
		return xmlpath.getList(xmlPath);		
	}

	//Rarely used
	public <T> List<Map<String, T>> readListOfMaps(Response response,String xmlPath) {
		XmlPath xmlpath = getXmlPath(response);		
		return xmlpath.getList(xmlPath);	
	}
	
}
