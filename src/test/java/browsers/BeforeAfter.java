/**
 * 
 */
package browsers;

import java.io.File;
import java.io.PrintStream;

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

import util.DataFileReader;
import util.Screenshot;
import util.SystemDateTime;
import util.SystemOSName;

/**
 * @author jigneshkumarpatel
 *
 *         This class is for before set up contains Extent report setup contains
 *         browsers config
 *
 */
public class BeforeAfter {
	private static DataFileReader dataReader = new DataFileReader();

	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest logger;
	public static ExtentHtmlReporter htmlReporter;
	public static String line = "<<==============================>>";
	public static JavascriptExecutor je = (JavascriptExecutor) driver;

	@BeforeSuite
	public void beforeSuite() {

		report = new ExtentReports(System.getProperty("user.dir") + "/testReport/Localbrowser"
				+ SystemDateTime.currentDateTime() + "/" + SystemDateTime.currentDateTime() + "-Report.html");
		report.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

	}

	@Parameters("browser")
	@BeforeTest(alwaysRun = true)
	public void setup(String browser, ITestContext ctx) throws Exception {

		String suitTest = ctx.getCurrentXmlTest().getName();
		logger = report.startTest("<font color='magenta';>" + suitTest + "</font>");
		logger.log(LogStatus.INFO, "<font color='cyan';>" + suitTest + "</font>");

		System.out.println("<~˜~˜~~˜~˜~~˜~˜~~˜~˜~>");

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String browser, Method method, ITestContext ctx) throws Exception {
		String suitTest = ctx.getCurrentXmlTest().getName();

		if (browser.equalsIgnoreCase("chrome")) {

			if (SystemOSName.OSName.contains("win")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (SystemOSName.OSName.contains("mac")) {
				driver = new ChromeDriver();
			}

		} else if (browser.equalsIgnoreCase("firefox")) {

			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
			if (SystemOSName.OSName.contains("win")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (SystemOSName.OSName.contains("mac")) {
				driver = new FirefoxDriver();
			}

		} else if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		} else if (browser.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		driver.get(dataReader.getBaseUrl() + "/home/index.html");
		commonLibrary.CmpConsent.gdprConsent(driver);
		info(driver, driver.getCurrentUrl());
		logger = report.startTest((suitTest + "-" + "<font color='#b4dcff'>" + this.getClass().getSimpleName()
				+ "</font>" + "--" + "<font color='#00ffff'>" + method.getName() + "</font>"));

		logger.log(LogStatus.INFO, "****" + method.getName().toUpperCase() + "****");
		logger.log(LogStatus.INFO, method.getAnnotation(Test.class).description());
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		caps = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println(caps.getBrowserName() + caps.getVersion() + "--" + caps.getPlatform());
		info(driver, caps.getBrowserName() + caps.getVersion() + "--" + caps.getPlatform());
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result, ITestContext context, Method method) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			fail(driver, method.getName());
			logger.log(LogStatus.INFO, logger.getDescription() + "-Fail Screenshot");

			// Screenshot
			Screenshot.takeimg(method, result, context);
			// error log file
			String fileName = method.getName();
			File logFile = new File(System.getProperty("user.dir") + "/testReport/Localbrowser/" + SystemDateTime.currentDateTime() + "/"
					+ fileName + ".log");
			PrintStream ps = new PrintStream(logFile);
			result.getThrowable().printStackTrace(ps);
			ps.close();
			logger.log(LogStatus.INFO,
					"<a href='" + logFile + "'target='_blank'>Open this log file for detailed error log</a>");
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
