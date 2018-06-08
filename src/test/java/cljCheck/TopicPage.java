/**
 * 
 */
package cljCheck;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.LogStatus;

import browsers.Url;
import config.*;

/**
 * @author jigneshkumar.patel
 *
 *
 *         Topic page
 */

public class TopicPage extends browsers.BrowserStack {

	@Test(retryAnalyzer = Retry.class, description = "assert Topic Index heading")
	public void a_TopicPageHeading() throws Exception {
		System.out.println("-----------------------------");
		System.out.println("****TopicPageHeading****");
		System.out.println("-----------------------------");
		topiclink(driver);

		// Heading Topic Index
		try {
			assertEquals("Topics Index - A to Z",
					driver.findElement(By.cssSelector("#js-article-text>div>h1")).getText());// (By.xpath("//h1[text()='Topics
																								// Index - A to
																								// Z']")).getText());
			System.out.println("Topic page heading is present");
			pass(driver, "Topic page heading is present");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
		System.out.println(line);
	}

	// Index name,A-Z and teampage(2 modules are empty)
	@Test(retryAnalyzer = Retry.class, description = "assert topic names index at top of the page, check if all topics are visible")
	public void b_TopicModules() throws Exception {
		System.out.println("-------------------------");
		System.out.println("****TopicModules****");
		System.out.println("-------------------------");
		topiclink(driver);

		// Topic index module A-Z alphabets
		if (driver.findElement(By.cssSelector("#js-article-text > div > div:nth-child(3) > div")).isDisplayed()) {
			System.out.println("Topic index is present");
			pass(driver, "Topic index is present");
		} else {
			System.out.println("Topic index is ***NOT*** present");
			pass(driver, "Topic index is ***NOT*** present");
		}

		// All topic names
		List<WebElement> TotalTopics = driver.findElements(By.cssSelector(".column>a"));

		for (WebElement topic : TotalTopics) {
			try {
				if (topic.getAttribute("href").isEmpty()) {
					continue;
				} else if (!topic.isDisplayed()) {
					System.out.println((TotalTopics.indexOf(topic) + 1) + " is ***not*** Displayed");
					info(driver, (TotalTopics.indexOf(topic) + 1) + " is ***not*** Displayed");
				}
			} catch (Exception e) {
			}
		}
		/*
		 * List<WebElement>ele=driver.findElements(By.cssSelector(".moduleFull>div"));
		 * for (int e=0;e<ele.size();e++){ ele.get(e); if(ele.isEmpty()){
		 * System.out.println(e+1+" Module not present");
		 * fail(driver,(e+1)+" Module is not present"); }else
		 * System.out.println(e+1+" Module is present");
		 * pass(driver,(e+1)+" Module is present"); }
		 */
		System.out.println(line);
	}

	// Topic name Index
	@Test // (retryAnalyzer=Retry.class,description="Topic name index on top of the page
			// all letter visible clickable and scrolled to element")
	public void c_TopicIndex() throws Exception {
		System.out.println("----------------------");
		System.out.println("****TopicIndex****");
		System.out.println("----------------------");
		topiclink(driver);

		List<WebElement> topicname = driver.findElements(By.cssSelector(".ccolcc.topicLetter>a"));
		for (int t = 0; t < topicname.size(); t++) {
			WebElement tn = topicname.get(t);
			String url = tn.getAttribute("href");
			if (tn.isEnabled()) {
				System.out.println(url + " is clickable");
			} else {
				System.out.println(url + " is NOT clickable");
				fail(driver, url + " is NOT clickable");
			}
		}

		// click on teampage and test if page scrolled
		// Long positionBefore = (Long) je.executeScript("return window.pageYOffset;");
		// System.out.println("Page position before clicking is "+positionBefore);
		driver.findElement(By.cssSelector("div.ccolcc.topicTeam > a")).click();
		Thread.sleep(2000);
		if (driver.getCurrentUrl().endsWith("/topics#teamPages")) {
			System.out.println("Url contains topic name");
			System.out.println(driver.getCurrentUrl());
			pass(driver, "Url contains topic name");
		}
		/*
		 * Long positionAfter = (Long) je.executeScript("return window.pageYOffset;");
		 * if(positionAfter>5371 && positionAfter<6372) {
		 * System.out.println("Page position after clicking is "+positionAfter);
		 * pass(driver, "Page is scrolled to Element in view port after clicking");
		 * 
		 * }
		 */
		// verify http response
		verifyLinkActive(url);

		System.out.println(line);
	}

	private void verifyLinkActive(String url) {
		LinkTest.verifyLinkActive(url);
	}

	// 5 Seconds Of Summer
	@Test(enabled = false, retryAnalyzer = Retry.class)
	public void d_Test5() throws Exception {
		System.out.println("----------------------");
		System.out.println("****Test5****");
		System.out.println("----------------------");

		topiclink(driver);

		WebElement summer5 = driver.findElement(By.xpath("//*[text()='5 Seconds Of Summer']"));
		try {
			assertEquals("5 Seconds Of Summer", summer5.getText());
		} catch (Exception e1) {
			e1.printStackTrace();
			error(driver, e1.getMessage());
		}
		String expected5 = summer5.getAttribute("href");
		try {
			summer5.click();
			Thread.sleep(2000);
			if (driver.getCurrentUrl().contains(expected5)) {
				System.out.println(expected5 + " is pass");
				pass(driver, expected5 + " is pass");
			} else {
				System.out.println(expected5 + " is Fail");
				fail(driver, expected5 + " is Fail");
			}
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}
		Thread.sleep(3000);
		System.out.println(line);
	}

	// 60 Minutes Australia
	@Test(enabled = false, retryAnalyzer = Retry.class)
	public void e_Test6() throws Exception {
		System.out.println("----------------------");
		System.out.println("****Test6****");
		System.out.println("----------------------");
		topiclink(driver);

		WebElement summer6 = driver.findElement(By.xpath("//*[text()='60 Minutes Australia']"));
		try {
			assertEquals("60 Minutes Australia", summer6.getText());
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}
		String expected6 = summer6.getAttribute("href");
		try {
			summer6.click();
			Thread.sleep(2000);
			if (driver.getCurrentUrl().contains(expected6)) {
				System.out.println(expected6 + " is pass");
				logger.log(LogStatus.PASS, "<font color='#008000';>" + "Pass for " + expected6 + "</font>",
						driver.getCurrentUrl());
			} else {
				System.out.println(expected6 + " is Fail");
				fail(driver, expected6 + " is Fail");
			}
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}

		Thread.sleep(2000);
		System.out.println(line);
	}

	// All links in A-Z and teampages module
	@Test // (retryAnalyzer=Retry.class,description="click on topic name and assert topic
			// url in new window")
	public void f_topic_names() throws Exception {
		System.out.println("--------------------------");
		System.out.println("****Topic names****");
		System.out.println("--------------------------");
		topiclink(driver);

		// click topicname

		TopicName(driver, "Ariana Grande");
		TopicName(driver, "Breaking News");
		TopicName(driver, "Celebrity Net Worths");
		TopicName(driver, "Liverpool");

		System.out.println(line);
	}

	public static void TopicName(WebDriver driver, String topicname) throws Exception {

		Actions action = new Actions(driver);

		String expectedUrl = driver.findElement(By.linkText(topicname)).getAttribute("href");
		// System.out.println(expectedUrl);
		String parent = driver.getWindowHandle();
		WebElement topicclick = driver.findElement(By.linkText(topicname));
		WaitObj.wait(driver, topicclick);

		try {
			action.moveToElement(topicclick).build().perform();
		} catch (Exception e) {

		}
		if (caps.getPlatform().toString().toLowerCase().contains("mac")) {
			String NewTab = Keys.chord(Keys.COMMAND, Keys.RETURN);
			topicclick.sendKeys(NewTab);
			Thread.sleep(2000);
		} else {
			String NewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
			topicclick.sendKeys(NewTab);
			Thread.sleep(2000);
		}

		ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
		if (child != null) {
			driver.switchTo().window(child.get(1));
		}
		Thread.sleep(2000);
		try {
			// if(driver.getCurrentUrl().contains(expectedUrl+"index.html")){
			if (driver.getCurrentUrl().contains(expectedUrl)) {
				System.out.println("Pass for " + topicname);
				info(driver, driver.getCurrentUrl());
				pass(driver, "Pass for " + topicname);
			} else {
				System.out.println("Fail for " + topicname);
				fail(driver, "Fail for " + topicname);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		} finally {
			if (child.size() > 1) {
				driver.close();
			}
			driver.switchTo().window(parent);
		}
		Thread.sleep(3000);

		System.out.println("~~~~~~~~~~~~~~~~");
	}

	// Don`t Miss puff list
	@Test(retryAnalyzer = Retry.class, description = "Click on top 2 articles of pufflist")
	public void g_DontMiss() throws Exception {
		System.out.println("---------------------");
		System.out.println("****DontMiss****");
		System.out.println("---------------------");
		topiclink(driver);

		WebElement element = driver.findElement(By.cssSelector(".femail.item"));
		String puffhead = driver.findElement(By.cssSelector(".femail.item>div>h3")).getText();
		List<WebElement> puffarticles = driver.findElements(By.cssSelector(".femail.item>div>ul>li>a"));
		String puffheading = "DON'T MISS";

		PuffList.pufflist(driver, element, puffhead, puffarticles, puffheading);

		System.out.println(line);
	}

	// Weather
	@Test(retryAnalyzer = Retry.class, description = "Weather icons and info. displayed on page")
	public void h_WeathreTest() throws Exception {
		System.out.println("------------------------");
		System.out.println("****WeathreTest****");
		System.out.println("------------------------");
		topiclink(driver);

		Weather.Weathericon(driver);
		System.out.println(line);
	}

	public static void topiclink(WebDriver driver) throws Exception {

		driver.get(Url.baseurl + "/topics");

		// driver.findElement(By.linkText("Topics Index")).click();
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		System.out.println(driver.getCurrentUrl());
		info(driver, driver.getCurrentUrl());
		try {
			Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Url.baseurl + "/topics"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return;
		}
	}

}
