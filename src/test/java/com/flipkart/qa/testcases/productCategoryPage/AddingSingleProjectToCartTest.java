package com.flipkart.qa.testcases.productCategoryPage;

import java.util.HashMap;

import org.testng.Assert;
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
	String productName = "Redmi Note 7 Pro (Nebula Red, 64 GB)";

	@BeforeClass
	public void setup() {
		init();
		homepage = new HomePage();
		searchResultPage = new SearchResultPage();
		productOverviewPage = new ProductOverviewPage();
		viewCartPage = new ViewCartPage();
	}

	@Test
	public void addingSingleProjectToCartTest() throws InterruptedException {
		homepage.selectSubMainMenu(mainMenuText, subMainMenuText);
		//searchResultPage.verifySearchResultandProjOverviewPricesSame(productName);
		
		HashMap<String, String> map = searchResultPage.printProductName(productName);
		String offerPrice = map.get("OfferPrice");
		String originalPrice = map.get("OriginalPrice");
		searchResultPage.switchToWindow(2);

		String productPageOfferPrice = productOverviewPage.getofferPrice();
		String productPageOriginalPrice = productOverviewPage.getoriginalPrice();
		Assert.assertEquals(offerPrice, productPageOfferPrice);
		Assert.assertEquals(originalPrice, productPageOriginalPrice);

		productOverviewPage.addcart();
		String cartPageOfferPrice = viewCartPage.getofferPrice();
		String cartPageOriginalPrice = viewCartPage.getoriginalPrice();
		Assert.assertEquals(offerPrice, cartPageOfferPrice);
		Assert.assertEquals(originalPrice, cartPageOriginalPrice);

		int totalItems = viewCartPage.getTotalItems();
		int myCartTotalProrducts = viewCartPage.getMyCartTotalProducts();
		Assert.assertEquals(myCartTotalProrducts, totalItems);

		int myCartTotalPrice = viewCartPage.getMyCartTotalOfferPrices();
		int totalPayablePrice = viewCartPage.gettotalPayablePrice();
		Assert.assertEquals(myCartTotalPrice, totalPayablePrice);

		int mycartSavePrice = viewCartPage.getMyCartSavingPrice();
		int youWillSavePrice = viewCartPage.getYouWillSavePrice();
		Assert.assertEquals(mycartSavePrice, youWillSavePrice);
		/*Thread.sleep(5000);
		driver.close();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[@id='container']/div/div[1]/div[1]/div[2]/div[1]/div/a[1]/img")).click();
*/
		// viewCartPage.clickPlaceOrderBtn();
	}


}
