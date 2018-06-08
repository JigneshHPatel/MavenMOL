/**
 * 
 */
package browsers;

import java.io.File;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author jigneshkumarpatel
 *
 *         This class is for before set up contains Extent report setup contains
 *         browsers config
 *
 */
public class BeforeAfter {
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest logger;
	public static ExtentHtmlReporter htmlReporter;
	public static String line = "<<==============================>>";
	public static JavascriptExecutor je = (JavascriptExecutor) driver;

	@BeforeSuite
	public void beforeSuite() {

		report = new ExtentReports(System.getProperty("user.dir") + "/testReport/" + Url.currentDate + "-Report.html");
		report.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

	}

	@Parameters("browser")
	@BeforeTest(alwaysRun = true)
	public void setup(String browser, ITestContext ctx) throws Exception {

		String suitTest = ctx.getCurrentXmlTest().getName();

		if (browser.equalsIgnoreCase("chrome")) {

			if (Url.os.contains("win")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (Url.os.contains("mac")) {
				driver = new ChromeDriver();
			}

			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = caps.getBrowserName();
			String browserVersion = caps.getVersion();
			logger = report.startTest("<font color='magenta';>" + suitTest + "</font>");
			String suiteinfo = suitTest + " Start";
			logger.log(LogStatus.INFO, "<font color='cyan';>" + suiteinfo + "</font>");

			if (suitTest.equals("cljTestChrome") && driver != null) {
				logger.log(LogStatus.INFO, "Chrome is open", browserName + "  " + browserVersion);

			} else {
				logger.log(LogStatus.ERROR, "Browser is not open");
			}

		} else if (browser.equalsIgnoreCase("firefox")) {

			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
			if (Url.os.contains("win")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (Url.os.contains("mac")) {
				driver = new FirefoxDriver();
			}
			logger = report.startTest("<font color='magenta';>" + suitTest + "</font>");
			if (driver != null) {
				logger.log(LogStatus.INFO, "Firefox is open");
			} else {
				logger.log(LogStatus.ERROR, "Firefox is not open");
			}

		} else if (browser.equalsIgnoreCase("IE")) {

			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

			logger = report.startTest("<font color='magenta';>" + suitTest + "</font>");
			if (driver != null) {
				logger.log(LogStatus.INFO, "IE is open");
			} else {
				logger.log(LogStatus.ERROR, "IE is not open");
			}

		} else if (browser.equalsIgnoreCase("safari")) {
			logger = report.startTest("<font color='magenta';>" + suitTest + "</font>");
			driver = new SafariDriver();
			if (driver != null) {
				logger.log(LogStatus.INFO, "Safari is open");
			} else {
				logger.log(LogStatus.ERROR, "Safari is not open");
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Url.URL(driver);

		System.out.println("<=======================>");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method, ITestContext ctx) throws Exception {
		String suitTest = ctx.getCurrentXmlTest().getName();
		// logger = report.startTest("<font color='magenta';>"+suitTest+"</font>");
		// String suiteinfo = suitTest+" Start";
		// logger.log(LogStatus.INFO, "<font color='cyan';>"+suiteinfo+"</font>");
		if (suitTest.equals("cljTestChrome") && driver != null) {
			logger = report.startTest(
					("CH" + "-" + "<font color='#b4dcff'>" + this.getClass().getSimpleName() + "-" + method.getName())
							+ "</font>",
					"-" + "<font color='white'>" + method.getName() + "</font>");

		} else if (suitTest.equals("cljTestFirefox") && driver != null) {
			logger = report.startTest(
					("FF" + "-" + "<font color='#b4dcff'>" + this.getClass().getSimpleName() + "-" + method.getName())
							+ "</font>",
					"-" + "<font color='white'>" + method.getName() + "</font>");

		} else if (suitTest.equals("cljTestIE") && driver != null) {
			logger = report.startTest(
					("IE" + "-" + "<font color='#b4dcff'>" + this.getClass().getSimpleName() + "-" + method.getName())
							+ "</font>",
					"-" + "<font color='white'>" + method.getName() + "</font>");

		} else {
			logger.log(LogStatus.ERROR, "Browser is not open");
		}

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result, ITestContext context) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, result.getMethod().toString());
			logger.log(LogStatus.INFO, logger.getDescription());
		}

		report.endTest(logger);
		report.flush();

	}

	@AfterTest(alwaysRun = true)
	public void after() {
		if (driver != null) {
			driver.quit();
		}

	}

	public static void pass(WebDriver driver, String passMsg) {
		logger.log(LogStatus.PASS, "<font color='#008000';>" + passMsg + "</font>");
	}

	public static void fail(WebDriver driver, String failMsg) {
		logger.log(LogStatus.FAIL, "<font color='#dc143c';>" + failMsg + "</font>");
	}

	public static void info(WebDriver driver, String infoMsg) {
		logger.log(LogStatus.INFO, "<font color='#9fdbe2';>" + infoMsg + "</font>");
	}

	public static void error(WebDriver driver, String errorMsg) {
		logger.log(LogStatus.ERROR, "<font color='#FFFF00';>" + errorMsg + "</font>");
	}

}
