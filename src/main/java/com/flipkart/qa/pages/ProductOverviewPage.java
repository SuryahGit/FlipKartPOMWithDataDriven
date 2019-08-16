package com.flipkart.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;

public class ProductOverviewPage extends TestBase {

	private static final Logger log = Logger.getLogger(ProductOverviewPage.class.getName());

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[1]/h1/span")
	WebElement prodcutName;
	
	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[1]/div[2]/div/ul/li[1]/button")
	WebElement addToCartBtn;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[4]/div[1]/div/div[1]")
	WebElement offerPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[4]/div[1]/div/div[2]")
	WebElement originalPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[1]/div[2]/div/ul/li[2]/form/button")
	WebElement buyNowBtn;

	public ProductOverviewPage() {
		PageFactory.initElements(driver, this);
	}

	public String getofferPrice() {
		log("Getting Product Overview Page Product Offer Price");
		return offerPrice.getText().replace("₹", "");
	}

	public String getoriginalPrice() {
		log("Getting Product Overview Page Product Original Price");
		return originalPrice.getText().replace("₹", "");
	}

	public ViewCartPage addcart() {
		log("clicking add to cart button");
		addToCartBtn.click();
		return new ViewCartPage();
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}
}
