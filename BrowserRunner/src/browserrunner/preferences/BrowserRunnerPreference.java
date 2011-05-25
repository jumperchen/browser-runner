package browserrunner.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import browserrunner.BrowserRunnerActivator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class BrowserRunnerPreference
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public BrowserRunnerPreference() {
		super(GRID);
		setPreferenceStore(BrowserRunnerActivator.getDefault().getPreferenceStore());
		setDescription("A plug-in provides user to run external browser with Run-Jetty-Run Server.");
	}
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new StringFieldEditor(PreferenceConstants.P_HOST_PATH, "&Host Name", getFieldEditorParent()));

		addField(
				new BooleanFieldEditor(PreferenceConstants.P_START_RJR,
						"&Start up Run-Jetty-Run when the port not using."+
						"(Need some time ,please wait and then reload browser.)",
						getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {

	}
}