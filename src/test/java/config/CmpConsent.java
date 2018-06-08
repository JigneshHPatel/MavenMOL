/**
 * 
 */
package config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author jigneshkumarpatel
 *
 */
public class CmpConsent extends browsers.BeforeAfter {
	public static void gdprConsent(WebDriver driver) throws InterruptedException {

		try {
			if (driver.findElement(By.xpath("//button[text()='Got it']")).isDisplayed()) {
				driver.findElement(By.xpath("//button[text()='Got it']")).click();
				Thread.sleep(2000);
			}
			if (driver.findElement(By.cssSelector("button.mol-ads-cmp--close")).isDisplayed()) {
				driver.findElement(By.cssSelector("button.mol-ads-cmp--close")).click();
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}