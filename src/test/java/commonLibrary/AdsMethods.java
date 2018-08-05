/**
 * 
 */
package commonLibrary;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.ArticlePageObj;
import util.DataFileReader;

/**
 * @author jigneshkumarpatel This class is for common methods of mpus,
 *         billboards, sky and taboola ads
 */
public class AdsMethods extends browsers.BrowserStack {

	private static List<String> actualAds = new ArrayList<String>();
	private static DataFileReader dataReader = new DataFileReader();

	// MPU, Billboars, Sky, JW Player, inReadPlayer
	public static void adConsole(WebDriver driver) {

		JavascriptExecutor je = (JavascriptExecutor) driver;

		Long a = (Long) je.executeScript("return adverts.performance.state.auctions.length");

		for (int i = 0; i < a; i++) {

			for (String adName : dataReader.getAdNames()) {
				try {
					String AdRendered = "return adverts.performance.state.auctions[" + i + "]['slots']['" + adName
							+ "']['ad rendered']";
					String result = je.executeScript(AdRendered).toString();
					if (result != null) {
						System.out.println(adName + " is Visible " + result);
						System.out.println("-------");
						pass(driver, adName + " is Visible " + result);
						actualAds.add(adName);

					} else {
						System.out.println("**** " + adName + " **** is ***NOT*** rendered");
						warning(driver, "**** " + adName + " **** is ***NOT*** rendered");
					}

				} catch (Exception e) {

				}
			}
		}
		for (String expectedAd : dataReader.getAdNames()) {
			if (!actualAds.contains(expectedAd)) {
				System.out.println(expectedAd + " is NOT Present");
				warning(driver, expectedAd + " is NOT Present");
			}
		}
	}

	public static void taboola(WebDriver driver) throws InterruptedException {

		ArticlePageObj articleObj = new ArticlePageObj(driver);

		if (articleObj.taboolaContainerBeforeMWV.isDisplayed()) {
			System.out.println("Taboola ad Before MWV is displayed");
			System.out.println("Total taboola ads loaded before MWV taboola container are: "
					+ articleObj.taboolaAdsBeforeMWV.size());
			info(driver, "Total taboola ads loaded before MWV taboola container are: "
					+ articleObj.taboolaAdsBeforeMWV.size());

			for (WebElement taboolBeforeMWV : articleObj.taboolaAdsBeforeMWV) {
				System.out.println((articleObj.taboolaAdsBeforeMWV.indexOf(taboolBeforeMWV) + 1) + " ad is present");
				System.out.println(taboolBeforeMWV.getAttribute("title"));
				System.out.println("---------------------");
				pass(driver, (articleObj.taboolaAdsBeforeMWV.indexOf(taboolBeforeMWV) + 1) + " ad is present");
			}
		} else {
			System.out.println("Taboola ad BEFORE MWV is ***NOT*** displayed");
			fail(driver, "Taboola ad BEFORE MWV is ***NOT*** displayed");
		}
		System.out.println("==========================================");

		if (articleObj.taboolaContainerAfterMWV.isDisplayed()) {
			System.out.println("Taboola ad AFTER MWV is displayed");

			for (WebElement taboolaAfterMWV : articleObj.taboolaAdsAfterMWV) {
				System.out.println((articleObj.taboolaAdsAfterMWV.indexOf(taboolaAfterMWV) + 1) + " ad is present");
				System.out.println(taboolaAfterMWV.getAttribute("title"));
				System.out.println("---------------------");
				pass(driver, (articleObj.taboolaAdsAfterMWV.indexOf(taboolaAfterMWV) + 1) + " ad is present");
			}

		} else {
			System.out.println("Taboola ad AFTER MWV is ***NOT*** displayed");
			fail(driver, "Taboola ad AFTER MWV is ***NOT*** displayed");
		}

		System.out.println("==========================================");

		System.out.println("Total Taboola in article page are " + articleObj.taboolaAdsInInfiniteList.size());

		for (WebElement taboola : articleObj.taboolaAdsInInfiniteList) {
			if (taboola.isDisplayed()) {
				System.out.println((articleObj.taboolaAdsInInfiniteList.indexOf(taboola) + 1) + " : "
						+ taboola.getAttribute("id") + "  is displayed");
				pass(driver, taboola.getAttribute("id") + "  is displayed");
			}
		}
	}
}
