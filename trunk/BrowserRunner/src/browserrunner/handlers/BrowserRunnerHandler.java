package browserrunner.handlers;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import browserrunner.BrowserRunnerActivator;
import browserrunner.bootstrap.BrowserRunnerUrlBuilder;
import browserrunner.integration.RunJettyRunSupport;
import browserrunner.preferences.PreferenceConstants;
import browserrunner.util.BrowserUtil;
import browserrunner.util.ProjectUtil;

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

		final boolean startRJR = (BrowserRunnerActivator.getDefault()
				.getPreferenceStore()
				.getBoolean(PreferenceConstants.P_START_RJR));

		final boolean hostFailback = (BrowserRunnerActivator.getDefault()
				.getPreferenceStore()
				.getBoolean(PreferenceConstants.P_HOST_FAILBACK));

		final IResource target = ProjectUtil.getSelectedResource(window);
		IProject proj = target == null ? null : target.getProject();

		if (proj != null) {
			final ILaunchConfiguration lconf = RunJettyRunSupport
					.findLaunchConfiguration(proj.getName());

			try {
				try{
					int port = Integer.parseInt(lconf.getAttribute(RunJettyRunSupport.LAUNCH_PORT,"-1"));
					if(startRJR && isAvailablePort(port)){
						lconf.launch(ILaunchManager.DEBUG_MODE, null);
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
				}catch(IllegalArgumentException e){
					//wrong port , just ignore it.
				} catch (CoreException e) {
					//Need not to start if meet any error,we just ignore it.
					e.printStackTrace();
				}

				BrowserRunnerUrlBuilder arg = new BrowserRunnerUrlBuilder(
						hostName, browserPath, lconf, target , hostFailback);
				if (index == 0) { // default external browser
					BrowserUtil.openSystemBrowser(arg.getUrl());
				} else {
					BrowserUtil.openBrowser(arg.getBrowserPath(), arg.getUrl());
				}
			} catch (IllegalArgumentException ex) {
				MessageDialog
				.openError(window.getShell(), "BrowserRunner",
						"Browser startup failed , seems it got wrong configurations. ");
			} catch (IOException e) {
				e.printStackTrace();
				MessageDialog
						.openError(window.getShell(), "BrowserRunner",
								"Browser startup failed , check the browser file path in Perference! ");
			}

		} else {
			MessageDialog.openError(window.getShell(), "BrowserRunner",
					"We couldn't find any selected project!");
		}

		return null;
	}

	private static boolean isAvailablePort(int port) {
		if (port <= 0) {
			throw new IllegalArgumentException("Invalid start port: " + port);
		}

		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}

}
