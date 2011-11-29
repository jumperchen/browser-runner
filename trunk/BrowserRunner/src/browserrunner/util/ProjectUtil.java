package browserrunner.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.FileEditorInput;

public class ProjectUtil {
	public static String MAVEN_NATURE_ID = "org.maven.ide.eclipse.maven2Nature";

	public static IResource getSelectedResource(ISelection selection) {

		if (selection instanceof TreeSelection) {// could be project explorer
			Object first = ((TreeSelection) selection).getFirstElement();

			if (first instanceof IJavaElement){
				return ((IJavaElement) first).getResource();
			}else if (first instanceof IResource)
				return (IResource) first;
			else if (first instanceof IJavaProject)
				return ((IJavaProject) first).getResource();
			else if (first instanceof IProject) {
				try {
					return ((IProject) first).members()[0];
				} catch (CoreException e) {
					e.printStackTrace();
					return null;
				}
			}
		}

		return null;
	}

	public static IResource getSelectedResource(IWorkbenchWindow window) {

		ISelection selection = window.getSelectionService().getSelection();

		IResource ir = getSelectedResource(selection);
		if (ir != null)
			return ir;

		try{
			IEditorInput editorinput = window.getActivePage().getActiveEditor()
					.getEditorInput();
			FileEditorInput fileEditorInput = (FileEditorInput) editorinput
					.getAdapter(FileEditorInput.class);
			if (fileEditorInput == null || fileEditorInput.getFile() == null) {
				return null;
			}
			return fileEditorInput.getFile();
		}catch(NullPointerException ex){
			return null;
		}
	}

	/**
	 *
	 * @param editorinput
	 * @return null if not found
	 */
	public static IFile getFile(IEditorInput editorinput) {
		FileEditorInput fileEditorInput = (FileEditorInput) editorinput
				.getAdapter(FileEditorInput.class);

		if (fileEditorInput == null || fileEditorInput.getFile() == null) {
			return null;
		}
		return fileEditorInput.getFile();
	}

	public static IProject getProject(IWorkbenchWindow window) {
		IEditorInput editorinput = window.getActivePage().getActiveEditor()
				.getEditorInput();
		FileEditorInput fileEditorInput = (FileEditorInput) editorinput
				.getAdapter(FileEditorInput.class);

		if (fileEditorInput == null || fileEditorInput.getFile() == null) {
			return null;
		}
		return fileEditorInput.getFile().getProject();
	}

	public static IProject getProject(IEditorInput editorinput) {
		FileEditorInput fileEditorInput = (FileEditorInput) editorinput
				.getAdapter(FileEditorInput.class);

		if (fileEditorInput == null || fileEditorInput.getFile() == null) {
			return null;
		}
		return fileEditorInput.getFile().getProject();
	}

}
