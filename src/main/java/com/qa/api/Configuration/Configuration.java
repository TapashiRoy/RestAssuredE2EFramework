package com.qa.api.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.api.FrameworkException.APIException;

public class Configuration {
	private Properties prop;
	private FileInputStream ip;

	public Properties initProperties() {
		prop = new Properties();
		// Env details can be passed from Maven command line arguments
		// mvn clean install -Denv="QA"
		String envName = System.getProperty("env");
		try {
			if (envName == null) {
				System.out.println("No environment Details passed");
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} else {
				System.out.println("Running TCs on " + envName + " environment");

				switch (envName.toUpperCase().trim()) {
				case "QA":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "DEV":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				default:
					System.out.println("Running with default configurations");
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
