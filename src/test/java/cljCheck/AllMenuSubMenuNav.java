/**
 * 
 */
package cljCheck;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import browsers.Url;
import config.MastHead;
import config.PageFooter;
import config.PageLoadedObj;
import config.Retry;
import config.WaitObj;

/**
 * @author jigneshkumarpatel
 *
 *         Menu + Sub Menu navigation all channel/Sub Channel pages
 *
 */

public class AllMenuSubMenuNav extends browsers.BrowserStack {

	public static String menuline = "<<====࿇࿇࿇࿇࿇====࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇====࿇࿇࿇࿇࿇====>>";

	@Test(retryAnalyzer = Retry.class)
	public static void a_Home() throws Exception {
		System.out.println("--------------------------");
		System.out.println("*****All Menu Home****");
		System.out.println("--------------------------");
		Url.URL(driver);
		info(driver, "Homepage start" + driver.getCurrentUrl());

		// MastHead and page footer
		channelPage(driver);

		// Sub Channels
		submenu(driver);
		System.out.println(menuline);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void b_News() throws Exception {
		System.out.println("--------------------------");
		System.out.println("*****All Menu News****");
		System.out.println("--------------------------");
		Url.URL(driver);

		String channel = "News";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void c_US() throws Exception {
		System.out.println("-----------------------");
		System.out.println("*****All Menu US****");
		System.out.println("-----------------------");
		Url.URL(driver);

		String channel = "U.S.";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void d_Sports() throws Exception {
		System.out.println("---------------------------");
		System.out.println("*****All Menu Sports****");
		System.out.println("---------------------------");
		Url.URL(driver);

		String channel = "Sport";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void e_TVShowbiz() throws Exception {
		System.out.println("-------------------------------");
		System.out.println("*****All Menu TVShowbiz****");
		System.out.println("-------------------------------");
		Url.URL(driver);

		String channel = "TV&Showbiz";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void f_Australia() throws Exception {
		System.out.println("-------------------------------");
		System.out.println("*****AllMenuAustralia****");
		System.out.println("-------------------------------");
		Url.URL(driver);

		String channel = "Australia";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void g_Femail() throws Exception {
		System.out.println("------------------------------");
		System.out.println("*****All Menu Femail****");
		System.out.println("------------------------------");
		Url.URL(driver);

		String channel = "Femail";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void h_Health() throws Exception {
		System.out.println("-----------------------------");
		System.out.println("*****All Menu Health****");
		System.out.println("-----------------------------");
		Url.URL(driver);

		String channel = "Health";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void i_Science() throws Exception {
		System.out.println("-------------------------------");
		System.out.println("*****All Menu Science****");
		System.out.println("-------------------------------");
		Url.URL(driver);

		String channel = "Science";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void j_Money() throws Exception {
		System.out.println("-----------------------------");
		System.out.println("*****All Menu Money****");
		System.out.println("-----------------------------");
		Url.URL(driver);

		String channel = "Money";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void k_Video() throws Exception {
		System.out.println("---------------------------");
		System.out.println("*****All Menu Video****");
		System.out.println("---------------------------");
		Url.URL(driver);

		String channel = "Video";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void l_Travel() throws Exception {
		System.out.println("-----------------------------");
		System.out.println("*****All Menu Travel****");
		System.out.println("-----------------------------");
		Url.URL(driver);

		String channel = "Travel";
		MenuNav(driver, channel);
	}

	@Test(retryAnalyzer = Retry.class)
	public static void m_FashionFinder() throws Exception {
		System.out.println("------------------------------------");
		System.out.println("*****All Menu FashionFinder****");
		System.out.println("------------------------------------");
		Url.URL(driver);

		String channel = "Fashion Finder";
		MenuNav(driver, channel);
	}

	// Main Menu Class
	public static void MenuNav(WebDriver driver, String channel) throws Exception {

		WaitObj.wait(driver, driver.findElement(By.linkText(channel)));
		String expectedurl = driver.findElement(By.linkText(channel)).getAttribute("href");

		Actions action = new Actions(driver);
		try {
			action.moveToElement(driver.findElement(By.linkText(channel))).build().perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			WaitObj.wait(driver, driver.findElement(By.linkText(channel)));
			driver.findElement(By.linkText(channel)).click();
		}
		Thread.sleep(2000);
		System.out.println(driver.getCurrentUrl());

		PageLoadedObj.PageLoaded(driver, channel);

		// MastHead and Page footer
		channelPage(driver);

		if (driver.getCurrentUrl().equals(expectedurl)) {
			System.out.println("pass for " + expectedurl);
			pass(driver, "pass for " + expectedurl);
			if (channel.equals("Fashion Finder")) {
				return;
			}
			submenu(driver);
		} else {
			System.out.println("Fail for " + expectedurl);
			fail(driver, "Fail for " + expectedurl);
		}

		System.out.println(menuline);
	}

	// Channel page
	public static void channelPage(WebDriver driver) {
		MastHead.mastHead(driver);
		PageFooter.footer(driver);
	}

	// Sub Channel Class
	public static void submenu(WebDriver driver) throws Exception {
		Thread.sleep(3000);
		String subLine = "*************";
		System.out.println("subchannel start");
		Actions action = new Actions(driver);
		// Count list of elements in sub-channel
		List<WebElement> subM = driver.findElements(By.cssSelector(".nav-secondary ul:first-child li a"));

		String totalsub = "Total SubChannels are " + subM.size();
		System.out.println("Total SubChannels are " + totalsub);
		info(driver, "Total SubChannels are " + totalsub);
		System.out.println("*************");

		for (int i = 0; i < subM.size(); i++) {
			WebElement subLink = subM.get(i);
			WaitObj.wait(driver, subLink);
			String name = subLink.getText();
			System.out.println("===> " + name + " - " + (i + 1) + "of(" + subM.size() + ") <===");
			if (name.equalsIgnoreCase("Promos") || name.equalsIgnoreCase("Rewards")
					|| name.equalsIgnoreCase("Mail Shop") || name.equalsIgnoreCase("Discounts")
					|| name.equalsIgnoreCase("Baby Blog") || name.equalsIgnoreCase("Mail Travel")
					|| name.equalsIgnoreCase("MailEscapes") || name.equalsIgnoreCase("Fantasy")) {
				System.out.println("Skipped for " + name);
				String skipped = "Skipped for " + name;
				info(driver, skipped);
				continue;
			}

			String expectedsuburl = subLink.getAttribute("href");
			String parent = driver.getWindowHandle();
			try {
				action.moveToElement(subLink).build().perform();
			} catch (Exception e1) {

				String errorMsg = e1.getMessage();
				System.out.println(errorMsg);
				// info(driver,errorMsg);
			}

			if (caps.getPlatform().toString().toLowerCase().contains("mac")) {
				System.out.println("Mac newTab method");
				String NewTab = Keys.chord(Keys.COMMAND, Keys.RETURN);
				subLink.sendKeys(NewTab);
				Thread.sleep(2000);
			} else {
				System.out.println("Win newTab method");
				String NewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
				subLink.sendKeys(NewTab);
				Thread.sleep(3000);
			}

			Thread.sleep(2000);
			ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
			System.out.println("Total Windows are " + child.size());
			if (!child.isEmpty()) {
				driver.switchTo().window(child.get(1));
			} else {
				System.out.println("New window is ***NOT*** opened");
			}
			Thread.sleep(2000);

			// PageLoaded
			PageLoadedObj.PageLoaded(driver, name);
			// MastHead
			MastHead.mastHead(driver);

			if (name.equals("Motoring")) {
				try {
					assertEquals(Url.baseurl + "/money/cars/index.html", driver.getCurrentUrl());
					System.out.println("Pass for " + driver.getCurrentUrl());
					pass(driver, "Pass for " + driver.getCurrentUrl());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				} finally {

					if (child.size() > 1) {
						driver.close();
					}
					driver.switchTo().window(parent);
				}
				Thread.sleep(2000);
				System.out.println(subLine);
				continue;
			}
			if (name.equalsIgnoreCase("Columnists")) {
				try {
					Assert.assertTrue(driver.getCurrentUrl().contains("columnists"));
					System.out.println("Pass for " + driver.getCurrentUrl());
					pass(driver, "Pass for " + driver.getCurrentUrl());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				} finally {

					if (child.size() > 1) {
						driver.close();
					}
					driver.switchTo().window(parent);
				}
				Thread.sleep(2000);
				System.out.println(subLine);
				continue;
			}

			if (name.equals("Horoscopes")) {
				try {
					assertEquals(Url.baseurl + "/coffeebreak/horoscopes/index.html", driver.getCurrentUrl());
					System.out.println("Pass for " + driver.getCurrentUrl());
					pass(driver, "Pass for " + driver.getCurrentUrl());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				} finally {

					if (child.size() > 1) {
						driver.close();
					}
					driver.switchTo().window(parent);
				}
				Thread.sleep(2000);
				System.out.println(subLine);
				continue;
			}

			if (name.equalsIgnoreCase("Columnist")) {
				System.out.println(name);
				if (driver.getCurrentUrl().contains("columnists")) {
					System.out.println("Pass for columnist");
					pass(driver, "Pass for columnist");
					continue;
				}

			}
			try {
				if (driver.getCurrentUrl().equals(expectedsuburl)) {
					System.out.println("Pass for " + expectedsuburl);
					pass(driver, "Pass for " + expectedsuburl);
					// linkTest(driver);
				} else {
					System.out.println("Fail for " + expectedsuburl);
					fail(driver, "Fail for " + expectedsuburl);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				error(driver, e.getMessage());
			} finally {
				if (child.size() > 1) {
					driver.close();
				}
				driver.switchTo().window(parent);
				Thread.sleep(2000);
				System.out.println(subLine);
			}
		}

	}

}
