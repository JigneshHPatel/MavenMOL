/**
 * 
 */
package commonLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pageObjects.ArticlePageObj;

/**
 * @author jigneshkumarpatel
 *
 */
public class InfiniteScroll extends browsers.BrowserStack {

	public static void articleInfiniteScroll(WebDriver driver) throws InterruptedException {

		ArticlePageObj articleObj = new ArticlePageObj(driver);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", articleObj.infiniteModule);
		Thread.sleep(2000);

		for (int i = 1; i < 18; i++) {
			try {
				WebElement infiniteArticle = driver.findElement(By.xpath(articleObj.infiniteListArticle(i)));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", infiniteArticle);
				Thread.sleep(3000);
			} catch (Exception e) {
				System.out.println(i);
				break;
			}
		}
		WaitMethods.WaitUntilPageLoaded(driver);
	}

}
