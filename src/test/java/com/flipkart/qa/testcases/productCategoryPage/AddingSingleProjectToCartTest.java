package com.flipkart.qa.testcases.productCategoryPage;




import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.pages.HomePage;
import com.flipkart.qa.pages.ProductOverviewPage;
import com.flipkart.qa.pages.SearchResultPage;
import com.flipkart.qa.pages.ViewCartPage;

public class AddingSingleProjectToCartTest extends TestBase {

	HomePage homepage;
	SearchResultPage searchResultPage;
	ProductOverviewPage productOverviewPage;
	ViewCartPage viewCartPage;
	String mainMenuText = "Electronics";
	String subMainMenuText = "Mi";
	String productName = "Redmi Note 7 Pro (Space Black, 64 GB)";

	@BeforeClass
	public void setup() {
		init();
		homepage = new HomePage();
		searchResultPage = new SearchResultPage();
		productOverviewPage = new ProductOverviewPage();
		
	}

	@Test
	public void addingSingleProjectToCartTest() throws InterruptedException {
		homepage.selectSubMainMenu(mainMenuText, subMainMenuText);
		searchResultPage.verifySearchResultandProjOverviewPricesSame(productName);
		productOverviewPage.getofferPrice();
		viewCartPage = productOverviewPage.addcart();
		viewCartPage.verifyProjOverviwandViewCarePricesSame();
		viewCartPage.verifyProductItemCounts();
		viewCartPage.veryBothSideTotalProductPrice();
		viewCartPage.verySavingPrice();
	}


}
