Don't forget to give a ⭐ if you find the project useful.

This project is the outcome of my self-learning about the API Testing Automation framework - Rest-assured.I started doing some handson and explored more about its core features and other helper libraries. I started learning about the framework and have documented all my learnings in this repository with all example code from writing basic tests to running end to end API automation tests.

Tech Stack:
1. JDK 17
2. Maven Build tool
3. TestNG for test runner and parallel execution
4. Allure Report for reporting
5. CI/CD pipeline using Docker and Jenkins

This repo contains RestAssured codes, in addition to -Hamcrest Matchers helpful for writing testng assertions, Lombok is used to generate Getters and Setters automatically for requests, POJO classes are used for Serialization and De-Serialization, Utilities classes have been used for handling JSONPath, XMLPath, Excel etc, Jackson and Jayway APIs are used for Validation.

🚀 Getting Started 🚧 Prerequisites Before you can run this project, you must have the following software installed on your computer:

Java Development Kit (JDK) version 11 or later Apache Maven 

🔗 Dependencies This project uses the following dependencies:
TestNG version 6.14.3 
io.rest-assured version 5.4.0
jackson-databind version 2.16.2
jaywayjsonPath version 2.9.0
lombok version 1.18.30
org-apache-poi version 5.2.3 
com.aventstack.extentreport version 5.1.0 
io.qameta.allure version 2.12.0 

🛠️ Installation Clone this repository to your local machine. git clone https://github.com/TapashiRoy/RestAssuredE2EFramework.git
Navigate to the project directory using the command line. Install the dependencies and run mvn clean install

🌐 APIs under the scope of this project are -
1. https://gorest.co.in/public/v2/users
2. http://ergast.com//api/f1
3. https://test.api.amadeus.com/v1/security/oauth2/token
4. https://test.api.amadeus.com/v1/shopping/flight-destinations

Note that this APIs are being used for testing purposes, and I, the tester, acknowledge that I do not own or have any rights to this URLs. Testing activities are for demo purposes only.

👨🏼‍🔬Tests This project contains 7 sample test classes that demonstrate the E2E API functionality. All the assertions are maintained under Tests.

🧪 Test classes -

APISchemaValidationTest - contains TC for the Schema Validation of the API 
CreateUserTest - Contains TCs for creating user and passing the payload via instant data generation using DataProvider AND excel data using DataProvider AND via POJO
DeleteUserTest -  Contains TCs for E2E validation for user data
GetCircuitDataTest - Contains TCs for getting circuit 
GetFlightBookingTest - Contains TCs for retreiving Flight Bookings
GetUserTest - contains TCs for retrieving Users.
UpdateUserTest - Contains TCs for Updating User.

📝 Test Runners
testng_sanity.xml: Includes one test case that covers the basic functionality of the web app. 
testng_regression.xml: Includes set of test cases that checks for overall stability and functionality of the existing features.

🏃🏽How to run the tests 🚦 Running a test case Navigate to the project directory using command line. Run the following command but replace <test_case> with the name of the test case. mvn test -Dtest=<test_case> E.g. mvn test -Dtest=TC004_Logout 🚦 Running a test plan Navigate to the project directory using command line. Run the following command but replace <test_plan> with the file path of the test suite xml files. mvn test -DsuiteXmlFiles=<test_plan>



