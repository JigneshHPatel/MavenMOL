/**
 * 
 */
package cljCheck;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import browsers.Url;
import config.Retry;

/**
 * @author jigneshkumarpatel
 *
 *
 *         Previous Next control on article page
 */
// public class PreNextCtr extends browsers.BeforeCH{
public class PreNextCtr extends browsers.BrowserStack {

	@Test(retryAnalyzer = Retry.class)
	public void PreNextControls() throws Exception {
		System.out.println("****PreNextControls****");
		// SocialShareObj page= new SocialShareObj(driver);
		driver.get(Url.articleUrl);
		// page.firstarticle.click();
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);
		String firstarticleurl = driver.getCurrentUrl();
		System.out.println(firstarticleurl);
		info(driver, "Current Article is  " + firstarticleurl);
		JavascriptExecutor je = (JavascriptExecutor) driver;

		// Next Button
		System.out.println("~~~~~~~~~~~~~~~" + System.lineSeparator() + "Next Button" + System.lineSeparator()
				+ "~~~~~~~~~~~~~~~");
		WebElement element = driver.findElement(By.cssSelector(".nextBtn-3HPLH"));
		je.executeScript("arguments[0].scrollIntoView(true);", element);
		je.executeScript("arguments[0].style.display='block';", element);
		try {
			je.executeScript("arguments[0].click();", element);
			// driver.findElement(By.cssSelector(".nextBtn-3HPLH")).click();
			Thread.sleep(3000);
			String nexturl = driver.getCurrentUrl();
			if (nexturl.equals(firstarticleurl)) {
				System.out.println("Test ***FAIL*** for Next button");
				fail(driver, "Test ***FAIL*** for Next button");
				return;
			} else {
				System.out.println("Test pass for Next button");
				System.out.println(nexturl);
				pass(driver, "Test pass for Next button  " + nexturl);
			}
			WebElement nextelement = driver.findElement(By.cssSelector(".nextBtn-3HPLH"));
			je.executeScript("arguments[0].style.display='block';", nextelement);
			je.executeScript("arguments[0].click();", nextelement);
			Thread.sleep(3000);

			// stop if firefox
			if (caps.getBrowserName().toLowerCase().contains("firefox")) {
				return;
			}
			// Previous button
			System.out.println("~~~~~~~~~~~~~~~" + System.lineSeparator() + "Previous Button" + System.lineSeparator()
					+ "~~~~~~~~~~~~~~~");
			// WebElement pelement =
			// driver.findElement(By.cssSelector("div.previousBtn-2T0ps"));
			je.executeScript("arguments[0].style.display='block';",
					driver.findElement(By.cssSelector("div.previousBtn-2T0ps")));
			WebElement pelement = driver.findElement(By.cssSelector("div.previousBtn-2T0ps"));
			Thread.sleep(2000);
			// new Actions(driver).moveToElement(pelement).click().build().perform();
			je.executeScript("arguments[0].click();", pelement);
			Thread.sleep(3000);
			if (driver.getCurrentUrl().equals(nexturl)) {
				System.out.println(driver.getCurrentUrl());
				info(driver, driver.getCurrentUrl());
				System.out.println("Test pass for Previous button");
				pass(driver, "Test pass for Previous button");
			} else {
				System.out.println("Test ***FAIL*** for Previous button");
				fail(driver, "Test ***FAIL*** for Previous button");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}

		System.out.println(line);

	}

}
