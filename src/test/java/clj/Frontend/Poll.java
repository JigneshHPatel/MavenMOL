/**
 * 
 */
package clj.Frontend;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import util.Urls;
import commonLibrary.PollMethod;
import commonLibrary.WaitMethods;
import util.Retry;

/**
 * @author jigneshkumarpatel
 * 
 *         Poll on channel Share poll
 *
 */
public class Poll extends browsers.BrowserStack {

	@Test(retryAnalyzer = Retry.class, description = "On Channel,Check poll image, Question, Answers, vote on answer and share on social media")
	public void a_PollonChannel() throws Exception {

		WaitMethods.WaitUntilElementClickable(driver, driver.findElement(By.linkText("Sport")));
		driver.findElement(By.linkText("Sport")).click();
		Thread.sleep(3000);
		System.out.println(driver.getTitle());
		info(driver, driver.getTitle());

		PollMethod.poll(driver);

	}

	@Test(retryAnalyzer = Retry.class, description = "On Article, Check poll image, Question, Answers, vote on answer and share on social media")
	public void b_PollonArticle() throws Exception {

		driver.get(Urls.pollArticleUrl);

		Thread.sleep(3000);

		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		info(driver, driver.getTitle());

		PollMethod.poll(driver);

	}

}
