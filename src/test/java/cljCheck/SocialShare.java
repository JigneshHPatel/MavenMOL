/**
 * 
 */
package cljCheck;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import browsers.Url;
import config.Retry;
import config.SocialShareObj;
import config.WaitObj;

/**
 * @author jigneshkumarpatel
 *
 *
 *         Social share icon on homepage,article preview, and article page.
 * @param <WebDriver>
 */

public class SocialShare extends browsers.BrowserStack {

	static Actions action = (Actions) driver;

	@Test(retryAnalyzer = Retry.class, description = "FB icon on top left of the page")
	public void a_FacebookLike_TopLeft_HomePage() throws Exception {
		System.out.println("------------------------------------------------");
		System.out.println("****FacebookLike_TopLeft_HomePage****");
		System.out.println("------------------------------------------------");
		SocialShareObj page = new SocialShareObj(driver);
		// Facebook Like button on top left of page
		driver.get(Url.baseurl + "/home/index.html");
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());

		if (page.facebookbutton.isDisplayed()) {
			System.out.println("Facebook Like button on top right of page is present");
			pass(driver, "Facebook Like button on top right of page is present");
			String name = "facebook"; // page.facebookbutton.getAttribute("data-href");
			System.out.println("Button url is " + name);
			String parent = driver.getWindowHandle();
			try {
				// je.executeScript("arguments[0].click();", page.facebookbutton);
				page.facebookbutton.click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				logger.log(LogStatus.ERROR, e.getMessage());
			}
			Thread.sleep(2000);
			ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(child.get(1));
			Thread.sleep(2000);
			try {
				if (driver.getCurrentUrl().endsWith("facebook.com/DailyMail")) {
					System.out.println("Pass for FB button on top left");
					pass(driver, "Pass for FB button on top left");
				} else {
					System.out.println("FAIL for FB button on top left");
					fail(driver, "FAIL for FB button on top left");
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
			}

		} else {
			System.out.println("Facebook Like button on top right of page is NOT present");
			fail(driver, "Facebook Like button on top right of page is NOT present");
		}

		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Social share icons on article preview on home page, new FB preview and old previews")
	public void b_SocialShareArticlePreview() throws Exception {
		System.out.println("------------------------------------------------");
		System.out.println("****SocialShareArticlePreviewHomePage****");
		System.out.println("------------------------------------------------");
		// SocialShareObj page = new SocialShareObj(driver);
		driver.get(Url.baseurl + "/home/index.html");
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		JavascriptExecutor je = (JavascriptExecutor) driver;

		// New FB icon
		info(driver, "new FB icon test");
		List<WebElement> newFBicon = driver.findElements(By.cssSelector("li.facebook>button"));
		System.out.println("Total New FB icons on Page are " + newFBicon.size());
		info(driver, "Total New FB icons on Page are " + newFBicon.size());
		WebElement fbIcon = newFBicon.get(0);
		String parent = driver.getWindowHandle();
		fbIcon.click();
		Thread.sleep(2000);
		ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(child.get(1));
		Thread.sleep(2000);
		try {
			if (driver.getCurrentUrl().contains("facebook")) {
				System.out.println("Pass for New FB icon");
				pass(driver, "Pass for New FB icon");
			} else {
				System.out.println("Fail for New FB icon");
				fail(driver, "Fail for New FB icon");
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		} finally {
			if (child.size() > 1) {
				driver.close();
			}
			driver.switchTo().window(parent);
			Thread.sleep(2000);
		}

		// Share icon

		List<WebElement> allShareIcon = driver.findElements(By.cssSelector("li.gr3ox>a>span"));
		WebElement shareIcon = allShareIcon.get(0);
		Actions action = new Actions(driver);
		// action.moveToElement(shareIcon).build().perform();
		je.executeScript("arguments[0].scrollIntoView(true);", shareIcon);
		WaitObj.wait(driver, shareIcon);
		je.executeScript("arguments[0].setAttribute('style', 'display: block;');", shareIcon);
		shareIcon.click();
		// je.executeScript("arguments[0].click()", shareIcon);
		Thread.sleep(2000);
		List<WebElement> slinkstop = driver.findElements(By.cssSelector("#dms-drawer>div>ul>li"));

		System.out.println("Top Social Share Icons are " + slinkstop.size());
		info(driver, "Top Social Share Icons are " + slinkstop.size());

		for (WebElement social_icon : slinkstop) {
			String socialIconName = social_icon.getAttribute("class");
			System.out.println(socialIconName + "icon");
			info(driver, socialIconName + "icon");
			if (socialIconName.contains("print")) {
				if (social_icon.isDisplayed()) {
					System.out.println(socialIconName + " is displayed");
					pass(driver, socialIconName + " is displayed");
				} else {
					System.out.println(socialIconName + " is ***NOT*** displayed");
					fail(driver, socialIconName + " is ***NOT*** displayed");
				}
				continue;
			}
			if (socialIconName.contains("email")) {
				if (social_icon.isDisplayed()) {
					System.out.println(socialIconName + " is displayed");
					pass(driver, socialIconName + " is displayed");
				} else {
					System.out.println(socialIconName + " is ***NOT*** displayed");
					fail(driver, socialIconName + " is ***NOT*** displayed");
				}
				continue;
			}
			String parent2 = driver.getWindowHandle();
			try {
				action.moveToElement(social_icon).build().perform();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(social_icon));

			try {
				social_icon.click();
			} catch (Exception e) {
				e.printStackTrace();
				info(driver, e.getMessage());
			}
			Thread.sleep(2000);
			ArrayList<String> child2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(child2.get(1));
			Thread.sleep(2000);
			String currentUrl = driver.getCurrentUrl();

			try {
				if (currentUrl.toLowerCase().contains("dailymail")
						|| currentUrl.toLowerCase().contains(socialIconName)) {
					System.out.println("Test Pass for " + socialIconName);
					pass(driver, "Test Pass for " + socialIconName);
				} else {
					System.out.println("Test Fail for " + socialIconName);
					fail(driver, "Test Fail for " + socialIconName);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (child2.size() > 1) {
					driver.close();
				}
				driver.switchTo().window(parent2);
				Thread.sleep(2000);
			}

		}
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Social shares under search bar on homepage")
	public static void c_SocialShare_UnderSearch_HomePage() throws Exception {
		System.out.println("------------------------------------------------");
		System.out.println("****SocialShare_UnderSearch_HomePage****");
		System.out.println("------------------------------------------------");
		SocialShareObj page = new SocialShareObj(driver);

		// Social share module under search option
		driver.get(Url.baseurl + "/home/index.html");
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,350);");
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<WebElement> searchSocialIcon = page.sociallinkshome;
		System.out.println("Total social icon under search module are " + searchSocialIcon.size());
		info(driver, "Total social icon under search module are " + searchSocialIcon.size());
		config.SocialShareObj.socialShareArticle(driver, searchSocialIcon);
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Social Shares under search on Article page")
	public void d_SocialShare_underSearch_Article() throws Exception {
		System.out.println("***Social Icons Under Search in Article ***");

		SocialShareObj page = new SocialShareObj(driver);
		driver.get(Url.articleUrl);
		// page.firstarticle.click();
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,350);");
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Social share module under search option

		List<WebElement> sci = page.sociallinkshome;
		System.out.println("Article page Total social icon under search module are " + sci.size());
		info(driver, "Article page Total social icon under search module are " + sci.size());
		config.SocialShareObj.socialShareArticle(driver, sci);

		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Social shares on top of article/ under headline")
	public void e_SocialShare_Top_Article() throws Exception {
		// Social icon under article heading
		System.out.println("------------------------------------------------");
		System.out.println("***Social Icons Under ArticleHeading***");
		System.out.println("------------------------------------------------");
		SocialShareObj page = new SocialShareObj(driver);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		driver.get(Url.articleUrl);
		// page.firstarticle.click();
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#shareLinkTop>a")));
		try {
			je.executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.cssSelector("#shareLinkTop>a")));
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}
		List<WebElement> slinkstop = page.articletopsociallinks;
		System.out.println("Top Social Share Icons are " + slinkstop.size());
		info(driver, "Top Social Share Icons are " + slinkstop.size());
		config.SocialShareObj.socialShareArticle(driver, slinkstop);

		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Socialshares at bottom of Article")
	public void f_SocialShare_Bottom_Article() throws Exception {
		// Social icon bottom of article
		System.out.println("------------------------------------------------");
		System.out.println("***Social Icons at Bottom of Article***");
		System.out.println("------------------------------------------------");
		SocialShareObj page = new SocialShareObj(driver);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		driver.get(Url.articleUrl);
		// page.firstarticle.click();

		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());
		try {
			je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("shareLinkTop")));

			if ("Share or comment on this article".equalsIgnoreCase(page.social_links_title.getText())) {
				System.out.println(page.social_links_title.getText());
				info(driver, page.social_links_title.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}

		List<WebElement> social_link_bottom = page.social_links_bottom;
		System.out.println("Bottom Social Share Icons are " + social_link_bottom.size());
		info(driver, "Bottom Social Share Icons are " + social_link_bottom.size());
		config.SocialShareObj.socialShareArticle(driver, social_link_bottom);

		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "FB icon on flyout on Article page")
	public void g_FB_Icon_FlyOut_onArticle() throws Exception {
		System.out.println("------------------------------------------------");
		System.out.println("****FB_Icon_FlyOut_onArticle****");
		System.out.println("------------------------------------------------");
		// SocialShareObj page = new SocialShareObj(driver);
		driver.get(Url.articleUrl);
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		// page.firstarticle.click();
		String parent = driver.getWindowHandle();
		// je.executeScript("arguments[0].scrollIntoView(true);",
		// page.social_links_title);
		driver.findElement(By.cssSelector("a.comments-count.home")).click();
		Thread.sleep(3000);
		try {
			if (driver.findElement(By.cssSelector(".floater-19vuu")).isDisplayed()) {
				driver.findElement(By.cssSelector(".closeButton-1i2Y6")).click();

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			if (driver.findElement(By.cssSelector("span.vjs-flyout-close.vjs-flyout-button")).isDisplayed()) {
				driver.findElement(By.cssSelector("span.vjs-flyout-close.vjs-flyout-button")).click();
			}
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".button-share-facebook")));
			WebElement FBpopIcon = driver.findElement(By.cssSelector(".button-share-facebook"));
			if (FBpopIcon.isDisplayed()) {
				System.out.println("Flyout FB icon is displayed");
				pass(driver, "Flyout FB icon is displayed");
				WaitObj.wait(driver, FBpopIcon);
				Thread.sleep(2000);
				FBpopIcon.click();
				// je.executeScript("arguments[0].click();" , FBpopIcon );
			} else {
				System.out.println("Flyout FB icon is NOT displayed");
				pass(driver, "Flyout FB icon is NOT displayed");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}
		Thread.sleep(3000);
		ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
		if (child != null) {
			driver.switchTo().window(child.get(1));
			Thread.sleep(2000);
		}
		try {
			if (driver.getCurrentUrl().contains("facebook")) {
				System.out.println("FB on flyout is pass");
				pass(driver, "FB on flyout is pass");
			} else {
				System.out.println("FB on flyout is FAIL");
				fail(driver, "FB on flyout is FAIL");
			}
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
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "FB icon on Related module")
	public void h_FB_Icon_on_RelatedAtrticle() throws Exception {
		System.out.println("------------------------------------------------");
		System.out.println("****FB_Icon_on_RelatedAtrticle****");
		System.out.println("------------------------------------------------");
		JavascriptExecutor je = (JavascriptExecutor) driver;
		// Actions action = new Actions(driver);

		SocialShareObj page = new SocialShareObj(driver);

		// page.firstarticle.click();
		driver.get(Url.articleUrl);
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		System.out.println(driver.getCurrentUrl());
		info(driver, driver.getCurrentUrl());

		if (page.relatedarticle.isDisplayed()) {
			WebElement relatedmodule = driver.findElement(By.cssSelector(".rotator-panels.link-bogr1.linkro-ccox"));
			try {
				je.executeScript("arguments[0].scrollIntoView(true);", relatedmodule);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			Thread.sleep(2000);
			System.out.println("Related article module is present");
			pass(driver, "Related article module is present");

			// page.fbcount_onrelated.isDisplayed();
			String parent = driver.getWindowHandle();
			try {
				// action.moveToElement(page.fbicon_onrelated).build().perform();
				je.executeScript("arguments[0].click();", page.fbicon_onrelated);
				// page.fbicon_onrelated.click();
				Thread.sleep(2000);
				ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(child.get(1));
				if (driver.getCurrentUrl().contains("facebook.com")) {
					System.out.println("FB icon on Article is pass");
					pass(driver, "FB icon on Article is pass");
					driver.close();
					driver.switchTo().window(parent);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				error(driver, e.getMessage());
			}

		} else {
			System.out.println("Related article module is NOT present");
			fail(driver, "Related article module is NOT present");
		}
		System.out.println("Related finished");
		System.out.println(line);

	}

	// comment share
	@Test(retryAnalyzer = Retry.class, description = "Social share on comments")
	public void i_SocialShareOn_Comments() throws Exception {

		System.out.println("------------------------------------------------");
		System.out.println("****socialOnComments****");
		System.out.println("------------------------------------------------");
		// SocialShareObj page = new SocialShareObj(driver);

		Actions action = new Actions(driver);
		driver.get(Url.articleUrl);
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		// page.firstarticle.click();
		driver.findElement(By.cssSelector(".count-number")).click();
		WebElement comment = driver
				.findElement(By.xpath("//*[@class='rc-title']/following::div[2]/div[2]/div[1]/div[2]"));
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style','visibility:visible;');",
					comment);
			Thread.sleep(2000);
		} catch (Exception e1) {
			System.out.println("Comment icons not visible");
		}
		try {
			action.moveToElement(comment).build().perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<WebElement> commentshare = driver.findElements(By.xpath(
				"//*[@class='rc-title']/following::div[2]/div[2]/div[1]/div[2]//*[contains(@class, 'ShareComment')]"));
		System.out.println("CommentShare module is " + commentshare.size());
		info(driver, "CommentShare module is " + commentshare.size());
		socialiconclick(driver, commentshare);
		System.out.println(line);

	}

	public static void socialiconclick(WebDriver driver, List<WebElement> eleList) throws Exception {
		Actions action = new Actions(driver);
		for (WebElement shareicon : eleList) {
			String socialname = shareicon.getAttribute("class");
			System.out.println(socialname);
			info(driver, socialname);

			String parent = driver.getWindowHandle();
			try {
				je.executeScript("arguments[0].style.visibility='visible';", shareicon);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			try {
				action.moveToElement(shareicon).build().perform();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			try {
				shareicon.click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				error(driver, e.getMessage());
			}

			Thread.sleep(2000);
			ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
			try {

				driver.switchTo().window(child.get(1));
				Thread.sleep(4000);
				if (socialname.equals("fShareComment")) {
					String facebook = socialname.replaceAll(socialname, "facebook");
					// System.out.println(facebook);
					if (driver.getCurrentUrl().contains(facebook)) {
						System.out.println("pass for " + facebook);
						pass(driver, "pass for " + facebook);
					}
				} else if (socialname.equals("tShareComment")) {
					String twitter = socialname.replaceAll(socialname, "twitter");
					if (driver.getCurrentUrl().contains(twitter)) {
						System.out.println("pass for " + twitter);
						pass(driver, "pass for " + twitter);
					}
				} else if (socialname.equals("pShareComment")) {
					String pinterest = socialname.replaceAll(socialname, "pinterest");
					if (driver.getCurrentUrl().contains(pinterest)) {
						System.out.println("pass for " + pinterest);
						pass(driver, "pass for " + pinterest);
					}
				} else if (socialname.equals("gShareComment")) {
					String google = socialname.replaceAll(socialname, "google");
					if (driver.getCurrentUrl().contains(google)) {
						System.out.println("pass for " + google);
						pass(driver, "pass for " + google);
					}
				} else {
					System.out.println("Fail for " + socialname);
					fail(driver, "Fail for " + socialname);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				fail(driver, e.getMessage());
			}

			if (child.size() > 1) {
				driver.close();
			}
			driver.switchTo().window(parent);
			Thread.sleep(2000);
		}
	}
}
