package com.flipkart.qa.pages;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.util.SeleniumUtils;

public class SearchResultPage extends TestBase {

	private static final Logger log = Logger.getLogger(SearchResultPage.class.getName());

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/h1")
	WebElement categoryText;

	@FindBy(xpath = "//*[@class='col col-7-12']/div[1]")
	List<WebElement> productList;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div/div/div/div/a/div[2]/div[2]/div[1]/div/div[1]")
	List<WebElement> productOfferPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div/div/div/div/a/div[2]/div[2]/div[1]/div/div[2]")
	List<WebElement> productOrigninalPrice;

	@FindBy(css = "a[class^='_2mylT6']")
	List<WebElement> productList1;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div/div/div/div/div/a[2]/div/div[1]")
	List<WebElement> productOfferPrice1;

	@FindBy(xpath = "//*[@id='container']/div/div[3]/div[2]/div[1]/div[2]/div/div/div/div/div/a[2]/div/div[2]")
	List<WebElement> productOrigninalPrice1;

	public SearchResultPage() {
		PageFactory.initElements(driver, this);
	}

	public HashMap<String, String> printProductName(String productName) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(prop.getProperty("implicitwait")));
		wait.until(ExpectedConditions.visibilityOf(categoryText));
		HashMap<String, List<WebElement>> map = elementSize();

		int totalProductsize = map.get("pList").size();
	//	System.out.println("totalProductsize " + totalProductsize);
		Thread.sleep(2000);
		HashMap<String, String> map1 = new HashMap<String, String>();
		for (int i = 0; i < totalProductsize; i++) {
			String products = map.get("pList").get(i).getText();
	//		System.out.println(i + " " + products);
			if (products.equals(productName)) {
				log("Getting Search Result Page Product(" + productName + ") Offer Price");
				String offerPrice = map.get("pOfferPrice").get(i).getText().replace("₹", "");
				String originalPrice = map.get("pOrigninalPrice").get(i).getText().replace("₹", "");
			//	System.out.println("Offer Price " + offerPrice + " Original Price " + originalPrice);
				map1.put("OfferPrice", offerPrice);
				map1.put("OriginalPrice", originalPrice);
				log("Clicking " + productName + " from search results");
				map.get("pList").get(i).click();
				break;
			}
		}
		return map1;
	}

	public void verifySearchResultandProjOverviewPricesSame(String productName) throws InterruptedException {
		HashMap<String, String> map = printProductName(productName);
		String offerPrice = map.get("OfferPrice");
		String originalPrice = map.get("OriginalPrice");
		new SeleniumUtils().switchToWindow(2);
		String productPageOfferPrice = new ProductOverviewPage().getofferPrice();
		String productPageOriginalPrice = new ProductOverviewPage().getoriginalPrice();
		log("Verifiying Search result page Product Offer price " + offerPrice + " with product overview page Offer price "
				+ productPageOfferPrice);
		Assert.assertEquals(offerPrice, productPageOfferPrice);
		log("Verifiying Search result page Product Original price " + originalPrice
				+ " with product overview page Offer price " + productPageOriginalPrice);
		Assert.assertEquals(originalPrice, productPageOriginalPrice);

	}

	public HashMap<String, List<WebElement>> elementSize() {

		HashMap<String, List<WebElement>> map = new HashMap<String, List<WebElement>>();
		if (productList.size() > 0) {
			// System.out.println("productList Size " + productList.size());
			map.put("pList", productList);
			map.put("pOfferPrice", productOfferPrice);
			map.put("pOrigninalPrice", productOrigninalPrice);
			return map;
		} else {
			// System.out.println("productList1 Size " + productList1.size());
			map.put("pList", productList1);
			map.put("pOfferPrice", productOfferPrice1);
			map.put("pOrigninalPrice", productOrigninalPrice1);
			return map;
		}
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}
}
