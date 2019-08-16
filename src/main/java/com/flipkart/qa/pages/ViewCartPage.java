package com.flipkart.qa.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;

public class ViewCartPage extends TestBase {
	
	private static final Logger log = Logger.getLogger(ViewCartPage.class.getName());
	
	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div[1]/div[1]/div[1]/a")
	WebElement productNameLbl;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div[1]/div[1]/span[1]")
	WebElement offerPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div[1]/div[1]/span[2]")
	WebElement originalPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[3]/div/form/button")
	WebElement placeOrderBtn;

	@FindBy(xpath = "//*[contains(text(), 'Price (')]")
	WebElement totalItems;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div/div/div[1]/div[1]/span[1]")
	List<WebElement> offerPrices;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div/div/div[1]/div[1]/span[2]")
	List<WebElement> originalPrices;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[2]/div[1]/div/div/div[1]/div[3]/div/span")
	WebElement totalPayablePrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div/div/div[1]/div[1]/div[1]/a")
	List<WebElement> myCartTotalProrducts;

	@FindBy(xpath = "//*[contains(text(),'You will save')]")
	WebElement YouWillSavePrice;

	public ViewCartPage() {
		PageFactory.initElements(driver, this);
	}

	public String getofferPrice() {
		log("Getting View Cart Page Product Offer Price");
		return offerPrice.getText().replace("₹", "");
	}

	public String getoriginalPrice() {
		
		log("Getting View Cart Page Product Original Price");
		return originalPrice.getText().replace("₹", "");
	}

	public void clickPlaceOrderBtn() {
		log("clicking place order button");
		placeOrderBtn.click();
	}

	public int getTotalItems() {
		log("Getting Total Items Text");
		String toItems = totalItems.getText();
		// System.out.println(toItems);
		String[] newtoItems = toItems.replace("Price (", "").split(" ", 2);
		// System.out.println("Total Items "+newtoItems[0]);
		return Integer.parseInt(newtoItems[0]);
	}

	public int getMyCartTotalOfferPrices() {
		log("Getting View Cart Page My Added Products Total Offer Price");
		int priceSize = offerPrices.size();

		int getMyCartTotalPrice = 0;
		for (int i = 0; i < priceSize - 1; i++) {
			String price = driver.findElement(By.xpath("//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div["
					+ (i + 2) + "]/div/div[1]/div[1]/span[1]")).getText().replaceAll("[^a-zA-Z0-9]", "");
			getMyCartTotalPrice = getMyCartTotalPrice + Integer.parseInt(price);
		}

		return getMyCartTotalPrice;
	}

	public int gettotalPayablePrice() {
		log("Getting View Cart Page Total Payable Price");

		return Integer.parseInt(totalPayablePrice.getText().replaceAll("[^a-zA-Z0-9]", ""));
	}

	public int getMyCartTotalProducts() {
		
		log("Getting View Cart Page My Total Products Size");
		return myCartTotalProrducts.size();
	}

	public int getMyCartTotalOriginalPrices() {
		log("Getting View Cart Page My Added Products Total Orinal Price");
		int priceSize = originalPrices.size();

		int getMyCartTotalOrinalPrice = 0;
		for (int i = 0; i < priceSize; i++) {
			String price = driver.findElement(By.xpath("//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div["
					+ (i + 2) + "]/div/div[1]/div[1]/span[2]")).getText().replaceAll("[^a-zA-Z0-9]", "");
			getMyCartTotalOrinalPrice = getMyCartTotalOrinalPrice + Integer.parseInt(price);
		}

		return getMyCartTotalOrinalPrice;
	}

	public int getMyCartSavingPrice() {
		log("Getting View Cart Page My Added Products Total Saving Price");
		int getMyCartTotalofferPrice = getMyCartTotalOfferPrices();
		int getMyCartTotalOrinalPrice = getMyCartTotalOriginalPrices();
		// System.out.println("Saving Price
		// "+(getMyCartTotalOrinalPrice-getMyCartTotalofferPrice));
		return getMyCartTotalOrinalPrice - getMyCartTotalofferPrice;
	}

	public int getYouWillSavePrice() {
		log("Getting View Cart Page You wil Save Price");
		String YouSavePrice = YouWillSavePrice.getText();
		//System.out.println("Text "+YouSavePrice);
		String[] s1 = YouSavePrice.replace("You will save ₹", "").replace(",", "").split(" ", 2);
	//	System.out.println(s1[0]);
		return Integer.parseInt(s1[0]);
	}
	
	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}
}
