package browserrunner.preferences.fieldeditor;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class RunnerFileFieldEditor extends FileFieldEditor {

	public RunnerFileFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}

	protected boolean checkState() {

		return true;
	}
}
