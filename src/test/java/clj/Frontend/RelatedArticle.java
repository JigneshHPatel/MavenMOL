/**
 * 
 */
package clj.Frontend;

import org.testng.annotations.Test;
import commonLibrary.RelatedArticleMethod;
import util.Retry;
import util.Urls;

/**
 * @author jigneshkumarpatel
 *
 */

public class RelatedArticle extends browsers.BrowserStack {

	@Test(retryAnalyzer = Retry.class, description = "check related module, FB icon and related articles")
	public static void RelatedArticles() throws Exception {

		driver.get(Urls.articleUrl);
		Thread.sleep(2000);
		String articleurl = driver.getCurrentUrl();
		System.out.println(articleurl);
		info(driver, articleurl);
		System.out.println("---___---___---");
		RelatedArticleMethod.relatedArticle(driver);

	}
}
