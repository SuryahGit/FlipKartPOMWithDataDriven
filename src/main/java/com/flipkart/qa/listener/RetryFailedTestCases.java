package com.flipkart.qa.listener;

import org.apache.log4j.Logger;
import org.testng.ITestResult;

public class RetryFailedTestCases {

	private int retrycount = 0;
	private int maxRetryLimit = 3;
	private Logger log = Logger.getLogger(RetryFailedTestCases.class);

	public boolean retry(ITestResult result) {
		if (retrycount < maxRetryLimit) {
			log.info("Retrying test " + result.getName() + " with status " + getResultStatusName(result.getStatus())
					+ " for the " + (retrycount + 1) + " times.");
			retrycount++;
			return true;
		}
		return false;
	}

	private String getResultStatusName(int status) {
		String resultName = null;
		if (status == 1) {
			resultName = "Success";
		} else if (status == 2) {
			resultName = "FAILURE";
		} else if (status == 3) {
			resultName = "SKIP";
		}
		return resultName;
	}
	
}
