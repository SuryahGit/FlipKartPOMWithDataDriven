package com.flipkart.qa.testcases.productCategoryPage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.pages.HomePage;
import com.flipkart.qa.pages.ProductOverviewPage;
import com.flipkart.qa.pages.SearchResultPage;
import com.flipkart.qa.pages.ViewCartPage;
import com.flipkart.qa.util.SeleniumUtils;

public class testFilpKart extends TestBase {

	HomePage homepage;
	SearchResultPage searchResultPage;
	ProductOverviewPage productOverviewPage;
	ViewCartPage viewCartPage;
	String mainMenuText = "Men";
	String subMainMenuText = "Sports Shoes";
	String productName = "WNDR-13 Running Shoes For Men";

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
	}
}
