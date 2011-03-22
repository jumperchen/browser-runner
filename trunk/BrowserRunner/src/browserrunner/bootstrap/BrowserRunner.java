package browserrunner.bootstrap;

import java.io.IOException;

import browserrunner.util.BrowserUtil;

public class BrowserRunner {

	public void runRrowser(String browserpath,String url) throws IOException {
		// possible IOException for specified resource not found.
		Runtime.getRuntime().exec(browserpath+ " " + url);
	}
	public void runRrowser(String url) throws IOException {
		// possible IOException for specified resource not found.
		BrowserUtil.openSystemBrowser(url);
	}


}
