package com.flipkart.qa.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.util.ExplicitWait;

public class ViewCartPage extends TestBase {

	private static final Logger log = Logger.getLogger(ViewCartPage.class.getName());
	public String productPageOfferPrice;
	public String productPageOriginalPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div[1]/div[1]/div[1]/a")
	WebElement productNameLbl;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div[1]/div[1]/span[1]")
	WebElement offerPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div[1]/div[1]/span[2]")
	WebElement originalPrice;

	@FindBy(xpath = "//span[contains(text(),'Place Order')]")
	WebElement placeOrderBtn;

	@FindBy(xpath = "//*[contains(text(), 'Price (')]")
	WebElement totalItems;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div/div/div[1]/div[1]/span[1]")
	List<WebElement> offerPrices;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div/div/div[1]/div[1]/span[2]")
	List<WebElement> originalPrices;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[2]/div[1]/div/div/div[1]/div[3]/div/span")
	WebElement totalPayablePrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[2]/div[1]/div/div/div[1]/div[2]/span")
	WebElement deliveryPrice;

	@FindBy(xpath = "//*[@id='container']/div/div[2]/div[2]/div/div[1]/div/div/div/div[1]/div[1]/div[1]/a")
	List<WebElement> myCartTotalProrducts;

	@FindBy(xpath = "//*[contains(text(),'You will save')]")
	WebElement YouWillSavePrice;

	public ViewCartPage() {
		PageFactory.initElements(driver, this);
	}

	public ViewCartPage(String OfferPrice, String OriginalPrice) {
		this();
		productPageOfferPrice = OfferPrice;
		productPageOriginalPrice = OriginalPrice;
		/*System.out.println(productPageOfferPrice + " constructor " + productPageOriginalPrice);
		System.out.println(productPageOfferPrice + " constructor " + productPageOriginalPrice);*/
	}

	public String[] getofferPrice() {
		log("Getting View Cart Page Product Offer Price");

		int priceSize = offerPrices.size();
		String[] price = new String[priceSize - 1];
		for (int i = 1; i <= price.length; i++) {
			price[i - 1] = offerPrices.get(i).getText().replaceAll("[^a-zA-Z0-9]", "");
		//	System.out.println("My Cart Product Offer Price " + price[i - 1]);
		}
		return price;
	}

	public String[] getoriginalPrice() {

		log("Getting View Cart Page Product Original Price");
		int priceSize = originalPrices.size();
		String[] price = new String[priceSize];
		for (int i = 0; i < price.length; i++) {
			price[i] = originalPrices.get(i).getText().replaceAll("[^a-zA-Z0-9]", "");
			//System.out.println("My Cart Product Original Price " + price[i]);
		}
		return price;
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
		for (int i = 1; i < priceSize; i++) {
			String price = offerPrices.get(i).getText().replaceAll("[^a-zA-Z0-9]", "");
		//	System.out.println("My Cart Product Offer Price " + price);
			getMyCartTotalPrice = getMyCartTotalPrice + Integer.parseInt(price);
		}

		return getMyCartTotalPrice;
	}

	public int getdeliveryPrice() {

		String dPrice = deliveryPrice.getText();
		if (dPrice.equalsIgnoreCase("Free")) {
			//System.out.println("Delivery Price is " + dPrice);
			return 0;
		} else {

			int dPri = Integer.parseInt(dPrice.replace("₹", ""));
			//System.out.println("Delivery Price is " + dPri);
			return dPri;
		}

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
			String price = originalPrices.get(i).getText().replaceAll("[^a-zA-Z0-9]", "");
			//System.out.println("My Cart Product Orinal Price " + price);
			getMyCartTotalOrinalPrice = getMyCartTotalOrinalPrice + Integer.parseInt(price);
		}

		return getMyCartTotalOrinalPrice;
	}

	public int getMyCartSavingPrice() {
		log("Getting View Cart Page My Added Products Total Saving Price");
		int getMyCartTotalofferPrice = getMyCartTotalOfferPrices();
		int getMyCartTotalOrinalPrice = getMyCartTotalOriginalPrices();
		int dPrice = getdeliveryPrice();
		// System.out.println("Saving Price
		// "+(getMyCartTotalOrinalPrice-getMyCartTotalofferPrice));
		return getMyCartTotalOrinalPrice - getMyCartTotalofferPrice - dPrice;
	}

	public int getYouWillSavePrice() {
		log("Getting View Cart Page You wil Save Price");
		String YouSavePrice = YouWillSavePrice.getText();
		// System.out.println("Text "+YouSavePrice);
		String[] s1 = YouSavePrice.replace("You will save ₹", "").replace(",", "").split(" ", 2);
		// System.out.println(s1[0]);
		return Integer.parseInt(s1[0]);
	}

	public void verifyProjOverviwandViewCarePricesSame() throws InterruptedException {

		new ExplicitWait().waitForWebElementToVisible(placeOrderBtn, explicitWait);
		String[] cartPageOfferPrice = getofferPrice();
		String[] cartPageOriginalPrice = getoriginalPrice();
		for (int i = 0; i < cartPageOfferPrice.length; i++) {
			String cartpageOfferPrice = cartPageOfferPrice[i];
			if (cartpageOfferPrice.equalsIgnoreCase(productPageOfferPrice)) {
			//	System.out.println(productPageOfferPrice + " Test " + cartpageOfferPrice);
				log("Verifiying Product overview page Offer price " + productPageOfferPrice
						+ " with ViewCart Page Offer price " + cartpageOfferPrice);
				Assert.assertEquals(cartpageOfferPrice, productPageOfferPrice);
				break;
			}
		}
		for (int i = 0; i < cartPageOfferPrice.length; i++) {
			String cartpageOriginalPrice = cartPageOriginalPrice[i];
			if (cartpageOriginalPrice.equalsIgnoreCase(productPageOriginalPrice)) {
	//			System.out.println(productPageOriginalPrice + " Test " + cartpageOriginalPrice);
				log("Verifiying Product overview page Original price " + productPageOriginalPrice
						+ " with ViewCart Page Original price " + cartpageOriginalPrice);
				Assert.assertEquals(cartpageOriginalPrice, productPageOriginalPrice);
				break;
			}
		}
	}

	public void verifyProductItemCounts() {
		int totalItems = getTotalItems();
		int myCartTotalProrducts = getMyCartTotalProducts();
		log("Verifiying view page added My cart Total product count " + myCartTotalProrducts
				+ " with PRICE DETAILS total Items count " + totalItems);
		Assert.assertEquals(myCartTotalProrducts, totalItems);
	}

	public void veryBothSideTotalProductPrice() {
		int myCartTotalPrice = getMyCartTotalOfferPrices();
		int totalPayablePrice = gettotalPayablePrice();
		int delPrice = getdeliveryPrice();
		//System.out.println("MY Cart Total Price Includes Delivery " + (myCartTotalPrice + delPrice));
		log("Verifiying view page added My Cart Total Price " + (myCartTotalPrice + delPrice)
				+ " with My Cart Total Payable Price " + totalPayablePrice);
		Assert.assertEquals((myCartTotalPrice + delPrice), totalPayablePrice);
	}

	public void verySavingPrice() {
		int mycartSavePrice = getMyCartSavingPrice();
		int youWillSavePrice = getYouWillSavePrice();
		log("Verifiying view page added My Cart Save Price " + mycartSavePrice
				+ " with PRICE DETAILS You Will Save Price " + youWillSavePrice);
		Assert.assertEquals(mycartSavePrice, youWillSavePrice);
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}

}
