package com.flipkart.qa.testcases.productCategoryPage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.pages.HomePage;
import com.flipkart.qa.pages.ProductOverviewPage;
import com.flipkart.qa.pages.SearchResultPage;
import com.flipkart.qa.pages.ViewCartPage;
import com.flipkart.qa.util.SeleniumUtils;

public class AddingMultipleProjectToCartTest extends TestBase {

	HomePage homepage;
	SearchResultPage searchResultPage;
	ProductOverviewPage productOverviewPage;
	ViewCartPage viewCartPage;
	String mainMenuText = "Electronics";
	String subMainMenuText = "Mi";
	String productName = "Redmi Note 7 Pro (Space Black, 64 GB)";
	String mainMenuText1 = "Men";
	String subMainMenuText1 = "Sports Shoes";
	String productName1 = "WNDR-13 Running Shoes For Men";

	@BeforeClass
	public void setup() {
		init();
		homepage = new HomePage();
		searchResultPage = new SearchResultPage();
		productOverviewPage = new ProductOverviewPage();

	}

	@Test
	public void addingMultipleProjectToCartTest() throws InterruptedException {
		homepage.selectSubMainMenu(mainMenuText, subMainMenuText);
		searchResultPage.verifySearchResultandProjOverviewPricesSame(productName);
		viewCartPage = productOverviewPage.addcart();
		viewCartPage.verifyProjOverviwandViewCarePricesSame();
		viewCartPage.verifyProductItemCounts();
		viewCartPage.veryBothSideTotalProductPrice();
		viewCartPage.verySavingPrice();
		driver.close();
		new SeleniumUtils().switchToWindow(1);
		homepage.homePageLogoClick();
		homepage.selectSubMainMenu(mainMenuText1, subMainMenuText1);
		searchResultPage.verifySearchResultandProjOverviewPricesSame(productName1);
		viewCartPage = productOverviewPage.addcart();
		viewCartPage.verifyProjOverviwandViewCarePricesSame();
		viewCartPage.verifyProductItemCounts();
		viewCartPage.veryBothSideTotalProductPrice();
		viewCartPage.verySavingPrice();
		viewCartPage.clickPlaceOrderBtn();
	}
}
