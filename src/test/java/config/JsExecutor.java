/**
 * 
 */
package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author jigneshkumarpatel
 * 
 *         javaScriptExecutor methods e.g. scrollIntoView, click
 *
 */
public class JsExecutor extends browsers.BrowserStack {
	public static void scrollIntoView(WebDriver driver, WebElement element) throws Exception {
		je.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
	}

	public static void clickElement(WebDriver driver, WebElement element) throws Exception {
		WaitObj.wait(driver, element);
		je.executeScript("arguments[0].click();", element);
		Thread.sleep(2000);

	}

}
