/**
 * 
 */
package cljCheck;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import browsers.Url;
import config.Retry;
import config.WaitObj;

/**
 * @author jigneshkumar.patel
 *
 *         This is SEO module test on channel page Test if all article links and
 *         images are displayed open two article from module
 *
 *
 */
public class SEO_Module extends browsers.BrowserStack {

	@Test(retryAnalyzer = Retry.class, description = "SEO module test, test all links and images displayes in module and open two articles from module")
	public static void seoModule() throws Exception {
		driver.get(Url.baseurl + "/home/index.html");
		Thread.sleep(2000);
		config.CmpConsent.gdprConsent(driver);
		try {
			asserEquals(Url.baseurl + "/home/index.html", driver.getCurrentUrl());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		info(driver, driver.getCurrentUrl());
		if (driver.findElement(By.xpath("//*[@data-track-module='seo-articles^article-list-module']")).isDisplayed()) {
			System.out.println("SEO module is present on homepage");
			pass(driver, "SEO module is present on homepage");
			try {
				Assert.assertTrue(
						driver.findElement(By.cssSelector(".heading-3-j4x")).getText().contains("IN OTHER NEWS..."));
				System.out.println("SEO heading is displayed");
				pass(driver, "SEO heading is displayed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			List<WebElement> allSeoArticles = driver.findElements(By.cssSelector(".article-1HIOK>a"));
			int totalSeoArticles = allSeoArticles.size();
			System.out.println("Total articles in SEO module are " + totalSeoArticles);
			info(driver, "Total articles in SEO module are " + totalSeoArticles);
			for (int i = 0; i < totalSeoArticles; i++) {
				WebElement seoArticle = allSeoArticles.get(i);
				if (seoArticle.isDisplayed() && seoArticle.isEnabled()) {
					System.out.println("SEO article " + (i + 1) + " displayed");
					pass(driver, "SEO article " + (i + 1) + " displayed");
				} else {
					System.out.println("SEO article " + (i + 1) + " ***FAILED***");
					fail(driver, "SEO article " + (i + 1) + " ***FAILED***");
				}

			}
			for (int a = 0; a < 2; a++) {
				WebElement seoArticle = allSeoArticles.get(a);
				String seoArticleUrlExpected = seoArticle.getAttribute("href");
				String parent = driver.getWindowHandle();
				WaitObj.wait(driver, seoArticle);
				if (caps.getPlatform().toString().toLowerCase().contains("mac")) {
					String newTab = Keys.chord(Keys.COMMAND, Keys.RETURN);
					seoArticle.sendKeys(newTab);
					Thread.sleep(2000);
				} else {
					String newTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
					seoArticle.sendKeys(newTab);
					Thread.sleep(2000);
				}

				ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
				if (!child.isEmpty()) {
					driver.switchTo().window(child.get(1));
				}
				try {
					// driver.switchTo().wait();
					assertEquals(driver.getCurrentUrl(), seoArticleUrlExpected);
					System.out.println("SEO Article " + (a + 1) + " opened");
					System.out.println(driver.getCurrentUrl());
					pass(driver, "SEO Article " + (a + 1) + " Passed");
					info(driver, driver.getCurrentUrl());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (child.size() > 1) {
					driver.close();
				}
				driver.switchTo().window(parent);
				Thread.sleep(3000);
			}

			// Images
			List<WebElement> allSeoArticleImages = driver.findElements(By.cssSelector(".article-1HIOK>a>img"));
			int totalSeoArticleImages = allSeoArticleImages.size();
			if (totalSeoArticleImages == totalSeoArticles) {
				System.out.println("Total images are present for all SEO articles");
				pass(driver, "Total images are present for all SEO articles");
			}

			for (int img = 0; img < totalSeoArticleImages; img++) {
				WebElement seoArticleImage = allSeoArticleImages.get(img);
				if (seoArticleImage.isDisplayed()) {
					System.out.println("Image is displayed for " + (img + 1));
					pass(driver, "Image is displayed for " + (img + 1));
				}

			}

		} else {
			System.out.println("SEO module is ***NOT*** present on homepage");
			fail(driver, "SEO module is ***NOT*** present on homepage");
		}
	}

	private static void asserEquals(String string, String currentUrl) {
		// TODO Auto-generated method stub

	}

}
