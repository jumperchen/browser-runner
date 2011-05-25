package browserrunner.integration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class RunJettyRunSupport {
	public static final String LAUNCH_WEBAPPDIR = "run_jetty_run.WEBAPPDIR_ATTR";
	public static final String LAUNCH_PORT = "run_jetty_run.PORT_ATTR";
	public static final String LAUNCH_CONTEXT = "run_jetty_run.CONTEXT_ATTR";

	public static ILaunchConfiguration findLaunchConfiguration(
			String projectName) {
		ILaunchManager lnmanger = DebugPlugin.getDefault().getLaunchManager();
		try {
			for (ILaunchConfiguration lc : lnmanger.getLaunchConfigurations()) {

				if (isSupported(lc, projectName))
					return lc;

			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isSupported(ILaunchConfiguration launch,
			String projectname) throws CoreException {
		String currentProjectName = launch.getAttribute(
				IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		if (!currentProjectName.equals(projectname)) return false;

		if("".equals(launch.getAttribute(RunJettyRunSupport.LAUNCH_CONTEXT,"")))
			return false;

		if("".equals(launch.getAttribute(RunJettyRunSupport.LAUNCH_WEBAPPDIR,"")))
			return false;

		return true;
	}
}
