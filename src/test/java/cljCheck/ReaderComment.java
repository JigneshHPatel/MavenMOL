/**
 * 
 */
package cljCheck;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import browsers.Url;
import config.Retry;
import config.WaitObj;

/**
 * @author jigneshkumarpatel Test for Reader comment on article page
 *
 */

public class ReaderComment extends browsers.BrowserStack {

	@Test(retryAnalyzer = Retry.class, description = "Info on comments e.g. user info, newest,oldest etc.")
	public void a_CommentInfo() throws Exception {
		System.out.println("------------------------");
		System.out.println("***CommentInfo***");
		System.out.println("------------------------");
		commentSection(driver);

		WebElement modMessage = driver.findElement(By.cssSelector("#reader-comments>div.rc-content >div.rc-title>p"));
		if (modMessage.isDisplayed()) {
			System.out.println("Moderation message is " + "'" + modMessage.getText() + "'");
			pass(driver, "Moderation message is " + "'" + modMessage.getText() + "'");
		} else {
			System.out.println("Moderation message is NOT displyaed");
			fail(driver, "Moderation message is NOT displyaed");
		}

		// Newest,Oldest tabs
		List<WebElement> tabs = driver.findElements(By.cssSelector("#rc-tabs>li>a"));
		for (WebElement tab : tabs) {
			String tabName = tab.getText();
			List<WebElement> allcomments = driver.findElements(By.cssSelector("#js-comments>div"));
			System.out.println("Total comments on " + tabName + " are " + allcomments.size());
			pass(driver, "Total comments on " + tabName + " are " + allcomments.size());
		}
		String userInfo = driver.findElement(By.xpath("//*[@id='js-comments']/div[1]/div[2]/p[1]")).getText();
		String userGeo = driver.findElement(By.xpath("//*[@id='js-comments']/div[1]/div[2]/p[1]/a")).getText();
		System.out.println("User Info is " + userInfo + userGeo);
		pass(driver, "User Info is " + userInfo + userGeo);
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "posting direct comment by logging from post comment path")
	public void b_PostComment() throws Exception {
		System.out.println("-------------------------");
		System.out.println("***PostComment***");
		System.out.println("-------------------------");
		commentSection(driver);
		Actions action = new Actions(driver);
		driver.findElement(By.id("message")).clear();
		driver.findElement(By.id("message")).sendKeys("comment" + Url.currentDate);
		Assert.assertTrue(driver.findElement(By.id("message")).getAttribute("value").contains("comment"));
		WebElement submitComment = driver.findElement(By.cssSelector(".btn.btn-submit"));
		try {
			if (driver.findElement(By.cssSelector(".vjs-flyout-close.vjs-flyout-button")).isDisplayed()) {
				driver.findElement(By.cssSelector(".vjs-flyout-close.vjs-flyout-button")).click();
				Thread.sleep(1000);
			}
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			action.moveToElement(submitComment).build().perform();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		WaitObj.wait(driver, submitComment);
		// je.executeScript("arguments[0].click();", submitComment);
		submitComment.click();
		Thread.sleep(3000);
		String expectedText = "Login";
		// String actualText =
		// driver.findElement(By.cssSelector(".lcolumn>h3")).getText();
		if (expectedText.equalsIgnoreCase(driver.findElement(By.cssSelector(".lcolumn>h3")).getText())) {
			WebElement emailField = driver.findElement(By.id("reg-lbx-email-page"));
			emailField.clear();
			emailField.sendKeys("testmol@sharklasers.com");
			WebElement passField = driver.findElement(By.id("reg-lbx-password-page"));
			passField.clear();
			passField.sendKeys("testmol");
			driver.findElement(By.cssSelector(".reg-btn-login")).click();
			Thread.sleep(2000);
		} else {
			System.out.println("User is already logged in");
			info(driver, "User is already logged in");
		}
		try {
			driver.findElement(By.cssSelector(".comments-count.home")).click();
			Thread.sleep(4000);
			String submissionMsg = driver.findElement(By.cssSelector("div.submission-feedback-message")).getText();

			Assert.assertTrue(submissionMsg.contains("Thanks for sharing your comment"));
			pass(driver, " Comment Pass");
			info(driver, submissionMsg + " is present");
			System.out.println(" Direct comment pass");
			System.out.println(submissionMsg + " is present");
		} catch (Exception e) {
			System.out.println("Submission Massage not present");
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}

		WebElement logoutButton = driver.findElement(By.id("logout"));
		WaitObj.wait(driver, logoutButton);
		// je.executeScript("arguments[0].scrollIntoView(true);", logoutButton);
		Thread.sleep(2000);
		try {
			logoutButton.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Thread.sleep(2000);
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Post Reply on comment in oldest filter")
	public void c_PostReply() throws Exception {
		System.out.println("---------------------");
		System.out.println("***PostReply***");
		System.out.println("---------------------");
		commentSection(driver);
		Actions action = new Actions(driver);
		// click on oldest tab
		driver.findElement(By.cssSelector("#rc-tabs>li:nth-child(2)>a")).click();
		Thread.sleep(5000);

		List<WebElement> allReply = driver.findElements(By.cssSelector("button.btn.anchor-reply-comment.js-reply"));
		WebElement reply = allReply.get(0);
		WaitObj.wait(driver, reply);
		reply.click();
		// Thread.sleep(2000);
		try {
			WebElement replyfield = driver.findElement(By.cssSelector("div.reply-form.textarea-holder.field"));
			replyfield.click();
			driver.switchTo().activeElement().sendKeys("reply" + Url.currentDate);
			Assert.assertTrue(replyfield.getAttribute("value").contains("reply"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		WebElement post = driver.findElement(By.cssSelector(".btn.js-reply-submit"));
		WaitObj.wait(driver, post);
		try {
			action.moveToElement(post).build().perform();
		} catch (Exception e1) {

		}
		post.click();

		Thread.sleep(3000);
		String expectedText = "Login";
		// String actualText =
		// driver.findElement(By.cssSelector(".lcolumn>h3")).getText();
		if (expectedText.equalsIgnoreCase(driver.findElement(By.cssSelector(".lcolumn>h3")).getText())) {
			WebElement emailField = driver.findElement(By.id("reg-lbx-email-page"));
			emailField.clear();
			emailField.sendKeys("testmol@sharklasers.com");
			WebElement passField = driver.findElement(By.id("reg-lbx-password-page"));
			passField.clear();
			passField.sendKeys("testmol");
			driver.findElement(By.cssSelector(".reg-btn-login")).click();
			Thread.sleep(3000);
		} else {
			System.out.println("Login is not present");
			info(driver, "Login is not present");
		}
		try {
			driver.findElement(By.cssSelector(".comments-count.home")).click();
			Thread.sleep(4000);
			String submissionMsg = driver.findElement(By.cssSelector("div.submission-feedback-message")).getText();

			Assert.assertTrue(driver.getPageSource().contains("Thanks for sharing your comment"));
			pass(driver, " Comment Reply Pass");
			info(driver, submissionMsg + " is present");
			System.out.println("Comment Reply pass");
			System.out.println(submissionMsg + " is present");
		} catch (Exception e) {
			System.out.println("Submission Massage not present");
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}

		WebElement logoutButton = driver.findElement(By.id("logout"));
		WaitObj.wait(driver, logoutButton);
		// je.executeScript("arguments[0].scrollIntoView(true);", logoutButton);
		Thread.sleep(2000);
		try {
			logoutButton.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Thread.sleep(2000);
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Report abuse, asserting all filed are present in report abuse page and negative test")
	public void d_ReportAbuse() throws Exception {
		System.out.println("---------------------");
		System.out.println("***ReportAbuse***");
		System.out.println("---------------------");
		JavascriptExecutor je = (JavascriptExecutor) driver;
		commentSection(driver);
		String articleurl = driver.getCurrentUrl();
		WebElement reportAbuse = driver.findElement(By.cssSelector("#js-comments>div:nth-child(1)>a"));
		try {
			je.executeScript("arguments[0].style='visibility: visible;'", reportAbuse);
			WaitObj.wait(driver, reportAbuse);
			reportAbuse.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
		Thread.sleep(2000);
		if (driver.findElement(By.cssSelector("h2.debate-page-header.box")).getText()
				.equalsIgnoreCase("Report abuse")) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='abuse-details']/div[1]/div")).isDisplayed());
			System.out.println("Article title is present on report abuse page");
			pass(driver, "Article title is present on report abuse page");
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='abuse-details']/div[2]/div")).isDisplayed());
			System.out.println("Parent comment is present on report abuse page");
			pass(driver, "Parent comment is present on report abuse page");
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='abuse-details']/div[3]/div")).isDisplayed());
			System.out.println("Comment author details is present on report abuse page");
			pass(driver, "Comment author details is present on report abuse page");
			WebElement emailField = driver.findElement(By.id("email"));
			emailField.clear();
			emailField.sendKeys("testmol@sharklasers.com");
			if (emailField.getAttribute("value").contains("testmol@sharklasers.com")) {
				System.out.println("Email field passed");
				pass(driver, "Email field passed");
			}
			WebElement complaint = driver.findElement(By.id("complaint"));
			complaint.clear();
			complaint.sendKeys("Test");
			if (complaint.getAttribute("value").contains("Test")) {
				System.out.println("Complaint field passed");
				pass(driver, "Complaint field passed");
			}
			if (driver.findElement(By.cssSelector("button.reg-btn.wocc")).isDisplayed()) {
				System.out.println("Submit button is displayed");
				pass(driver, "Submit button is displayed");
			}
			Thread.sleep(2000);
			/*
			 * if(driver.findElement(By.cssSelector("div.reg-fld.fail")).isDisplayed()) {
			 * System.out.println("Report Abuse error message displyed");
			 * pass(driver,"Report Abuse error message displyed"); }else {
			 * System.out.println("Report Abuse error message is **NOT** displyed");
			 * fail(driver,"Report Abuse error message is **NOT** displyed"); }
			 */

			// Click on back button
			WebElement backButton = driver.findElement(By.cssSelector(".reg-btn.reverse-wocc"));
			WaitObj.wait(driver, backButton);
			backButton.click();
			// driver.findElement(By.cssSelector("#abuse-details > div:nth-child(10) > div >
			// a")).click();
			Thread.sleep(2000);
			Assert.assertTrue(articleurl.contains(driver.getCurrentUrl()));
			pass(driver, "Report abuse pass");
			System.out.println("Report abuse pass");
		} else {
			System.out.println("Report abuse page is not present");
			fail(driver, "Report abuse page is not present");
		}
		System.out.println(line);

	}

	@Test(retryAnalyzer = Retry.class, description = "voting UP and DOWN comment")
	public void e_RateComment() throws Exception {
		System.out.println("----------------------");
		System.out.println("***RateComment***");
		System.out.println("----------------------");
		commentSection(driver);

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100);");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// click on oldest tab
		driver.findElement(By.cssSelector("#rc-tabs>li:nth-child(2)>a")).click();
		Thread.sleep(5000);

		List<WebElement> allrate = driver
				.findElements(By.cssSelector("div.rating-button.grey-rating-button.rating-button-up"));
		System.out.println("Total rateButtonsUp are " + allrate.size());
		info(driver, "Total rateButtonsUp are " + allrate.size());
		WebElement rate = allrate.get(0);
		List<WebElement> allrateCount = driver.findElements(By.cssSelector("div.rating-button-inline"));
		WebElement rateCountEle = allrateCount.get(1);
		String rateCount = rateCountEle.getText();
		System.out.println("Rate Up count are " + Integer.parseInt(rateCount));
		info(driver, "Rate Up count are " + Integer.parseInt(rateCount));
		rate.click();
		Thread.sleep(3000);
		List<WebElement> allrateCountAfter = driver.findElements(By.cssSelector("div.rating-button-inline"));
		WebElement rateCountEleAfter = allrateCountAfter.get(1);
		String rateCountAfter = rateCountEleAfter.getText();
		System.out.println("Rate Up count after is " + Integer.parseInt(rateCountAfter));
		info(driver, "Rate count Up after is " + Integer.parseInt(rateCountAfter));

		if (rateCountAfter.equals(rateCount)) {
			System.out.println("Rateup is not incremented after Rate up");
			warning(driver, "Rateup is not incremented after Rate up");
		} else {
			System.out.println("Rate is incremented after Rate up");
			pass(driver, "Rate is incremented after Rate up");
		}

		// Rate down

		System.out.println("*****Rate down*****");
		List<WebElement> allratedown = driver
				.findElements(By.cssSelector("div.rating-button.grey-rating-button.rating-button-down"));
		System.out.println("Total rateButtonsDown are " + allratedown.size());
		info(driver, "Total rateButtonsDown are " + allratedown.size());
		WebElement ratedown = allratedown.get(1);
		List<WebElement> allrateCountDown = driver.findElements(By.cssSelector("div.rating-button-inline"));
		WebElement rateCountDownEle = allrateCountDown.get(0);
		String rateCountDown = rateCountDownEle.getText();
		System.out.println("Rate Down count are " + Integer.parseInt(rateCountDown));
		info(driver, "Rate Down count are " + Integer.parseInt(rateCountDown));
		ratedown.click();
		Thread.sleep(3000);
		List<WebElement> allrateCountDownAfter = driver.findElements(By.cssSelector("div.rating-button-inline"));
		WebElement rateCountDownEleAfter = allrateCountDownAfter.get(0);
		String rateCountDownAfter = rateCountDownEleAfter.getText();
		System.out.println("Rate Down count after is " + Integer.parseInt(rateCountDownAfter));
		info(driver, "Rate count Down after is " + Integer.parseInt(rateCountDownAfter));
		/*
		 * if(driver.findElement(By.xpath(
		 * "//*[@id='comment-295157525']/div[2]/div[1]/span")).getText().equals("Rated")
		 * ) { System.out.println("Rated text present");
		 * pass(driver,"Rated text present"); }
		 */
		if (rateCountDownAfter.equals(rateCountDown)) {
			System.out.println("RateDown is not incremented after Rate down");
			warning(driver, "RateDown is not incremented after Rate down");
		} else {
			System.out.println("Rate is incremented after Rate Down");
			pass(driver, "Rate is incremented after Rate Down");
		}

		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Posting comment to Facebook and Dailymail")
	public void f_FbComment() throws Exception {
		System.out.println("----------------------");
		System.out.println("***FbComment***");
		System.out.println("----------------------");
		commentSection(driver);
		WebDriverWait visibleWait = new WebDriverWait(driver, 30);

		driver.findElement(By.id("message")).clear();
		driver.findElement(By.id("message")).sendKeys("comment" + Url.currentDate);
		Assert.assertTrue(driver.findElement(By.id("message")).getAttribute("value").contains("comment"));
		try {
			if (driver.findElement(By.cssSelector(".floater-19vuu")).isDisplayed()) {
				driver.findElement(By.cssSelector(".closeButton-1i2Y6")).click();

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		driver.findElement(By.id("handlePostToFacebookInput")).click();
		Thread.sleep(2000);
		WaitObj.wait(driver, driver.findElement(By.cssSelector(".btn.fbConfirm")));
		driver.findElement(By.cssSelector(".btn.fbConfirm")).click();
		Thread.sleep(3000);
		try {
			Assert.assertTrue(driver.getCurrentUrl().contains("www.facebook.com/login"));
			System.out.println("Facebook login page is present");
			pass(driver, "Facebook login page is present");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Facebook login page is ***NOT*** present");
			fail(driver, "Facebook login page is ***NOT*** present");
		}

		WebElement email = driver.findElement(By.id("email"));
		email.clear();
		email.sendKeys("testmolfb1@gmail.com");
		WebElement pass = driver.findElement(By.id("pass"));
		pass.clear();
		pass.sendKeys("testmol");
		driver.findElement(By.id("loginbutton")).click();
		Thread.sleep(2000);
		WaitObj.wait(driver, driver.findElement(By.cssSelector(".btn.btn-submit")));
		driver.findElement(By.cssSelector(".btn.btn-submit")).click();
		visibleWait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".submission-feedback-fbmessage"))));
		String message = driver.findElement(By.cssSelector(".submission-feedback-fbmessage")).getText();
		if (message.equalsIgnoreCase("Your comment will also be posted to the Facebook account for Don Joe")) {
			System.out.println("Comment posted by FB");
			pass(driver, "Comment posted by FB, submission message displayed " + message);
		} else {
			System.out.println("confirmation message not present");
			warning(driver, "confirmation message not present");
		}
	}

	public static void commentSection(WebDriver driver) throws Exception {
		driver.get(Url.baseurl + "/home/index.html");
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		// List<WebElement>puffArticles=driver.findElements(By.cssSelector(".femail.item>div>ul>li>a"));
		for (int i = 1; i < 10; i++) {
			WebElement puffArticle = driver
					.findElement(By.cssSelector(".femail.item>div>ul>li:nth-child(" + i + ")>a"));
			// String rcArticle = puffArticle.getAttribute("href");
			WaitObj.wait(driver, puffArticle);
			puffArticle.click();
			Thread.sleep(2000);

			String rcStatus = (String) je.executeScript("return PageCriteria.readerComments");
			String articleType = (String) je.executeScript("return PageCriteria.articleStyle");
			System.out.println("Comment status  " + rcStatus);
			System.out.println("Article type " + articleType);

			if (rcStatus.equalsIgnoreCase("W") && articleType.equalsIgnoreCase("wide")) {

				System.out.println("Article " + i);
				break;

			} else {
				driver.navigate().back();
				Thread.sleep(3000);
			}
		}

		// driver.get(Url.articleUrl);
		Thread.sleep(2000);
		System.out.println(driver.getCurrentUrl());
		info(driver, driver.getCurrentUrl());
		if (driver.findElement(By.id("logout")).isDisplayed()) {
			System.out.println("User is already logged in");
			driver.findElement(By.id("logout")).click();
			Thread.sleep(2000);
			Assert.assertTrue(driver.findElement(By.id("login")).isDisplayed());
			System.out.println("User is logged out");
		}

		WebElement commentIcon = driver.findElement(By.cssSelector("a.comments-count.home"));
		if (commentIcon.isDisplayed()) {
			if (driver.getPageSource().contains("We are no longer accepting comments on this article.")) {
				System.out.println("comments are NOT allowed on this article");
				warning(driver, "comments are NOT allowed on this article");
			}
			commentIcon.click();
			Thread.sleep(3000);
			try {
				Assert.assertTrue(driver.getCurrentUrl().contains("comments"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (driver.findElement(By.cssSelector(".floater-19vuu")).isDisplayed()) {
					driver.findElement(By.cssSelector(".closeButton-1i2Y6")).click();

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (driver.findElement(By.cssSelector(".vjs-flyout-close.vjs-flyout-button")).isDisplayed()) {
					driver.findElement(By.cssSelector(".vjs-flyout-close.vjs-flyout-button")).click();
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {

			System.out.println("comment is NOT present");
			fail(driver, "comment is NOT present");
			return;
		}

	}

}
