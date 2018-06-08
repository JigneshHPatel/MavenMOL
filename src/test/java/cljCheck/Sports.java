package cljCheck;

import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import browsers.Url;
import config.LinkTest;
import config.MastHead;
import config.PageFooter;
import config.PuffList;
import config.Retry;
import config.WaitObj;
import config.Weather;

/**
 * @author jigneshkumar.patel
 *
 *
 *         Sports Menu Nav Team pages, Matchzone
 *
 *
 *         New tab only works in windows for MAC need to change CONTROL to
 *         COMMAND for Newtab
 */

public class Sports extends browsers.BrowserStack {

	// ad under search
	@Test(retryAnalyzer = Retry.class, description = "ad under search option on sport page")
	public void a_Ad() throws Exception {
		System.out.println("-----------------");
		System.out.println("****Ad's****");
		System.out.println("-----------------");

		sportlink(driver);

		if (driver.findElement(By.cssSelector(".sciencetech.item.html_snippet_module")).isDisplayed()) {
			System.out.println("Ad wrapper under search on top of page is present ");
			pass(driver, "Ad wrapper under search on top of page is present ");

		} else {
			System.out.println("Ad wrapper under search on top of page is NOT present ");
			fail(driver, "Ad wrapper under search on top of page is NOT present ");
		}
		System.out.println(line);
	}

	// STARS OF SPORT Puff list
	@Test(retryAnalyzer = Retry.class, description = "START OF SPORT (1st) puff list on sport page")
	public void b_StartsOfSportPuff() throws Exception {
		System.out.println("-----------------");
		System.out.println("****StartsOfSportPuff****");
		System.out.println("-----------------");

		sportlink(driver);

		WebElement element = driver.findElement(By.xpath("//*[@data-track-module='llg-1001803^puff']"));
		// JsExecutor.scrollIntoView(driver, element);
		String puffhead = driver.findElement(By.xpath("//*[text()='STARS OF SPORT']")).getText();
		List<WebElement> puffarticles = driver
				.findElements(By.xpath("//*[@data-track-module='llg-1001803^puff']/ul/li/a"));
		String puffheading = "STARS OF SPORT";
		PuffList.pufflist(driver, element, puffhead, puffarticles, puffheading);
		System.out.println(line);
	}

	// MORE START OF SPORTS Puff list
	@Test(retryAnalyzer = Retry.class, description = "MORE START OF SPORT (1st) puff list on sport page")
	public void c_MoreStartofSportsPuff() throws Exception {
		System.out.println("---------------------------------");
		System.out.println("****MoreStartofSportsPuff****");
		System.out.println("---------------------------------");

		sportlink(driver);

		WebElement element = driver.findElement(By.xpath("//*[@data-track-module='llg-62197409^puff']"));
		// JsExecutor.scrollIntoView(driver, element);
		String puffhead = driver.findElement(By.xpath("//*[text()='MORE STARS OF SPORT']")).getText();
		List<WebElement> puffarticles = driver
				.findElements(By.xpath("//*[@data-track-module='llg-62197409^puff']/ul/li/a"));
		String puffheading = "MORE STARS OF SPORT";
		PuffList.pufflist(driver, element, puffhead, puffarticles, puffheading);
		System.out.println(line);
	}

