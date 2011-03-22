package browserrunner.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import browserrunner.BrowserRunnerActivator;
import browserrunner.preferences.fieldeditor.RunnerFileFieldEditor;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class BrowserRunnerBrowsersPreference extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public BrowserRunnerBrowsersPreference() {
		super(GRID);
		setPreferenceStore(BrowserRunnerActivator.getDefault()
				.getPreferenceStore());
		setDescription("Setting up browser runner.\n ( you can add browser argument after the path if need.)");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		RunnerFileFieldEditor first = new RunnerFileFieldEditor(
				PreferenceConstants.getBrowserPathKey(0),
				"&Choose browser 1:", getFieldEditorParent());

		first.setEnabled(false, getFieldEditorParent());
		addField(first);


		for (int i = 1; i < BrowserRunnerActivator.BROUWSER_COUNT; ++i) {
			addField(new RunnerFileFieldEditor(
					PreferenceConstants.getBrowserPathKey(i),
					"&Choose browser " + (i + 1) + ":", getFieldEditorParent()));
		}

	}

	@Override
	protected void performApply() {
		super.performApply();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}