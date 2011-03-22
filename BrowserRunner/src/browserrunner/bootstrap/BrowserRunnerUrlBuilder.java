package browserrunner.bootstrap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;

import runjettyrun.Plugin;

public class BrowserRunnerUrlBuilder {

	private String webapp;

	private String context;

	private int port;

	private String browserPath ;

	private String hostname;

	private IResource target;

	/**
	 * accept RJR LaunchConfiguration
	 *
	 * @param rjrLaunchConfig
	 */
	public BrowserRunnerUrlBuilder(String browserPath,ILaunchConfiguration rjrLaunchConfig,IResource resource) {
		this("localhost",browserPath,rjrLaunchConfig,resource);
	}
	public BrowserRunnerUrlBuilder(String hostname,String browserPath,ILaunchConfiguration rjrLaunchConfig,IResource resource) {

		try {
			if(hostname == null || "".equals(hostname.trim()) )
				this.hostname = "localhost";
			else
				this.hostname = hostname;
			this.browserPath = browserPath;
			this.webapp = rjrLaunchConfig.getAttribute(Plugin.ATTR_WEBAPPDIR, "");
			this.context = rjrLaunchConfig.getAttribute(Plugin.ATTR_CONTEXT, "");
			this.port = Integer.parseInt(rjrLaunchConfig.getAttribute(Plugin.ATTR_PORT, "8080"));

			this.target = resource;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getBrowserPath() {
		return browserPath;
	}

	public void setBrowserPath(String browserPath) {
		this.browserPath = browserPath;
	}

	public BrowserRunnerUrlBuilder(String browserPath,String webapp, String context, int port) {
		this.browserPath = browserPath;
		this.webapp = webapp;
		this.context = context;
		this.port = port;
	}

	public String getWebapp() {
		return webapp;
	}

	public void setWebapp(String webapp) {
		this.webapp = webapp;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public String getUrl(){

		IPath targetPath = null;

		if (target == null) {
			targetPath = new Path("");
		} else {
			IPath relativePath = target.getProjectRelativePath();
			IPath webappPath = new Path(getWebapp());
			targetPath = relativePath.makeRelativeTo(webappPath);
		}

		String context = getContext();
		int port = getPort();

		String url = null;

		// TonyQ 2011/1/9
		// for case when no any context, ex. http://localhost/hello.zul
		if ("/".equals(context))
			context = "";

		if (port == 80)
			url = "http://" + getHostname() + "" + context + "/"
					+ targetPath.toString();
		else
			url = "http://" + getHostname() + ":" + port + context + "/"
					+ targetPath.toString();

		return url;
	}
}
