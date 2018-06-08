/**
 * 
 */
package config;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author jigneshkumar.patel
 *
 */
public class LinkTest extends browsers.BrowserStack {

	public static void httpreq(WebDriver driver) {

		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total links are " + links.size());
		for (int i = 0; i < links.size(); i++) {
			WebElement ele = links.get(i);
			String url = ele.getAttribute("href");

			verifyLinkActive(url);
		}
	}

	public static void verifyLinkActive(String linkUrl) {

		try {
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
			}

			if (httpURLConnect.getResponseCode() != 200) {

				System.out.println(linkUrl + url + " - " + httpURLConnect.getResponseMessage());
				info(driver, linkUrl + url + " - " + httpURLConnect.getResponseMessage());
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println("*******" + url + " Link not found" + "**********");
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);
				info(driver, "*******" + url + " Link not found" + "**********");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
