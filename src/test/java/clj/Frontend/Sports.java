package clj.Frontend;

import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import util.Urls;
import commonLibrary.WeatherWidget;
import pageObjects.AllPagesObj;
import pageObjects.SportPageObj;
import util.NewTabValue;
import util.Retry;
import commonLibrary.PageFooter;
import commonLibrary.PuffListMethod;
import commonLibrary.VerifyLinkTest;
import commonLibrary.WaitMethods;

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

	public static void sportlink(WebDriver driver) throws Exception {
		WaitMethods.WaitUntilElementClickable(driver, driver.findElement(By.linkText("Sport")));
		driver.findElement(By.linkText("Sport")).click();
		Thread.sleep(3000);
		try {
			assertEquals(Urls.baseurl + "/sport/index.html", driver.getCurrentUrl());
			info(driver, driver.getCurrentUrl());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
	}

	// STARS OF SPORT Puff list
	@Test(retryAnalyzer = Retry.class, description = "START OF SPORT (1st) puff list on sport page")
	public void a_StartsOfSportPuff() throws Exception {
		SportPageObj sportObj = new SportPageObj(driver);
		sportlink(driver);

		String ExpectedPuffHeading = "STARS OF SPORT";

		PuffListMethod.puffListMethod(driver, sportObj.startsOfSportPuffModule, sportObj.getstartsOfSportPuffHeading(),
				ExpectedPuffHeading, sportObj.startsOfSportPuffArticles);
	}

	// MORE START OF SPORTS Puff list
	@Test(retryAnalyzer = Retry.class, description = "MORE START OF SPORT (1st) puff list on sport page")
	public void b_MoreStartofSportsPuff() throws Exception {
		SportPageObj sportObj = new SportPageObj(driver);
		sportlink(driver);

		String ExpectedPuffHeading = "MORE STARS OF SPORT";

		PuffListMethod.puffListMethod(driver, sportObj.MoreStartsOfSportPuffModule,
				sportObj.getMoreStartsOfSportPuffHeading(), ExpectedPuffHeading,
				sportObj.MoreStartsOfSportPuffArticles);
	}

	// Page Footer
	@Test(retryAnalyzer = Retry.class, description = "Page footer and bottom menu on sport page")
	public void c_Footer() throws Exception {

		sportlink(driver);
		PageFooter.footer(driver);

	}

	// Top Menu on page
	@Test(retryAnalyzer = Retry.class, description = "top menu (whole module .page-header.bdrgr2) and masthead of sport channel")
	public void d_TopMenuAndMastHead() throws Exception {
		AllPagesObj allObj = new AllPagesObj(driver);
		sportlink(driver);

		// TOPMENU
		System.out.println("====TOP MENU===");
		if (allObj.MastHeadAndMenuIsDisplayed()) {
			System.out.println("Page header and Menu is present");
			String infoMsg = "Page header and Menu is present";
			info(driver, infoMsg);
		}

		// MastHEAD
		commonLibrary.MastHead.mastHead(driver);

	}

	// Weather
	@Test(retryAnalyzer = Retry.class, description = "Weather icons and info on sport page")
	public void e_weather() throws Exception {

		sportlink(driver);

		WeatherWidget.WeatherIcons(driver);

	}

	// sports sub-channel
	@Test(enabled = false, retryAnalyzer = Retry.class, description = "All sub channels e.g. football, premier league etc.")
	public void f_SubChannels() throws Exception {

		driver.manage().deleteAllCookies();
		Thread.sleep(3000);
		sportlink(driver);
		Thread.sleep(2000);

		AllMenuSubMenuNav.submenu(driver);

	}

	// Team pages
	@Test(retryAnalyzer = Retry.class, description = "click on each Team Pages and assert url")
	public void g_TeamPages() throws Exception {
		SportPageObj sportObj = new SportPageObj(driver);
		sportlink(driver);

		try {
			sportObj.TeamModuleIsDisplayed();
		} catch (Exception e) {
			System.out.println("Team Module is ***NOT*** displayed");
			fail(driver, "Team Module is ***NOT*** displayed");
			return;
		}
		for (int i = 1; i <= sportObj.TotalTeamIcons.size(); i++) {
			WebElement teamIcon = driver.findElement(By.xpath(sportObj.getTeamIconLink(i)));
			String teamExpectedUrl = teamIcon.getAttribute("href");
			String teamName = teamIcon.getAttribute("title");

			System.out.println("===> " + teamName + "  " + (sportObj.TotalTeamIcons.indexOf(teamIcon) + 1) + "("
					+ sportObj.TotalTeamIcons.size() + ") <===");

			info(driver, "===> " + teamName + " <===");

			WaitMethods.WaitUntilElementClickable(driver, teamIcon);
			String tmparent = driver.getWindowHandle();

			teamIcon.sendKeys(NewTabValue.NewTab());
			Thread.sleep(2000);
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
				System.out.println("Teampage is present for " + teamName);
				pass(driver, "Teampage is present for " + teamName);

				try {
					sportObj.TeamTransferNewsModuleIsDisplayed();
					System.out.println("TEAM AND TRANSFER NEWS Module is present for " + teamName);
					pass(driver, "TEAM AND TRANSFER NEWS Module is present for " + teamName);
				} catch (Exception e) {
					System.out.println("TEAM AND TRANSFER NEWS  Module is ***NOT*** present for " + teamName);
					warning(driver, "TEAM AND TRANSFER NEWS Module is ***NOT*** present for " + teamName);
				}
				try {
					sportObj.TeamBlogIsDisplayed();
					System.out.println(teamName + " Team Blogs is present");
					pass(driver, teamName + " Team Blogs is present");
				} catch (Exception e) {
					System.out.println(teamName + " Team Blogs is ***NOT*** present");
					warning(driver, teamName + " Team Blogs is ***NOT*** present");
				}
				try {

					sportObj.NewsFromAroundtheWorldIsDisplayed();
					System.out.println(teamName + " News from around the world is present");
					pass(driver, teamName + " News from around the world is present");
				} catch (Exception e) {
					System.out.println(teamName + " News from around the world is ***NOT*** present");
					warning(driver, teamName + " News from around the world is ***NOT*** present");
				}
				System.out.println("-----------------------");
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

	}

	@Test(retryAnalyzer = Retry.class, description = "Match zone, Result module selecting first 2 options and check if renders")
	public void h_MatchZones() throws Exception {
		SportPageObj sportObj = new SportPageObj(driver);
		sportlink(driver);
		sportObj.FixtureTab.click();
		Thread.sleep(2000);
		for (int i = 1; i <= 18; i++) {
			List<String> matches = Arrays.asList("fixture", "results", "tables", "matchzone");
			if (i <= 2) {
				sportObj.MatchZoneDropdownArrow.click();
				WebElement linkText = driver.findElement(By.xpath(sportObj.MatchLink(i)));
				String link = linkText.getAttribute("href");
				String matchName = linkText.getText();
				driver.get(link);
				Thread.sleep(2000);
				int a = 1;
				for (String match : matches) {
					driver.findElement(By.xpath(sportObj.FixtureTabs(a))).click();
					Thread.sleep(2000);
					if (match.equals("fixture")) {
						Assert.assertTrue(driver.getCurrentUrl().contains(link));
					} else {
						Assert.assertEquals(driver.getCurrentUrl(), link + "#" + match);
					}
					try {
						sportObj.MatchZoneTableIsDisplayed();
						System.out.println("-->" + matchName + " is Pass for " + match.toUpperCase());
						pass(driver, matchName + " is Pass for " + match.toUpperCase());
					} catch (Exception e) {
						System.out.println(matchName + "Fixture Table is not Displayed for " + match);
					}
					a++;
				}
				System.out.println("-----------------------");
			}
		}

	}

	// All elements and images
	@Test(enabled = false, retryAnalyzer = Retry.class, description = "http response of all images and links on sport page")
	public void i_AllElemets() throws Exception {

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
		VerifyLinkTest.httpreq(driver);

	}

}
