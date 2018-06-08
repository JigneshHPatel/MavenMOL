/**
 * 
 */
package cljCheck;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import browsers.Url;
import config.MostSharedObj;
import config.Retry;

/**
 * @author jigneshkumar.patel This class is for RAW work
 */

public class MostShared extends browsers.BrowserStack {
	// public class MostShared extends browsers.BeforeAfter {

	@Test(retryAnalyzer = Retry.class)
	public void MostShared_UK_HomePage() throws Exception {
		// UK Home Page
		System.out.println("*** UK home start ***");
		// info(driver, "*** UK home start ***");
		driver.get(Url.baseurl + "/home/index.html");
		// driver.findElement(By.linkText("Home")).click();
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());
		// driver.get(Url.baseurl+"/home/index.html");
		mostshared(driver);
		System.out.println("*** UK home finished ***");
		System.out.println("~-*-~_~-*-~_~-*-~_~-*-~_~-*-~_~-*");
	}

	@Test(retryAnalyzer = Retry.class)
	public void MostShared_US_HomePage() throws Exception {
		// US Home Page
		System.out.println("*** US home start ***");
		// info(driver, "*** US home start ***");
		driver.get(Url.baseurl + "/ushome/index.html");
		// driver.findElement(By.linkText("U.S.")).click();
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		try {
			assertEquals(driver.getCurrentUrl(), Url.baseurl + "/ushome/index.html");
			System.out.println(driver.getCurrentUrl());
			info(driver, driver.getCurrentUrl());
		} catch (Exception e) {
			System.out.println(e);
			error(driver, e.getMessage());
		}

		mostshared(driver);
		System.out.println("*** US home finished ***");
		System.out.println("~-*-~_~-*-~_~-*-~_~-*-~_~-*-~_~-*");

	}

	public static void mostshared(WebDriver driver) throws Exception {

		MostSharedObj page = new MostSharedObj(driver);
		Actions action = new Actions(driver);
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", page.mostsharedmodule);
		} catch (Exception e1) {
			e1.printStackTrace();
			info(driver, e1.getMessage());
		}
		Thread.sleep(2000);
		if (page.mostsharedmodule.isDisplayed()) {
			try {
				assertEquals(page.mostsharedtitle.getText().toUpperCase(), "MOST SHARED RIGHT NOW");
				pass(driver, "MOST SHARED RIGHT NOW Title is Present");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				error(driver, e.getMessage());
			}
			System.out.println(page.mostsharedtitle.getText() + " Title is Present");

			List<WebElement> msrl = page.linksinmsr;
			System.out.println("Total Articles in Most Shared are " + msrl.size());
			info(driver, "Total Articles in Most Shared are " + msrl.size());
			for (int i = 0; i < 2; i++) {
				String article = page.linksinmsr.get(i).getAttribute("href");
				String parent = driver.getWindowHandle();
				try {
					WebElement element = page.linksinmsr.get(i);
					config.WaitObj.wait(driver, element);

					try {
						action.moveToElement(element).build().perform();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

					if (caps.getPlatform().toString().toLowerCase().contains("mac")) {
						String NewTab = Keys.chord(Keys.COMMAND, Keys.RETURN);
						element.sendKeys(NewTab);
						Thread.sleep(2000);
					} else {
						String NewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);

						element.sendKeys(NewTab);
						Thread.sleep(2000);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				}
				ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(child.get(1));
				Thread.sleep(2000);

				String articleurl = driver.getCurrentUrl();

				if (articleurl.contains(article)) {
					System.out.println(i + 1 + " article pass");
					System.out.println(articleurl);
					pass(driver, "Pass for Shared article " + i);
					info(driver, articleurl);
					System.out.println("~˜~˜~˜~˜~˜~˜~˜~˜~˜~˜~˜~˜~˜~˜~");
				} else {
					System.out.println(i + 1 + " article ***FAIL***");
					fail(driver, i + 1 + " article ***FAIL***");
				}

				if (child.size() > 1) {
					driver.close();
				}

				driver.switchTo().window(parent);
				Thread.sleep(3000);
			}

		} else {
			System.out.println("Most shared module is NOT present");
			fail(driver, "Most Shared Module is ***NOT*** Present ");
		}

	}
}
