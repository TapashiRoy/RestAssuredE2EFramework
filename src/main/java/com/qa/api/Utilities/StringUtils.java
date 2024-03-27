package com.qa.api.Utilities;

public class StringUtils {
	
	public static String getRandomEmailGenerator() {
		String email = "APIAutomation" + System.currentTimeMillis() +"@gmail.com";
		return email;
	}

}
