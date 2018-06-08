package cljCheck;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import browsers.Url;
import config.Retry;

public class SearchPage extends browsers.BrowserStack {
	@Test(retryAnalyzer = Retry.class, description = "Searching word from home page and assert visibility of articles in SearchPage")
	public void a_SearchResult() throws Exception {
		System.out.println("-----------------------");
		System.out.println("****Search Page****");
		System.out.println("-----------------------");
		searchPage(driver);

		String MostRecentmsg = "Total Most Recent searched articles are ";
		searchResult(driver, MostRecentmsg);

		driver.findElement(By.cssSelector("#orderBy2")).click();
		Thread.sleep(6000);
		try {
			if (driver.findElement(By.cssSelector(".sch-results")).isDisplayed()) {
				System.out.println("search result is displayed in Oldest");
				pass(driver, "search result is displayed in Oldest");

				String Oldestmsg = "Total articles in Oldest are ";
				searchResult(driver, Oldestmsg);
			} else {
				System.out.println("search result is NOT displayed in Oldest");
				fail(driver, "search result is NOT displayed in Oldest");
			}
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}
		driver.findElement(By.cssSelector("#sort")).click();
		Thread.sleep(6000);
		try {
			if (driver.findElement(By.cssSelector(".sch-results")).isDisplayed()) {
				System.out.println("search result is displayed in Relevent");
				pass(driver, "search result is displayed in Relevent");
				String Relaventmsg = "Total articles in Relevent are ";
				searchResult(driver, Relaventmsg);
			} else {
				System.out.println("search result is NOT displayed in Oldest in Relevent");
				fail(driver, "search result is NOT displayed in Oldest in Relevent");
			}
			if (driver.findElement(By.cssSelector(".sch-res-title")).getText().contains(Url.search)) {
				System.out.println("Search result article header contains " + Url.search + " word in Relevent option");
				pass(driver, "Search result article header contains " + Url.search + " word");
			}
		} catch (Exception e) {
			e.printStackTrace();
			error(driver, e.getMessage());
		}
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Advance search options by channel in search page")
	public void b_AdvanceSearchByChannel() throws Exception {
		System.out.println("--------------------------");
		System.out.println("****AdvanceSearchByChannel****");
		System.out.println("--------------------------");
		searchPage(driver);

		// Advance search heading
		try {
			String advaceFilter = driver.findElement(By.xpath("//*[text()=' ADVANCED SEARCH FILTERS']")).getText();
			Assert.assertTrue(advaceFilter.equalsIgnoreCase("ADVANCED SEARCH FILTERS"));
			System.out.println("Advance search heading is present");
			pass(driver, "Advance search heading is present");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail(driver, "Advance search heading is ***NOT*** present");
		}

		// Channel
		if (driver.findElement(By.cssSelector("div.searchnavigator.searchnavigator-first.cleared")).isDisplayed()) {
			System.out.println("Channel options module is present");
			pass(driver, "Channel options module is present");
			List<WebElement> allChannels = driver.findElements(By.cssSelector("#Channel>li>input"));
			System.out.println("Total channel filters are " + allChannels.size());
			info(driver, "Total channel filters are " + allChannels.size());
			for (WebElement channel : allChannels) {
				if (channel.isDisplayed()) {
					System.out.println(channel.getAttribute("value") + " is present");
					pass(driver, channel.getAttribute("value") + " is present");
				}
			}

			// Select science channel checkbox
			driver.findElement(By.cssSelector("input[value='sciencetech']")).click();

			// Click on GO
			driver.findElement(By.xpath("//*[@class='searchNavigators cleared xogr1 ccox']/span[1]/button/span[1]"))
					.click();
			Thread.sleep(3000);

			// Verify result contains science channel only
			List<WebElement> allArticles = driver
					.findElements(By.cssSelector("h2.sch-res-section.cleared.link-wocc>a"));
			int totalArticles = allArticles.size();
			System.out.println("Total articles are " + totalArticles);
			info(driver, "Total articles are " + totalArticles);
			for (WebElement article : allArticles) {
				if (article.getText().equalsIgnoreCase("Science")) {
					System.out.println(
							"Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") Pass");
					pass(driver, "Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") Pass");
				} else {
					System.out.println(
							"Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") ***FAIL***");
					fail(driver,
							"Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") ***FAIL***");

				}
			}
		}
		System.out.println(line);

	}

	@Test(retryAnalyzer = Retry.class, description = "Advance search options by Content Type in search page")
	public void c_AdvanceSearchByContentType() throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("****AdvanceSearchByContentType****");
		System.out.println("---------------------------------------");
		searchPage(driver);

		// Select (Untick article and video) Fact Box checkbox
		driver.findElement(By.cssSelector("input[value='article']")).click();
		driver.findElement(By.cssSelector("input[value='video']")).click();

		// Click on GO
		driver.findElement(By.xpath("//*[@class='searchNavigators cleared xogr1 ccox']/span[1]/button/span[1]"))
				.click();
		Thread.sleep(3000);

		// Verify result contains factboxes only
		List<WebElement> allArticles = driver.findElements(By.cssSelector("div.sch-res-content>span"));
		int totalArticles = allArticles.size();
		System.out.println("Total articles are " + totalArticles);
		info(driver, "Total articles are " + totalArticles);
		for (WebElement article : allArticles) {
			if (article.getText().equalsIgnoreCase("Fact Box")) {
				System.out.println("Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") Pass");
				pass(driver, "Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") Pass");
			} else {
				System.out.println(
						"Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") ***FAIL***");
				fail(driver, "Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") ***FAIL***");

			}
		}
		System.out.println(line);
	}

