package browserrunner.util;

import java.io.IOException;
import java.net.URL;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import browserrunner.BrowserRunnerActivator;

import runjettyrun.Plugin;

public class BrowserUtil {

	/**
	 *
	 * @param browserPath
	 * @param url
	 * @throws IOException browser path not found.
	 */
	public static void openBrowser(String browserPath,String url) throws IOException{
		// possible IOException for specified resource not found.
		Runtime.getRuntime().exec(browserPath+ " " + url);
	}

	public static IWebBrowser openSystemBrowser(String url) {
		try {
			IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
			IWebBrowser browser = browserSupport.getExternalBrowser();
			browser.openURL(new URL(url));
			return browser;
		} catch (Exception e) {
			return null;
		}
	}

	public static IWebBrowser openEmbedSystemBrowser(String browserid, String url) {
		try {
			IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
			IWebBrowser browser = browserSupport.createBrowser(browserid); //$NON-NLS-1$

			browser.openURL(new URL(url));
			return browser;
		} catch (Exception e) {
			return null;
		}
	}
}