	// MailOnline Sport Newsletter
	@Test(retryAnalyzer = Retry.class, description = "MailOnline Sport Newsletter signup module")
	public void d_SportsNewsletter() throws Exception {
		System.out.println("-------------------------------");
		System.out.println("****SportsNewsletter****");
		System.out.println("-------------------------------");

		sportlink(driver);

		try {
			driver.switchTo().frame(driver.findElement(
					By.xpath("//*[@src='http://i.dailymail.co.uk/i/graphics/2017/04/06/636x140Sport.html']")));
			Thread.sleep(3000);
			if (driver.findElement(By.cssSelector("div.container")).isDisplayed()) {
				assertEquals("MailOnline Sport Newsletter",
						driver.findElement(By.cssSelector("div.channel")).getText());
				System.out.println("MailOnline Sport Newsletter is present");
				pass(driver, "MailOnline Sport Newsletter is present");
				driver.findElement(By.id("Email")).clear();
				driver.findElement(By.id("Email")).sendKeys("testemail.google.com");
				driver.findElement(By.cssSelector("input[type='submit']")).click();
				WebDriverWait wait = new WebDriverWait(driver, 40);
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div.thankyou"))));
				if (driver.findElement(By.cssSelector("div.thankyou")).getText()
						.equalsIgnoreCase("Thanks for signing up. Please check your inbox.")) {
					System.out.println("NewsLetter Sign up Succsessfull");
					pass(driver, "NewsLetter Sign up Succsessfull");
				} else {
					System.out.println("NewsLetter Sign up Failure");
					error(driver, "NewsLetter Sign up Failure");
				}

			}
		} catch (Exception e) {
			System.out.println("MailOnline Sport Newsletter is NOT present");
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
		System.out.println(line);
	}

	// Billbord
	@Test(enabled = false, retryAnalyzer = Retry.class, description = "Billboard on top of Sport page under the menu")
	public void e_BillBoard() throws Exception {
		System.out.println("---------------------");
		System.out.println("****BillBoard****");
		System.out.println("---------------------");

		sportlink(driver);
		if (driver.findElement(By.cssSelector("#billBoard")).isDisplayed()) {
			System.out.println("Billbord is present");
		} else {
			System.out.println("Billbord is NOT present");
		}
		try {
			if (driver.findElement(By.cssSelector("#billBoard")) != null) {
				System.out.println("Billbord Ad is present");
			} else {
				System.out.println("Billbord Ad is NOT present");
			}
			System.out.println(line);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(line);
	}

	// Page Footer
	@Test(retryAnalyzer = Retry.class, description = "Page footer and bottom menu on sport page")
	public void f_Footer() throws Exception {
		System.out.println("--------------------");
		System.out.println("****Footer****");
		System.out.println("--------------------");

		sportlink(driver);
		PageFooter.footer(driver);

		System.out.println(line);
	}

	// Top Menu on page
	@Test(retryAnalyzer = Retry.class, description = "top menu (whole module .page-header.bdrgr2) and masthead of sport channel")
	public void g_TopMenuAndMastHead() throws Exception {
		System.out.println("-------------------");
		System.out.println("***Top Menu***");
		System.out.println("-------------------");

		sportlink(driver);

		// TOPMENU
		System.out.println("====TOP MENU===");
		if (driver.findElement(By.cssSelector(".page-header.bdrgr2")).isDisplayed()) {
			System.out.println("Page header and Menu is present");
			String infoMsg = "Page header and Menu is present";
			info(driver, infoMsg);
		}

		// MastHEAD
		MastHead.mastHead(driver);

		System.out.println(line);
	}

	// Weather
	@Test(retryAnalyzer = Retry.class, description = "Weather icons and info on sport page")
	public void h_weather() throws Exception {
		System.out.println("-------------------");
		System.out.println("****weather****");
		System.out.println("-------------------");

		sportlink(driver);
		Weather.Weathericon(driver);

		System.out.println(line);
	}

	// sports sub-channel
	@Test(enabled = false, retryAnalyzer = Retry.class, description = "All sub channels e.g. football, premier league etc.")
	public void i_SubChannels() throws Exception {
		System.out.println("------------------------");
		System.out.println("****SubChannels****");
		System.out.println("------------------------");

		driver.manage().deleteAllCookies();
		Thread.sleep(3000);
		sportlink(driver);
		Thread.sleep(2000);

		AllMenuSubMenuNav.submenu(driver);

		System.out.println(line);
	}

	// Team pages
	@Test(retryAnalyzer = Retry.class, description = "click on each Team Pages and assert url")
	public void j_TeamPages() throws Exception {
		System.out.println("-----------------------");
		System.out.println("****TeamPages****");
		System.out.println("-----------------------");

		sportlink(driver);

		List<WebElement> totalTeamNames = driver
				.findElements(By.cssSelector(".club-badges.club-badges-big.club-badges-2013-big.cleared>li>a"));
		for (WebElement tm : totalTeamNames) {
			String teamExpectedUrl = tm.getAttribute("href");
			String tname = tm.getAttribute("title");
			System.out.println(
					"===> " + tname + "  " + (totalTeamNames.indexOf(tm) + 1) + "(" + totalTeamNames.size() + ") <===");
			info(driver, "===> " + tname + " <===");

			WaitObj.wait(driver, tm);
			String tmparent = driver.getWindowHandle();

			if (caps.getPlatform().toString().toLowerCase().contains("mac")) {
				System.out.println("Mac newTab method");
				String NewTab = Keys.chord(Keys.COMMAND, Keys.RETURN);
				tm.sendKeys(NewTab);
				Thread.sleep(2000);
			} else {
				System.out.println("Win newTab method");
				String NewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
				tm.sendKeys(NewTab);
				Thread.sleep(3000);
			}
			ArrayList<String> tmchild = new ArrayList<String>(driver.getWindowHandles());
			System.out.println("Total Window handles are " + tmchild.size());
			if (!tmchild.isEmpty()) {
				driver.switchTo().window(tmchild.get(1));
			} else {
				continue;
			}
			Thread.sleep(2000);

			String teamActualUrl = driver.getCurrentUrl();
			if (teamExpectedUrl.equalsIgnoreCase(teamActualUrl)) {
				System.out.println("Teampage is present for " + tname);
				pass(driver, "Teampage is present for " + tname);

				try {
					try {
						/*
						 * if(driver.findElement(By.tagName("h1")).isDisplayed()) {
						 * 
						 * String name=driver.findElement(By.tagName("h1")).getText();
						 * if(name.contains(tname)) {
						 * System.out.println("Teampage <h1> is present for "+tname);
						 * pass(driver,"Teampage h1 is present for "+tname);
						 * 
						 * } }else {
						 * System.out.println("Teampage <h1> is ***NOT*** present for "+tname);
						 * info(driver,"Teampage is h1 ***NOT*** present for "+tname);
						 * System.out.println("----------------"); }
						 */
					} catch (Exception e) {
						System.out.println("***{");
						System.out.println(e.getMessage());
						System.out.println("}***");
						warning(driver, "Teampage is h1 ***NOT*** present for " + tname);
					} finally {

						if (driver.findElement(By.cssSelector(".football-team-news")).isDisplayed()) {
							System.out.println("TEAM AND TRANSFER NEWS Module is present for" + tname);
							pass(driver, "TEAM AND TRANSFER NEWS Module is present for" + tname);
						} else {
							System.out.println("TEAM AND TRANSFER NEWS  Module is ***NOT*** present for" + tname);
							warning(driver, "TEAM AND TRANSFER NEWS Module is ***NOT*** present for" + tname);
						}

						try {
							if (driver.findElement(By.cssSelector(".team-blogs")).isDisplayed()) {
								System.out.println(tname + " Team Blogs is present");
								pass(driver, tname + " Team Blogs is present");
							} else {
								System.out.println(tname + " Team Blogs is ***NOT*** present");
								warning(driver, tname + " Team Blogs is ***NOT*** present");
							}
						} catch (Exception e) {
							System.out.println("***{");
							System.out.println(e.getMessage());
							System.out.println("}***");
							warning(driver, tname + " Team Blogs is ***NOT*** present");
						} finally {

							if (driver.findElement(By.cssSelector(".item.football_team_web_news_wrapper"))
									.isDisplayed()) {
								System.out.println(tname + " News from around the world is present");
								pass(driver, tname + " News from around the world is present");
							} else {
								System.out.println(tname + " News from around the world is ***NOT*** present");
								warning(driver, tname + " News from around the world is ***NOT*** present");
							}
							System.out.println("-----------------------");
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				}

			} else {
				System.out.println("Team Page is ***NOT*** open(expected vs actual url not matching");
				System.out.println("Expected Url is " + teamExpectedUrl);
				System.out.println("Actual Url is " + teamActualUrl);

			}

			if (tmchild.size() > 1) {
				driver.close();
			}

			driver.switchTo().window(tmparent);
			Thread.sleep(2000);
		}
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Match zone, Result module selecting first 2 options and check if renders")
	public void k_MatchZones() throws Exception {
		System.out.println("----------------------");
		System.out.println("****MatchZones****");
		System.out.println("----------------------");

		sportlink(driver);

		for (int i = 1; i <= 18; i++) {
			if (i < 2) {

				// FIXTURES
				driver.findElement(By.xpath("//*[@ class='fixtures-tables']/ul/li[1]/a")).click();
				// dropdown arrow
				driver.findElement(By.xpath("//*[@id='dd']/div/div")).click();
				Thread.sleep(2000);
				// dropdown list
				WebElement linkText = driver.findElement(By.xpath("//*[@id='dd']/ul/li[" + i + "]/a"));
				String link = linkText.getAttribute("href");
				String fname = linkText.getText();
				driver.get(link);
				Thread.sleep(2000);
				try {
					String url = driver.getCurrentUrl();
					if (url.equalsIgnoreCase(link)) {
						driver.findElement(By.cssSelector("div.fixtures-tables-main-panel")).isDisplayed();
						System.out.println("-->" + fname + " is Pass for FIXTURES");
						pass(driver, fname + " is Pass for FIXTURES");
					} else {
						System.out.println("-->" + fname + " is ***FAIL*** for FIXTURES");
						fail(driver, fname + " is ***FAIL*** for FIXTURES");
					}
				} catch (Exception e) {
					System.out.println(fname + " is ***FAIL*** for FIXTURES");
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				}
				// RESULTS
				driver.findElement(By.xpath("//*[@ class='fixtures-tables']/ul/li[2]/a")).click();
				Thread.sleep(2000);
				try {
					String url = driver.getCurrentUrl();
					if (url.equalsIgnoreCase(link + "#results")) {
						driver.findElement(By.cssSelector("div.fixtures-tables-main-panel")).isDisplayed();
						System.out.println("-->" + fname + " is Pass for RESULTS");
						pass(driver, fname + " is Pass for RESULTS");
					} else {
						System.out.println("-->" + fname + " is ***FAIL*** for RESULTS");
						fail(driver, fname + " is ***FAIL*** for RESULTS");
					}
				} catch (Exception e) {
					System.out.println(fname + " is ***FAIL*** for RESULTS");
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				}
				// TABLES
				driver.findElement(By.xpath("//*[@ class='fixtures-tables']/ul/li[3]/a")).click();
				Thread.sleep(2000);
				try {
					String url = driver.getCurrentUrl();
					if (url.equalsIgnoreCase(link + "#tables")) {
						driver.findElement(By.cssSelector("div.fixtures-tables-main-panel")).isDisplayed();
						System.out.println("-->" + fname + " is Pass for TABLES");
						pass(driver, fname + " is Pass for TABLES");
					} else {
						System.out.println("-->" + fname + " is ***FAIL*** for TABLES");
						fail(driver, fname + " is ***FAIL*** for TABLES");
					}
				} catch (Exception e) {
					System.out.println(fname + " is ***FAIL*** for TABLES");
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				}
				// MATCH ZONE
				driver.findElement(By.xpath("//*[@ class='fixtures-tables']/ul/li[4]/a")).click();
				Thread.sleep(2000);
				try {
					String url = driver.getCurrentUrl();
					if (url.equalsIgnoreCase(link + "#matchzone")) {
						driver.findElement(By.cssSelector("div.fixtures-tables-main-panel")).isDisplayed();
						System.out.println("-->" + fname + " is Pass for MATCH ZONE");
						pass(driver, fname + " is Pass for MATCH ZONE");
					} else {
						System.out.println("-->" + fname + " is ***FAIL*** for MATCH ZONE");
						fail(driver, fname + " is ***FAIL*** for MATCH ZONE");
					}
				} catch (Exception e) {
					System.out.println(fname + " is ***FAIL*** for MATCH ZONE");
					System.out.println(e.getMessage());
					error(driver, e.getMessage());
				}
				System.out.println("~~~~˜˜˜˜~~~~˜˜˜˜~~~~˜˜˜˜~~~~");
			}
		}

		System.out.println(line);
	}

	// All elements and images
	@Test(enabled = false, retryAnalyzer = Retry.class, description = "http response of all images and links on sport page")
	public void l_AllElemets() throws Exception {
		System.out.println("-----------------------");
		System.out.println("****AllElemets****");
		System.out.println("-----------------------");

		sportlink(driver);

		Thread.sleep(3000);
		List<WebElement> wele = driver.findElements(By.tagName("a"));
		int we = wele.size();
		System.out.println("Total links are " + we);
		String infoMsg = "Total links are " + we;
		info(driver, infoMsg);

		// images
		List<WebElement> weleim = driver.findElements(By.tagName("img"));
		int img = weleim.size();
		System.out.println("Total images are " + img);
		String imgInfo = "Total images are " + img;
		info(driver, imgInfo);
		LinkTest.httpreq(driver);
		System.out.println(line);
	}

	public static void sportlink(WebDriver driver) throws Exception {
		driver.get(Url.baseurl + "/home/index.html");
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		WaitObj.wait(driver, driver.findElement(By.linkText("Sport")));
		driver.findElement(By.linkText("Sport")).click();
		Thread.sleep(3000);
		try {
			assertEquals(Url.baseurl + "/sport/index.html", driver.getCurrentUrl());
			info(driver, driver.getCurrentUrl());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
	}

}
