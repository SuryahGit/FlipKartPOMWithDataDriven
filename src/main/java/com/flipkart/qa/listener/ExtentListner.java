package com.flipkart.qa.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;


public class ExtentListner implements ITestListener {

	private Logger log = Logger.getLogger(ExtentListner.class);
//	public static ExtentReports extentReport;
//	public static ExtentTest test;

	public void onTestStart(ITestResult result) {
		// test.log(Status.INFO, result.getName() + " is started successfully");
		Reporter.log(result.getMethod().getMethodName() + " is started successfully");
		log.info(result.getMethod().getMethodName() + " is started successfully");
	}

	public void onTestSuccess(ITestResult result) {
		// test.log(Status.PASS, result.getName() + " test case is passed");
		Reporter.log(result.getMethod().getMethodName() + " test case is passed");
		log.info(result.getMethod().getMethodName() + " test case is passed");
	}

	public void onTestFailure(ITestResult result) {
		// test.log(Status.FAIL, result.getThrowable());
		Reporter.log(result.getMethod().getMethodName() + " is failed.." + result.getThrowable());
		log.error(result.getMethod().getMethodName() + " is failed.." + result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
//		test.log(Status.SKIP, result.getThrowable());
		Reporter.log(result.getMethod().getMethodName() + " is skipped.." + result.getThrowable());
		log.warn(result.getMethod().getMethodName() + " is skipped.." + result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {
		// extentReport = ExtentManager.getInstatnce();
		// test = extentReport.createTest(context.getName());
		// test = extentReport.createTest(context.getCurrentXmlTest().getName());
		Reporter.log(context.getCurrentXmlTest().getName() + " Class Started..");
		log.info(context.getCurrentXmlTest().getName() + " Class Started..");
	}

	public void onFinish(ITestContext context) {
		// extentReport.flush();
		Reporter.log(context.getName() + " Class Finished..");
		log.info(context.getName() + " Class Finished..");
	}
}
