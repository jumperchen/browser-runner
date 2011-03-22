package browserrunner.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import runjettyrun.utils.ProjectUtil;
import runjettyrun.utils.RunJettyRunLaunchConfigurationUtil;
import browserrunner.BrowserRunnerActivator;
import browserrunner.bootstrap.BrowserRunner;
import browserrunner.bootstrap.BrowserRunnerUrlBuilder;
import browserrunner.preferences.PreferenceConstants;
import browserrunner.util.BrowserUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class BrowserRunnerHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public BrowserRunnerHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		int index = Integer.parseInt(event.getParameter("browserIndex"));
		final String browserPath = (BrowserRunnerActivator.getDefault()
				.getPreferenceStore().getString(PreferenceConstants
				.getBrowserPathKey(index)));

		final String hostName = (BrowserRunnerActivator.getDefault()
				.getPreferenceStore()
				.getString(PreferenceConstants.P_HOST_PATH));

		final IResource target = ProjectUtil.getSelectedResource(window);
		IProject proj = target == null ? null : target.getProject();

		if (proj != null) {
			final ILaunchConfiguration lconf = RunJettyRunLaunchConfigurationUtil
					.findLaunchConfiguration(proj.getName());

			BrowserRunnerUrlBuilder arg = new BrowserRunnerUrlBuilder(hostName, browserPath, lconf,target);
			try {
				if(index == 0 ){ //default external browser
					BrowserUtil.openSystemBrowser(arg.getUrl());
				}else{
					BrowserUtil.openBrowser(arg.getBrowserPath() ,arg.getUrl());
				}
			} catch (IOException e) {
				e.printStackTrace();
				MessageDialog.openError(window.getShell(), "BrowserRunner",
						"Browser startup failed , check the browser file path in Perference! ");
			}

		} else {
			MessageDialog.openError(window.getShell(), "BrowserRunner",
					"We couldn't find any selected project!");
		}

		return null;
	}
}
