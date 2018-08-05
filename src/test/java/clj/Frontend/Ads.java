/**
 * 
 */
package clj.Frontend;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import commonLibrary.AdsMethods;
import commonLibrary.InfiniteScroll;
import commonLibrary.WaitMethods;
import util.Retry;
import util.Urls;

/**
 * @author jigneshkumarpatel
 *
 */
public class Ads extends browsers.BrowserStack {

	
	@Test(retryAnalyzer = Retry.class, description = "MPU's, Billboards, and Sky ads on home page")
	public void a_MpuOnHomePage() throws Exception {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		WaitMethods.WaitUntilPageLoaded(driver);
		AdsMethods.adConsole(driver);
	}

	@Test(retryAnalyzer = Retry.class, description = "MPU's, Billboards, and Sky ads on home page")
	public void b_MpuOnUSHomePage() throws Exception {

		driver.get(Urls.baseurl + "/ushome");
		WaitMethods.WaitUntilPageLoaded(driver);

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		WaitMethods.WaitUntilPageLoaded(driver);
		AdsMethods.adConsole(driver);
	}

	@Test(retryAnalyzer = Retry.class, description = "MPU's, Billboards, and Sky ads on home page")
	public void c_MpuOnAUHomePage() throws Exception {

		driver.get(Urls.baseurl + "/auhome");
		WaitMethods.WaitUntilPageLoaded(driver);

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		WaitMethods.WaitUntilPageLoaded(driver);
		AdsMethods.adConsole(driver);
	}
	
	@Test(retryAnalyzer = Retry.class, description = "MPU's, Billboards, and Sky ads on Article page")
	public void d_MpuOnAericlePage() throws Exception {

		driver.get(Urls.articleUrl);
		Thread.sleep(5000);
		//WaitMethods.WaitUntilPageLoaded(driver);

		InfiniteScroll.articleInfiniteScroll(driver);
		Thread.sleep(3000);
		WaitMethods.WaitUntilPageLoaded(driver);
		AdsMethods.adConsole(driver);
	}
	
	@Test(retryAnalyzer = Retry.class, description = "Taboola on article page")
	public void e_TaboolaOnArticlePage() throws Exception {
		
		driver.get(Urls.articleUrl);
		Thread.sleep(5000);
		WaitMethods.WaitUntilPageLoaded(driver);
		AdsMethods.taboola(driver);
	}

}
