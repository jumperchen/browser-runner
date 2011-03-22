package browserrunner.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import browserrunner.BrowserRunnerActivator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = BrowserRunnerActivator.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.P_BROWSER_PATH +"0" , "default system browser ");
		store.setDefault(PreferenceConstants.P_BROWSER_PATH +"1" , "\"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe\"");
		store.setDefault(PreferenceConstants.P_HOST_PATH , "localhost");
	}

}
