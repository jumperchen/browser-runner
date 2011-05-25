package browserrunner.preferences;

import browserrunner.BrowserRunnerActivator;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	public static final String P_BROWSER_PATH = "browserPathPreference";
	public static final String P_HOST_PATH = "hostPathPreference";

	public static final String P_START_RJR = "startRunJetyRunPreference";
	public static final String P_HOST_FAILBACK = "hostFailbackJetyRunPreference";

	/**
	 * get browser path key
	 * @param index start at 0
	 * @return
	 */
	public static final String getBrowserPathKey(int index) {

		if (index >= 0 && index < BrowserRunnerActivator.BROUWSER_COUNT)
			return P_BROWSER_PATH + "" + index;
		else
			return null;
	}
	// public static final String P_BOOLEAN = "booleanPreference";
	//
	// public static final String P_CHOICE = "choicePreference";
	//
	// public static final String P_STRING = "stringPreference";

}
