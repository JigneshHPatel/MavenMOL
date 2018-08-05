/**
 * 
 */
package util;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author jigneshkumarpatel
 * 
 *         Get String value of system OS name
 *
 */
public class SystemOSName extends browsers.BrowserStack {

	public static String OSName = ((RemoteWebDriver) driver).getCapabilities().getPlatform().toString().toLowerCase();

	public static boolean isWindows() {
		return OSName.contains("win");
	}

	public static boolean isMac() {
		return OSName.contains("mac");
	}

}
