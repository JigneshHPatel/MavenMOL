/**
 * 
 */
package commonLibrary;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author jigneshkumarpatel Different wait method for element to be clickable
 *         or visible
 */
public class WaitMethods extends browsers.BrowserStack {

	public static void WaitUntilElementClickable(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void WaitUntilElementVisible(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void WaitUntilPageLoaded(WebDriver driver) throws InterruptedException {
		for (int j = 0; j < 25; j++) {
			Thread.sleep(1000);
			if (((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
					.equals("complete")) {
				break;
			}
		}
	}

}
