/**
 * 
 */
package cljCheck;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.*;
import org.testng.annotations.*;

import browsers.Url;
import config.FFFconfig;

/**
 * @author jigneshkumar.patel and andrew.ewart
 *
 */
// public class FFF extends browsers.BeforeCH{
public class FFF extends browsers.BeforeAfter {

	@Test(enabled = false)
	public void FFFHomepage() throws Exception {
		System.out.println("****FFFHomepage****");
		driver.get(Url.baseurl + "/home/index.html");
		driver.manage().window().fullscreen();
		Thread.sleep(3000);
		FFFconfig.fffCarosel(driver);
	}

	@Test(enabled = false)
	public void FFFChannel() throws Exception {
		System.out.println("****FFFChannel****");
		driver.manage().window().fullscreen();
		driver.get(Url.baseurl + "/home/index.html");
		driver.findElement(By.linkText("Fashion Finder")).click();
		Thread.sleep(3000);
		String h1 = driver.findElement(By.cssSelector(".h1-page-last-updated>h1")).getText();
		assertEquals("Fashion Finder", h1);
		System.out.println("**** " + driver.findElement(By.xpath("//*[h1]")).getText() + " ****");
		FFFconfig.fffCarosel(driver);
		System.out.println(line);

	}

	@Test
	public void FFF_Article_Wide() throws Exception {
		System.out.println("****FFF_Article_Wide****");
		JavascriptExecutor je = (JavascriptExecutor) driver;
		driver.get(Url.baseurl + "/tvshowbiz/article-5401363/Mia-Wasikowska-turns-heads-plunging-black-gown.html");
		Thread.sleep(2000);
		je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#fff-inline")));
		if (driver.findElement(By.cssSelector("#fff-inline")).isDisplayed()) {
			System.out.println("FFF inline is present on wide article");
			if (driver.findElement(By.cssSelector("#fff-popup-crop>img")).isDisplayed()) {
				System.out.println("Big Preview Image is present");
			}
			if (driver.findElement(By.cssSelector("#fff-inline-image")).isDisplayed()
					&& driver.findElement(By.cssSelector("#fff-inline-image")).isEnabled()) {
				System.out.println("Small Preview Image is present AND clickable");
			}
			if (driver.findElement(By.cssSelector(".fff-social-links")).isDisplayed()) {
				List<WebElement> socials = driver.findElements(By.cssSelector(".fff-social-links>ul>li"));
				for (WebElement socialEle : socials) {
					if (socialEle.isEnabled()) {
						System.out.println("Pass for " + socialEle.getAttribute("class"));
					}
				}
			} else {
				System.out.println("Social icons are not displayed");
			}
			if (driver.findElement(By.cssSelector(".fff-buybtn.fff-main-product")).isDisplayed()
					&& driver.findElement(By.cssSelector(".fff-buybtn.fff-main-product")).isEnabled()) {
				System.out.println("Buy button on main product is displayed and clikable");
			}

			if (driver.findElement(By.cssSelector(".fff-related-product-container>img")).isDisplayed()) {
				List<WebElement> moreProduct = driver
						.findElements(By.cssSelector(".fff-related-product-container>img"));
				System.out.println("Total Products are " + moreProduct.size());
				for (WebElement moreProdImg : moreProduct) {
					if (moreProdImg.isDisplayed() && moreProdImg.isEnabled()) {
						System.out.println("More Products are displayed");
					}
				}
			}

		} else {
			System.out.println("FFF Inline is ***NOT*** present on wide article");
		}
	}

	@Test
	public void FFF_Article_XWide() throws Exception {
		System.out.println("****FFF_Article_XWide****");
		JavascriptExecutor je = (JavascriptExecutor) driver;
		driver.get(Url.baseurl + "/news/article-5627047/Duchess-Cambridge-admitted-Lindo-Wing.html");
		Thread.sleep(2000);
		je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#fff-inline")));
		if (driver.findElement(By.cssSelector("#fff-inline")).isDisplayed()) {
			System.out.println("FFF inline is present on xwide article");
			if (driver.findElement(By.cssSelector("#fff-popup-crop>img")).isDisplayed()) {
				System.out.println("Big Preview Image is present");
			}
			if (driver.findElement(By.cssSelector("#fff-inline-image")).isDisplayed()
					&& driver.findElement(By.cssSelector("#fff-inline-image")).isEnabled()) {
				System.out.println("Small Preview Image is present AND clickable");
			}
			if (driver.findElement(By.cssSelector(".fff-social-links")).isDisplayed()) {
				List<WebElement> socials = driver.findElements(By.cssSelector(".fff-social-links>ul>li"));
				for (WebElement socialEle : socials) {
					if (socialEle.isEnabled()) {
						System.out.println("Pass for " + socialEle.getAttribute("class"));
					}
				}
			} else {
				System.out.println("Social icons are not displayed");
			}
			if (driver.findElement(By.cssSelector(".fff-buybtn.fff-main-product")).isDisplayed()
					&& driver.findElement(By.cssSelector(".fff-buybtn.fff-main-product")).isEnabled()) {
				System.out.println("Buy button on main product is displayed and clikable");
			}

			if (driver.findElement(By.cssSelector(".fff-related-product-container>img")).isDisplayed()) {
				List<WebElement> moreProduct = driver
						.findElements(By.cssSelector(".fff-related-product-container>img"));
				System.out.println("Total Products are " + moreProduct.size());
				for (WebElement moreProdImg : moreProduct) {
					if (moreProdImg.isDisplayed() && moreProdImg.isEnabled()) {
						System.out.println("More Products are displayed");
					}
				}
			}

		} else {
			System.out.println("FFF Inline is ***NOT*** present on xwide article");
		}
	}
}