	@Test(retryAnalyzer = Retry.class, description = "Advance search options by Author in search page")
	public void d_AdvanceSearchByAuthor() throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("****AdvanceSearchByAuthor****");
		System.out.println("---------------------------------------");
		searchPage(driver);

		// Select Author
		driver.findElement(By.cssSelector("input[value='Associated Press']")).click();

		// Click on GO
		driver.findElement(By.xpath("//*[@class='searchNavigators cleared xogr1 ccox']/span[1]/button/span[1]"))
				.click();
		Thread.sleep(3000);

		// Verify result contains Author
		List<WebElement> allArticles = driver.findElements(By.cssSelector("div.sch-result.home.cleared>div>h4>a"));
		int totalArticles = allArticles.size();
		System.out.println("Total articles are " + totalArticles);
		info(driver, "Total articles are " + totalArticles);
		for (WebElement article : allArticles) {
			if (article.getText().contains("Associated Press")) {
				System.out.println("Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") Pass");
				pass(driver, "Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") Pass");
			} else {
				System.out.println(
						"Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") ***FAIL***");
				fail(driver, "Article " + (allArticles.indexOf(article) + 1) + "of(" + totalArticles + ") ***FAIL***");

			}
		}

		System.out.println(line);

	}

	@Test(retryAnalyzer = Retry.class, description = "Advance search options by Topic e.g. factbox in search page")
	public void e_AdvanceSearchByTopic() throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("****AdvanceSearchByTopic****");
		System.out.println("---------------------------------------");
		searchPage(driver);

		// Select Nasa checkbox
		driver.findElement(By.cssSelector("input[value='Nasa']")).click();

		// Click on GO
		driver.findElement(By.xpath("//*[@class='searchNavigators cleared xogr1 ccox']/span[1]/button/span[1]"))
				.click();
		Thread.sleep(3000);

		// Verify result contains science channel only
		String msg = "Total article displayed in Nasa are ";
		searchResult(driver, msg);

		System.out.println(line);

	}

	@Test(retryAnalyzer = Retry.class, description = "Advance search options by Day in search page")
	public void f_AdvanceSearchByDay() throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("****AdvanceSearchByDays****");
		System.out.println("---------------------------------------");
		searchPage(driver);
		DateFormat dateFormat = new SimpleDateFormat("MMMM d");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		String th;
		DateFormat dateFormat1 = new SimpleDateFormat(" yyyy");
		Date date2 = new Date();
		String currentDate2 = dateFormat1.format(date2);

		if (currentDate.endsWith("11")) {
			th = "th";
		}

		else if (currentDate.endsWith("12")) {
			th = "th";
		} else if (currentDate.endsWith("13")) {
			th = "th";
		} else if (currentDate.endsWith("1")) {
			th = "st";
		} else if (currentDate.endsWith("2")) {
			th = "nd";
		} else if (currentDate.endsWith("3")) {
			th = "rd";
		} else {
			th = "th";
		}
		String expectedDate = currentDate + th + currentDate2;
		// System.out.println("Expected date is: "+expectedDate);

		// clear search box
		driver.findElement(By.id("searchPhrase")).clear();

		// Select Today checkbox
		driver.findElement(By.cssSelector("input[value='today']")).click();

		// Click on GO
		driver.findElement(By.xpath("//*[@class='searchNavigators cleared xogr1 ccox']/span[1]/button/span[1]"))
				.click();
		Thread.sleep(3000);

		// Verify result articles are Today's

		List<WebElement> allArticles = driver.findElements(By.cssSelector(".sch-res-content>h4"));
		int totalArticles = allArticles.size();
		System.out.println("Total articles are " + totalArticles);
		info(driver, "Total articles are " + totalArticles);
		for (WebElement article : allArticles) {
			// System.out.println(article.getText());
			if (article.getText().contains(" - ")) {
				String[] dateString = article.getText().split(" - ");
				String rawDate = dateString[1];
				String[] formated = rawDate.split(",");
				String actualDate = formated[0];
				// System.out.println((allArticles.indexOf(article)+1)+"actualDate is:
				// "+actualDate);
				try {
					if (actualDate.equalsIgnoreCase(expectedDate)) {
						System.out.println(
								"Pass for " + (allArticles.indexOf(article) + 1) + "(" + allArticles.size() + ")");
						System.out.println("expected: " + expectedDate + " actual: " + actualDate);
						pass(driver, "Pass for " + (allArticles.indexOf(article) + 1) + "(" + allArticles.size() + ")");
					} else if (!actualDate.equalsIgnoreCase(expectedDate)) {
						System.out.println("Date not matching for  " + (allArticles.indexOf(article) + 1) + "("
								+ allArticles.size() + ")");
						System.out.println("expected: " + expectedDate + " actual: " + actualDate);
						warning(driver, "Date not matching for  " + (allArticles.indexOf(article) + 1) + "("
								+ allArticles.size() + ")");
						info(driver, "expected: " + expectedDate + " actual: " + actualDate);

					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				continue;
			}
		}
		System.out.println(line);
	}

	public static void searchPage(WebDriver driver) throws Exception {
		driver.manage().deleteAllCookies();
		Thread.sleep(5000);
		Url.URL(driver);

		// Typing search word in search box
		driver.findElement(By.xpath("//*[@name='searchPhrase']")).clear();
		driver.findElement(By.xpath("//*[@name='searchPhrase']")).sendKeys(Url.search);
		try {
			assertEquals(Url.search, driver.findElement(By.xpath("//*[@name='searchPhrase']")).getAttribute("value"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			error(driver, e.getMessage());
		}

		// Click on search button
		driver.findElement(By.cssSelector(".bing-logo")).click();

		// Assertion
		try {
			if (driver.findElement(By.cssSelector(".sch-head.home.cleared>h1")).isDisplayed()) {
				System.out.println("Search heading is present");
				pass(driver, "Search heading is present");
			} else {
				System.out.println("Search heading is NOT present");
				fail(driver, "Search heading is NOT present");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void searchResult(WebDriver driver, String msg) {
		List<WebElement> mr = driver.findElements(By.cssSelector(".sch-res-title"));
		int mrn = mr.size();
		System.out.println(msg + mrn);
		System.out.println("-----------");

	}

}
