package com.flipkart.qa.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.util.SeleniumUtils;

public class ProductOverviewPage extends TestBase {

	private static final Logger log = Logger.getLogger(ProductOverviewPage.class.getName());

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[1]/h1/span")
	WebElement prodcutName;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[1]/div[2]/div/ul/li[1]/button")
	WebElement addToCartBtn;

	@FindBy(xpath = "//div[@class='_1vC4OE _3qQ9m1']")
	WebElement offerPrice;

	@FindBy(xpath = "//div[@class='_3auQ3N _1POkHg']")
	WebElement originalPrice;

	/*@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[4]/div[1]/div/div[1]")
	WebElement offerPrice1;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[4]/div[1]/div/div[2]")
	WebElement originalPrice1;
*/
	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[1]/div[2]/div/ul/li[2]/form/button")
	WebElement buyNowBtn;

	@FindBy(xpath = "//span[@id='Size- UK/India']")
	WebElement selectSize;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[5]/div/div[2]/div[1]/ul/li")
	List<WebElement> selectSizes;

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

	public ViewCartPage addcart() throws InterruptedException {
		log("clicking add to cart button");
		String getofferPrice = getofferPrice();
		String getoriginalPrice = getoriginalPrice();
		boolean result = new SeleniumUtils().isDisplayed(selectSize);
		if (result) {
			for (int i = 0; i < selectSizes.size(); i++) {
				String sizeText = selectSizes.get(i).getText();
				String selectSize = "10";
				// System.out.println("Displaying Sizes " + sizeText);
				if (sizeText.equalsIgnoreCase(selectSize)) {
					Thread.sleep(2000);
					System.out.println("Selecting Shoe size " + sizeText);
					selectSizes.get(i).click();
					break;
				}
			}
			addToCartBtn.click();
		} else {
			addToCartBtn.click();
		}
		return new ViewCartPage(getofferPrice, getoriginalPrice);
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}
}
