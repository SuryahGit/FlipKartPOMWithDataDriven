package com.flipkart.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.flipkart.qa.reports.ExtentReport;
import com.flipkart.qa.util.ExcelReader;
import com.flipkart.qa.util.ExplicitWait;
import com.flipkart.qa.util.TestUtils;

public class TestBase {

	private static final Logger log = Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports extentReport;
	public static ExtentTest test;
	public static File reportDirectory;
	public static long implicitWait;
	public static int explicitWait;

	public TestBase() {
		prop = new Properties();
		FileInputStream fip;
		try {
			fip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\resources\\configfile\\config.properties");
			prop.load(fip);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@BeforeSuite
	public void beforeSuite() {
		extentReport = ExtentReport.getInstatnce();
	}

	@BeforeTest
	public void beforeTest() throws Exception {
		reportDirectory = new File(System.getProperty("user.dir") + "/src/main/resources/screenShots/");
		test = extentReport.createTest(getClass().getSimpleName());
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		test.log(Status.INFO, method.getName() + "**************test started***************");
		log.info("**************" + method.getName() + " Started***************");
	}

	@AfterMethod
	public void afterMthod(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, result.getName() + " Failed");
			test.log(Status.FAIL, result.getThrowable());
			String imagePath = captureScreenShot(result.getName(), driver);
			System.out.println("Failed Test Image Path " + imagePath);
			test.addScreenCaptureFromPath(imagePath);
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, result.getName() + " Skipped");
			test.log(Status.SKIP, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, result.getName() + " Passed");
			// String imagePath = captureScreenShot(result.getName(), driver);
			// test.addScreenCaptureFromPath(imagePath);
		}

		extentReport.flush();
	}

	@AfterTest
	public void afterTest() {
		if (driver != null) {
			//	driver.quit();
		}
	}

	public void init() {

		String log4jConficPath = System.getProperty("user.dir")
				+ "\\src\\main\\resources\\configfile\\log4j.properties";
		PropertyConfigurator.configure(log4jConficPath);

		String browsername = prop.getProperty("browser");
		if (browsername.equals("chrome")) {
			log.info("Opening the browser " + browsername);
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browsername.equals("FF")) {
			log.info("Opening the browser " + browsername);
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		implicitWait = Long.parseLong(prop.getProperty("implicitwait"));
		explicitWait = Integer.parseInt(prop.getProperty("explicitwait"));
		driver.manage().window().maximize();
		new ExplicitWait().setImplicitWait(implicitWait, TimeUnit.SECONDS);
		log.info("Navigating to url");
		driver.get(prop.getProperty("url"));
	}

	public static void logExtentReport(String s1) {
		test.log(Status.INFO, s1);
	}

	public String captureScreenShot(String fileName, WebDriver driver) {

		if (driver == null) {
			log.info("driver is null");
			return null;
		}
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			destFile = new File(reportDirectory + "/" + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			Files.copy(scrFile.toPath(), destFile.toPath());
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
					+ "'height='100' width='100'></a>");
		} catch (Exception e) {

		}
		return destFile.toString();
	}

	public Object[][] getExcelData(String excelName, String sheetName) {
		String excelLocation = System.getProperty("user.dir") + "/src/main/resources/configfile/" + excelName;
		log.info("Excel Location " + excelLocation);
		ExcelReader excel = new ExcelReader();
		Object[][] data = excel.getExcelData(excelLocation, sheetName);
		return data;
	}

	@AfterSuite
	public void afterSuite() throws Exception {
	//	TestUtils.sendEmailWithResults();
	}
}