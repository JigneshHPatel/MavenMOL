/**
 * 
 */
package clj.Frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import commonLibrary.OpenNewTabMethod;
import commonLibrary.PuffListMethod;
import commonLibrary.VerifyLinkTest;
import commonLibrary.WeatherWidget;
import pageObjects.PuffListObj;
import pageObjects.TopicPageObj;
import util.*;

/**
 * @author jigneshkumar.patel
 *
 *
 *         Topic page
 */

public class TopicPage extends browsers.BrowserStack {

	private static DataFileReader dataReader = new DataFileReader();

	private static void topiclink(WebDriver driver) throws Exception {

		driver.get(Urls.baseurl + "/topics");
		Thread.sleep(3000);
		System.out.println(driver.getCurrentUrl());
		info(driver, driver.getCurrentUrl());
		try {
			Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Urls.baseurl + "/topics"));
		} catch (Exception e) {
			System.out.println("Topic page url Assertion failed");
			System.out.println(e.getMessage());
			return;
		}
	}

	@Test(retryAnalyzer = Retry.class, description = "assert Topic Index heading")
	public void a_TopicPageHeading() throws Exception {
		TopicPageObj topicObj = new TopicPageObj(driver);
		topiclink(driver);

		// Heading Topic Index
		try {
			topicObj.topicHeadingIsPresent();
			System.out.println("Topic page heading is present");
			pass(driver, "Topic page heading is present");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
	}

	// Index name,A-Z and teampage(2 modules are empty)
	@Test(retryAnalyzer = Retry.class, description = "assert topic names index at top of the page, check if all topics are visible")
	public void b_TopicModules() throws Exception {
		TopicPageObj topicObj = new TopicPageObj(driver);
		topiclink(driver);

		// Topic index module A-Z alphabets
		if (topicObj.isAtoZLettersDisplayed()) {
			System.out.println("Topic index is present");
			pass(driver, "Topic index is present");
		} else {
			System.out.println("Topic index is ***NOT*** present");
			pass(driver, "Topic index is ***NOT*** present");
		}

		// All topic names
		for (WebElement topic : topicObj.TotalTopicNames) {
			try {
				if (topic.getAttribute("href").isEmpty()) {
					continue;
				} else if (!topic.isDisplayed()) {
					System.out.println((topicObj.TotalTopicNames.indexOf(topic) + 1) + " is ***not*** Displayed");
					info(driver, (topicObj.TotalTopicNames.indexOf(topic) + 1) + " is ***not*** Displayed");
				}
			} catch (Exception e) {
			}
		}

	}

	// Topic name Index
	@Test(description = "Topic name index on top of the page all letter visible clickable and scrolled to element")
	public void c_TopicIndex() throws Exception {

		TopicPageObj topicObj = new TopicPageObj(driver);
		topiclink(driver);

		for (int t = 0; t < topicObj.TotalTopicIndexA_Z.size(); t++) {
			WebElement tn = topicObj.TotalTopicIndexA_Z.get(t);
			String url = tn.getAttribute("href");
			if (tn.isEnabled()) {
				System.out.println(url + " is clickable");
			} else {
				System.out.println(url + " is NOT clickable");
				fail(driver, url + " is NOT clickable");
			}
		}

		topicObj.topicIndexTeamPages.click();
		Thread.sleep(2000);
		if (driver.getCurrentUrl().endsWith("/topics#teamPages")) {
			System.out.println("Url contains topic name");
			System.out.println(driver.getCurrentUrl());
			pass(driver, "Url contains topic name");
		}
		// verify http response
		verifyLinkActive(url);

	}

	private void verifyLinkActive(String url) {
		VerifyLinkTest.verifyLinkActive(url);
	}

	// All links in A-Z and teampages module
	@Test(description = "click on topic name and assert topic url in new window")
	public void d_topic_names() throws Exception {

		topiclink(driver);

		// click topicname

		for (String topicName : dataReader.getTopicNames()) {
			// TopicName(driver, topicName);
			Actions action = new Actions(driver);

			String expectedUrl = driver.findElement(By.linkText(topicName)).getAttribute("href");
			WebElement topicclick = driver.findElement(By.linkText(topicName));
			try {
				action.moveToElement(topicclick).build().perform();
			} catch (Exception e) {
			}
			OpenNewTabMethod.NewTabMethod(driver, topicclick, expectedUrl, topicName,
					OpenNewTabMethod.newTabMethod.links);
		}
	}

	// Don`t Miss puff list
	@Test(retryAnalyzer = Retry.class, description = "Click on top 2 articles of pufflist")
	public void g_DontMiss() throws Exception {
		PuffListObj puffObj = new PuffListObj(driver);
		topiclink(driver);
		String ActualPuffHeading = puffObj.getDontMissHeading();
		String ExpectedPuffHeading = "DON'T MISS";

		PuffListMethod.puffListMethod(driver, puffObj.DontMissPuffModule, ActualPuffHeading, ExpectedPuffHeading,
				puffObj.TotalDontMissPuffArticles);

	}

	// Weather
	@Test(retryAnalyzer = Retry.class, description = "Weather icons and info. displayed on page")
	public void h_WeathreTest() throws Exception {

		topiclink(driver);

		WeatherWidget.WeatherIcons(driver);
	}

}
