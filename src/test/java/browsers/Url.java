/**
 * @author jigneshkumarpatel
 * 
 * baseurl and article url setup
 * example: Url.baseurl
 * search word
 * Date formate = currentDate
 * os info
 * 
 * 
 */
package browsers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;

public class Url {

	public static final String baseurl = "http://www.dailymail.co.uk";
	public static final String articleUrl = baseurl + "/tvshowbiz/article-5628573/";

	/// sciencetech/article-5562589/Is-Africa-splitting-TWO.html

	public static final String search = "Mars";

	public static WebDriver driver;
	public static String os = System.getProperty("os.name").toLowerCase();
	public static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");
	public static Date date = new Date();
	public static String currentDate = dateFormat.format(date);

	public static void URL(WebDriver driver) throws Exception {
		driver.get(baseurl + "/home/index.html");
		Thread.sleep(3000);
		config.CmpConsent.gdprConsent(driver);

	}

}
