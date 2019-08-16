package com.flipkart.qa.util;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.flipkart.qa.base.TestBase;

public class ExplicitWait extends TestBase {

	private static Logger log = Logger.getLogger(ExplicitWait.class);

	/**
	 * This method will make sure element is visible
	 * 
	 * @param element
	 * @param timeOutInSeconds
	 */
	public void waitForWebElementToVisible(WebElement element, int timeOutInSeconds) {
		log.info("waiting for :" + element.getText() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("element is visible now");
	}

	/**
	 * This method will make sure elementToBeClickable
	 * 
	 * @param element
	 * @param timeOutInSeconds
	 */
	public void waitForElementToClickable(WebElement element, int timeOutInSeconds) {
		log.info("waiting for :" + element.toString() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.info("element is clickable now");
	}

	/**
	 * This method will make sure to wait till page load
	 * 
	 * @param timeOutInSeconds
	 * @param unit
	 */
	public void pageLoadTime(long timeOutInSeconds, TimeUnit unit) {
		log.info("Waiting for page to load for :" + timeOutInSeconds + " seconds");
		driver.manage().timeouts().pageLoadTimeout(timeOutInSeconds, unit);
		log.info("Page is loaded");
	}

	/**
	 * This is ImplicitWait Method
	 * 
	 * @param timeOutInSeconds
	 * @param unit
	 */
	public void setImplicitWait(long timeOutInSeconds, TimeUnit unit) {
		log.info("ImplicitWait has been set to :" + timeOutInSeconds);
		driver.manage().timeouts().implicitlyWait(timeOutInSeconds, unit);
	}

}
