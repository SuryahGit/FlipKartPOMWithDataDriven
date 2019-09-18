package com.flipkart.qa.util;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.flipkart.qa.base.TestBase;


public class SeleniumUtils extends TestBase{
	
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
	
	public void switchToWindow(int index) {

		Set<String> windows = driver.getWindowHandles();
		int i = 1;
		for (String window : windows) {
			if (i == index) {
				log.info("switching to " + index + " window");
				driver.switchTo().window(window);
			} else {
				i++;
			}
		}
	}
}
