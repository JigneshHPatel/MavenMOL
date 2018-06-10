/**
 * @author jigneshkumarpatel
 * BrowsrStack setup
 * before after methods
 * extent report config.
 */
package browsers;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import config.Screenshot;

public class BrowserStack {
	public static Credentials credentials;
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest logger;
	public static ExtentHtmlReporter htmlReporter;
	public static final String line = "⇦⇦⇦⇦⇦⇦⇦⇦⇦⇦࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇࿇⇨⇨⇨⇨⇨⇨⇨⇨⇨⇨";
	public static JavascriptExecutor je = (JavascriptExecutor) driver;
	public static String username;
	public static String key;
	public static String url;
	public static Capabilities caps;
	public static String platformInfo;

	@BeforeSuite
	public void beforeSuite() {
		
		credentials = new Credentials();
		username = credentials.getBrowserStackUsername();
		key = credentials.getBrowserStackKey();
		url = "https://" + username + ":" + key + "@hub-cloud.browserstack.com/wd/hub";

		report = new ExtentReports(System.getProperty("user.dir") + "/testReport/" + Url.currentDate + "/"
				+ Url.currentDate + "-Report.html");
		report.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

	}

	@Parameters(value = { "os", "os_version", "browser", "browser_version" })

	@BeforeTest(alwaysRun = true)
	public void Before(String os, String os_version, String browser, String browser_version, ITestContext ctx)
			throws MalformedURLException {

		String suitTest = ctx.getCurrentXmlTest().getName();
		logger = report.startTest("<font color='magenta';>" + suitTest + "</font>");
		logger.log(LogStatus.INFO, "<font color='cyan';>" + suitTest + "</font>");

		System.out.println("<~˜~˜~~˜~˜~~˜~˜~~˜~˜~>");
	}

	public static void setup(WebDriver driver, String browser, ITestContext ctx) throws Exception {

		String suitTest = ctx.getCurrentXmlTest().getName();

		caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		platformInfo = caps.getPlatform().toString().toLowerCase();
		logger = report.startTest("<font color='magenta';>" + suitTest + "</font>" + browserName);
		logger.log(LogStatus.INFO, "<font color='cyan';>" + suitTest + "</font>");
		info(driver, " Browser Information: " + browserName + browserVersion);
		info(driver, "Platform " + caps.getPlatform());
		System.out.println(browserName + browserVersion + " " + platformInfo);

	}

	@Parameters(value = { "os", "os_version", "browser", "browser_version" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String os, String os_version, String browser, String browser_version, Method method,
			ITestContext ctx) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserstack.debug", "true");
		capabilities.setCapability("browserstack.console", "errors");
		capabilities.setCapability("resolution", "1920x1080");
		capabilities.setCapability("os", os);
		capabilities.setCapability("os_version", os_version);
		capabilities.setCapability("browser", browser);
		capabilities.setCapability("browser_version", browser_version);
		// capabilities.setCapability("browserstack.selenium_version", "3.10.0");
		// capabilities.setCapability("browserstack.local", "true");
		if (browser.equalsIgnoreCase("firefox")) {
			System.out.println(browser + "gecko0.18.0");
			capabilities.setCapability("browserstack.geckodriver", "0.18.0");
		}
		URL serverUrl = new URL(url);
		driver = new RemoteWebDriver(serverUrl, capabilities);

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.getMessage();
		}
		driver.manage().window().maximize();

		String suitTest = ctx.getCurrentXmlTest().getName();
		logger = report.startTest((suitTest + "-" + "<font color='#b4dcff'>" + this.getClass().getSimpleName()
				+ "</font>" + "--" + "<font color='#00ffff'>" + method.getName() + "</font>"));

		logger.log(LogStatus.INFO, "****" + method.getName().toUpperCase() + "****");
		logger.log(LogStatus.INFO, method.getAnnotation(Test.class).description());
		caps = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println(caps.getBrowserName() + caps.getVersion() + "--" + caps.getPlatform());
		info(driver, caps.getBrowserName() + caps.getVersion() + "--" + caps.getPlatform());

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result, ITestContext context, Method method) throws Exception {

		if (result.getStatus() == ITestResult.FAILURE) {
			fail(driver, method.getName());
			info(driver, " Failed Screenshot");

			// Screenshot
			try {
				Screenshot.takeimg(method, result, context);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// error log file
			String fileName = context.getCurrentXmlTest().getName() + method.getName();
			File logFile = new File(
					System.getProperty("user.dir") + "/testReport/" + Url.currentDate + "/" + fileName + ".log");
			try {
				PrintStream ps = new PrintStream(logFile);
				result.getThrowable().printStackTrace(ps);
				ps.close();
				logger.log(LogStatus.INFO, "<a href='" + "./testReport/" + Url.currentDate + "/" + fileName + ".log"
						+ "'target='_blank'>Open this log file for detailed error log</a>");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		report.endTest(logger);
		report.flush();

		if (driver != null) {
			driver.quit();
		}
	}

	@AfterTest(alwaysRun = true)
	public void after() {

		System.gc();
	}

	public static void pass(WebDriver driver, String passMsg) {
		logger.log(LogStatus.PASS, "<font color='#00ff00';>" + passMsg + "</font>");
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

	public static void warning(WebDriver driver, String warningMsg) {
		logger.log(LogStatus.WARNING, "<font color='	#ff7f50'>" + warningMsg + "</font>");
	}

}
