package com.flipkart.qa.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.flipkart.qa.base.TestBase;


public class SeleniumUtils {
	
	private static Logger log = Logger.getLogger(SeleniumUtils.class);

	public boolean isDisplayed(WebElement element) {
		try {
			log.info("element is displayed " + element.getText());
			TestBase.logExtentReport("element is displayed " + element.getText());
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			log.error("element is not displayed ", e.getCause());
			TestBase.logExtentReport("element is not displayed " + e.getMessage());
			return false;
		}
	}
}
