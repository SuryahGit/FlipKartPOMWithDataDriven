package com.flipkart.qa.testcases.homepage;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.pages.HomePage;

public class LoginTestValidInput extends TestBase {

	private static final Logger log = Logger.getLogger(LoginTestValidInput.class.getName());
	HomePage homepage;

	/*
	 * public LoginTestValidInput() { super(); }
	 */

	@DataProvider(name = "testData")
	public Object[][] testData() {
		Object[][] data = getExcelData("testData.xlsx", "LoginData");
		return data;
	}

	@BeforeClass
	public void setup() {
		init();
		homepage = new HomePage();
	}

	@Test(dataProvider = "testData")
	public void loginTestWithValidInput(String userName, String password, String runMode) throws InterruptedException {
		if (runMode.equalsIgnoreCase("N")) {
			throw new SkipException("Run mode for data set is N ");
		}

		// homepage.loginToApplication(prop.getProperty("emailId"),
		// prop.getProperty("password"));
		homepage.loginToApplication(userName, password);
		boolean status = homepage.verifySuccessLoginMsg();
		Assert.assertTrue(status);
		homepage.logOutToApplication();

	}

	public void log(String data) {
		log.info(data);
	}

}
