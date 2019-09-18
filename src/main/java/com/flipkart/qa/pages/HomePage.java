package com.flipkart.qa.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.flipkart.qa.base.TestBase;
import com.flipkart.qa.util.ExplicitWait;
import com.flipkart.qa.util.SeleniumUtils;

public class HomePage extends TestBase {

	private static final Logger log = Logger.getLogger(HomePage.class.getName());

	@FindBy(xpath = "//a[contains(text(),'Login & Signup')]")
	WebElement loginAndSignup;

	@FindBy(css = "form>div>input[type='text']")
	WebElement logineMailOrMobileNumber;

	@FindBy(xpath = "//input[@type='password']")
	WebElement loginPassword;

	@FindBy(css = "button[type='submit']>span")
	WebElement loginBtn;

	@FindBy(xpath = "//button[contains(text(),'✕')]")
	WebElement LoginPopUpcloseBtn;

	@FindBy(xpath = "//*[@id='container']/div/div[1]/div[1]/div[2]/div[3]/div/div/div/div")
	WebElement successLoginUserName;

	@FindBy(xpath = "//a/div[contains(text(),'Logout')]")
	WebElement logOut;

	/*
	 * @FindBy(xpath = "//*[@id='block_top_menu']/ul/li[1]/a") public WebElement
	 * grocery;
	 */

	@FindBy(css = "div>:nth-child(2)>div>ul>li")
	List<WebElement> mainMenu;

	@FindBy(xpath = "//*[@id='container']/div/div[1]/div[1]/div[2]/div[1]/div/a[1]/img")
	WebElement homePageLogo;

	String subMainMenuPar1 = "//*[@id='container']/div/div[2]/div/ul/li[";
	String subMainMenuPar2 = "]/ul/li/ul/li/ul/li/a";

	// *[@id="container"]/div/div[2]/div/ul/li[1]/ul/li/ul/li[1]/ul/li[2]/a
	// *[@id='container']/div/div[2]/div/ul/li[2]/ul/li/ul/li/ul/li/a
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public void loginToApplication(String eMailOrMobileNumber, String pwd) {
		// loginAndSignup.click();
		log("Login into application");
		logineMailOrMobileNumber.sendKeys(eMailOrMobileNumber);
		log("Entering Email Id 0r Mobile Number " + eMailOrMobileNumber);
		loginPassword.sendKeys(pwd);
		log("Entering Password " + pwd);
		loginBtn.click();
		log("clicked " + pwd.toString());

	}

	public boolean verifySuccessLoginMsg() {
		new ExplicitWait().waitForWebElementToVisible(successLoginUserName, explicitWait);
		return new SeleniumUtils().isDisplayed(successLoginUserName);
	}

	public void logOutToApplication() {
		Actions act = new Actions(driver);
		act.moveToElement(successLoginUserName).build().perform();
		logOut.click();
		log("Logout Successfully");
		new ExplicitWait().waitForWebElementToVisible(LoginPopUpcloseBtn, explicitWait);
	}

	public void loginPopupclose() {

		boolean status = new SeleniumUtils().isDisplayed(LoginPopUpcloseBtn);
		if (status) {
			log("Closing Login Popup");
			LoginPopUpcloseBtn.click();
		}
	}

	public void homePageLogoClick() {
		homePageLogo.click();
	}

	public int mouseHoverToMainMenu(String mainMenuText) throws InterruptedException {

		int i = 0;
		Actions act = new Actions(driver);
		loginPopupclose();
		log("Mouse hovering to Main Menu " + mainMenuText);
		int mainMenuSize = mainMenu.size();
		// System.out.println("main Menu Size is " + mainMenuSize);
		for (i = 0; i < mainMenuSize; i++) {
			String maincategoryText = mainMenu.get(i).getText();
			// System.out.println(i + " value is " + maincategoryText);
			if (maincategoryText.equals(mainMenuText)) {
				act.moveToElement(mainMenu.get(i)).build().perform();
				break;
			}
		}
		return i;
	}

	public void selectSubMainMenu(String mainMenuText, String subMainMenuText) throws InterruptedException {
		int k = 1;
		int i = mouseHoverToMainMenu(mainMenuText);
		k = k + i;
		log("Mouse hovering to Sub Main Menu " + subMainMenuText);
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(prop.getProperty("implicitwait")));
		wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.xpath(subMainMenuPar1 + k + subMainMenuPar2))));
		// System.out.println("I value is " + i);
		// System.out.println("K value is " + k);
		List<WebElement> subMainMenu = driver.findElements(By.xpath(subMainMenuPar1 + k + subMainMenuPar2));
		int subMainMenuSize = subMainMenu.size();
		// System.out.println("sub Main Menu Size " + subMainMenuSize);
		for (int j = 0; j < subMainMenuSize; j++) {
			String subMenuCategoryText = subMainMenu.get(j).getText();
			// System.out.println(j + " j value is " + subMenuCategoryText);
			if (subMenuCategoryText.equals(subMainMenuText)) {
				subMainMenu.get(j).click();
				log("Cliked on the Sub Main Menu " + subMainMenuText);
				break;
			}
		}
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		TestBase.logExtentReport(data);
	}

}
