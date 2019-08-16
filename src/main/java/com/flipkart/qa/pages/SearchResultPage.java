package com.flipkart.qa.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.util.ExplicitWait;

public class SearchResultPage extends TestBase {

	private static final Logger log = Logger.getLogger(SearchResultPage.class.getName());

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/h1")
	WebElement categoryText;

	@FindBy(xpath = "//*[@class='col col-7-12']/div[1]")
	List<WebElement> productList;

	// *[@id="container"]/div/div[3]/div[2]/div[1]/div[2]/div[25]/div/div/div/a/div[3]/div[2]/div[1]/div/div[2]
	// *[@id="container"]/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div/a/div[3]/div[2]/div[1]/div/div[2]
	public SearchResultPage() {
		PageFactory.initElements(driver, this);
	}

	public HashMap<String, String> printProductName(String productName) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(prop.getProperty("implicitwait")));
		wait.until(ExpectedConditions.visibilityOf(categoryText));
		int totalProductsize = productList.size();
		// System.out.println("totalProductsize" + totalProductsize);
		Thread.sleep(5000);
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < totalProductsize; i++) {
			String products = productList.get(i).getText();
			// System.out.println(i + " " + products);
			if (products.equals(productName)) {
				log("Getting Search Result Page Product(" + productName + ") Offer Price");
				String offerPrice = driver
						.findElement(By.xpath("//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[" + (i + 2)
								+ "]/div/div/div/a/div[3]/div[2]/div[1]/div/div[1]"))
						.getText().replace("₹", "");
				log("Getting Search Result Page Product(" + productName + ") Original Price");
				String originalPrice = driver
						.findElement(By.xpath("//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[" + (i + 2)
								+ "]/div/div/div/a/div[3]/div[2]/div[1]/div/div[2]"))
						.getText().replace("₹", "");
				// System.out.println("Offer Price " + offerPrice + " Original Price " +
				// originalPrice);
				map.put("OfferPrice", offerPrice);
				map.put("OriginalPrice", originalPrice);
				log("Clicking " + productName + " from search results");
				productList.get(i).click();
				break;
			}
		}
		return map;
	}

	public void verifySearchResultandProjOverviewPricesSame(String productName) throws InterruptedException {
		HashMap<String, String> map = printProductName(productName);
		String offerPrice = map.get("OfferPrice");
		String originalPrice = map.get("OriginalPrice");
	//	new ExplicitWait().waitForElementToClickable(new ProductOverviewPage().addToCartBtn, explicitWait);
		switchToWindow(2);
		String productPageOfferPrice = new ProductOverviewPage().getofferPrice();
		String productPageOriginalPrice = new ProductOverviewPage().getoriginalPrice();
		log("Verifiying Search result page Product Offer price " + offerPrice + " with product overview Offer price "
				+ productPageOfferPrice);
		Assert.assertEquals(offerPrice, productPageOfferPrice);
		log("Verifiying Search result page Product Original price " + originalPrice
				+ " with product overview Offer price " + productPageOriginalPrice);
		Assert.assertEquals(originalPrice, productPageOriginalPrice);

	}

	public void switchToWindow(int index) {

		Set<String> windows = driver.getWindowHandles();
		int i = 1;
		for (String window : windows) {
			if (i == index) {
				log("switching to " + index + " window");
				driver.switchTo().window(window);
			} else {
				i++;
			}
		}
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}
}
