package com.flipkart.qa.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import com.flipkart.qa.base.TestBase;

public class TestUtils extends TestBase {

	public static String extentreportpath = System.getProperty("user.dir")
			+ "/src/main/resources/reports/extentreport.html";

	public static void sendEmailWithResults() throws Exception {

		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(extentreportpath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Execution Results");
		attachment.setName("results.html");
		System.out.println("Email");
	//	System.out.println(prop.getProperty("FromEmail") + prop.getProperty("EmailPassword"));
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(
				new DefaultAuthenticator(prop.getProperty("FromEmail"), prop.getProperty("EmailPassword")));
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		email.setFrom(prop.getProperty("FromEmail"));
		email.setSubject("Results");
		email.setMsg("Hi Team,\n\n Please find the attached test Automation Execution Results\n\n");
		try {
			email.addTo(getList("ToEmails"));
			email.addCc(getList("CCEmails"));
			email.addBcc(getList("BCCEmails"));
		} catch (Exception e) {

		}
		email.attach(attachment);
		email.send();
		System.out.println("Email sent-->");
	}

	public static String[] getList(String maillist) {
		String[] toList = null;
		toList = prop.getProperty(maillist).split(",");
		return toList;
	}
	
	public static void main(String[] args) throws Exception {
		TestUtils.sendEmailWithResults();
	}
}
