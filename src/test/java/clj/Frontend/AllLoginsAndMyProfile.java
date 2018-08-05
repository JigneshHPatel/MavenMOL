package clj.Frontend;

import org.testng.annotations.*;
import commonLibrary.LoginAndMyProfileMethods;
import commonLibrary.WaitMethods;
import pageObjects.RegistrationObj;
import util.DataFileReader;
import util.Retry;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllLoginsAndMyProfile extends browsers.BrowserStack {

	private static DataFileReader dataReader = new DataFileReader();

	public static void loginClick(WebDriver driver) throws Exception {
		RegistrationObj regObj = new RegistrationObj(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(regObj.LoginButtonOnHomepage));
		regObj.LoginButtonOnHomepage.click();
		info(driver, "Login is clicked");
		Thread.sleep(2000);
		// Wait until submit/ Login button is click-able on login page
		wait.until(ExpectedConditions.elementToBeClickable(regObj.LoginButtonOnLoginpage));
		info(driver, driver.getCurrentUrl());
	}

	@Test(retryAnalyzer = Retry.class, description = "Direct login and Profile page")
	public static void a_DirectLogin() throws Exception {
		RegistrationObj regObj = new RegistrationObj(driver);
		loginClick(driver);

		String expectedText = "Login";
		String actualText = regObj.getDirectLoginHeadingText();
		WebElement emailField = regObj.DirectLoginEmailField;
		String emailAddress = dataReader.getDirectloginUsername();
		WebElement passField = regObj.DirectLoginPasswordField;
		String password = dataReader.getDirectloginPassword();
		WebElement loginButton = regObj.LoginButtonOnLoginpage;
		String printMsg = "Direct";
		LoginAndMyProfileMethods.AllLoginMethods(driver, expectedText, actualText, emailField, emailAddress, passField, password, loginButton,
				printMsg);

	}

	@Test(retryAnalyzer = Retry.class, description = "Login by Facebook")
	public void b_FacebookLogin() throws Exception {
		RegistrationObj regObj = new RegistrationObj(driver);
		loginClick(driver);

		regObj.FBLoginButtonOnLoginpage.click();
		Thread.sleep(2000);
		String expectedText = "Log into Facebook";
		String actualText = regObj.getFacebookLoginHeadingText();
		WebElement emailField = regObj.FacebookLoginEmailField;
		String emailAddress = dataReader.getFacebookUsername();
		WebElement passField = regObj.FacebookLoginPasswordField;
		String password = dataReader.getFacebookPassword();
		WebElement loginButton = regObj.FacebookLoginSubmitButton;
		String printMsg = "Facebook";
		LoginAndMyProfileMethods.AllLoginMethods(driver, expectedText, actualText, emailField, emailAddress, passField, password, loginButton,
				printMsg);

	}

	@Test(retryAnalyzer = Retry.class, description = "Login by Twitter")
	public void c_TwitterLogin() throws Exception {
		RegistrationObj regObj = new RegistrationObj(driver);
		loginClick(driver);
		regObj.TwitterLoginButtonOnLoginpage.click();
		Thread.sleep(3000);

		String expectedText = "Authorize Registration - Live to use your account?";
		String actualText = regObj.getTwitterLoginHeadingText();
		WebElement emailField = regObj.TwitterLoginEmailField;
		String emailAddress = dataReader.getTwitterUsername();
		WebElement passField = regObj.TwitterLoginPasswordField;
		String password = dataReader.getTwitterPassword();
		WebElement loginButton = regObj.TwitterLoginSubmitButton;
		String printMsg = "Twitter";
		LoginAndMyProfileMethods.AllLoginMethods(driver, expectedText, actualText, emailField, emailAddress, passField, password, loginButton,
				printMsg);

	}

	@Test(retryAnalyzer = Retry.class, description = "Registration page")
	public void d_Registration() throws Exception {
		RegistrationObj regObj = new RegistrationObj(driver);
		loginClick(driver);

		// Registration heading
		try {
			regObj.NewToRegistrationHeadingIsPresent();
			System.out.println("Registration heading is displayed");
			pass(driver, "Registration heading is displayed");
		} catch (Exception e) {
			System.out.println("Registration heading is ***NOT*** displayed");
			System.out.println(e.getMessage());
			fail(driver, "Registration heading is ***NOT*** displayed");
		}

		//// Registration sub-headings
		for (WebElement RegistrationSubHeading : regObj.RegistrationSubHeadings) {
			String RegSubHeadingText = RegistrationSubHeading.getText();
			try {
				RegistrationSubHeading.isDisplayed();
				System.out.println(RegSubHeadingText + "  is Displayed");
				pass(driver, RegSubHeadingText + "  is Displayed");
			} catch (Exception e) {
				System.out.println(RegSubHeadingText + "  is ***NOT*** Displayed");
				System.out.println(e.getMessage());
				fail(driver, RegSubHeadingText + "  is ***NOT*** Displayed");
			}
		}

		// Click on Join Now
		WaitMethods.WaitUntilElementClickable(driver, regObj.JoinNowButton);
		regObj.JoinNowButton.click();
		Thread.sleep(3000);

		// Fill registration form
		try {
			regObj.RegistrationPageTitleIsPresent();
			System.out.println("Registration Page title is displayed");
			pass(driver, "Registration Page title is displayed");
		} catch (Exception e) {
			System.out.println("Registration Page title is displayed");
			System.out.println(e.getMessage());
			fail(driver, "Registration Page title is displayed");

		}

		// assert if registration labels are displayed
		for (WebElement RegFormLabel : regObj.RegistrationFormLabels) {
			String RegFormLabelText = RegFormLabel.getText();
			try {
				RegFormLabel.isDisplayed();
				System.out.println(RegFormLabelText + " is displayed");
				pass(driver, RegFormLabelText + " is displayed");
			} catch (Exception e) {
				System.out.println(RegFormLabelText + " is ***NOT*** displayed");
				fail(driver, RegFormLabelText + " is ***NOT*** displayed");
			}
		}

		try {
			regObj.RegistrationEmailField.clear();
			regObj.RegistrationEmailField.sendKeys("test@test.com");
			regObj.RegistrationUserNameField.clear();
			regObj.RegistrationUserNameField.sendKeys("TesterMolauto");
			regObj.RegistrationPasswordField.clear();
			regObj.RegistrationPasswordField.sendKeys("tester");
			regObj.RegistrationConfirmPasswordField.clear();
			regObj.RegistrationConfirmPasswordField.sendKeys("tester");
			regObj.RegistrationCityField.clear();
			regObj.RegistrationCityField.sendKeys("London");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", regObj.RegistrationTermsField);
			regObj.RegistrationSubmitButton.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Thread.sleep(2000);
		if (regObj.RegistrationCaptchaVerificationFailIsDisplayed()) {
			System.out.println("Pass for Registration page");
			pass(driver, "Pass for Registration page");
		} else {
			System.out.println("Fail for Registration page");
			pass(driver, "Fail for Registration page");
		}
	}

}
