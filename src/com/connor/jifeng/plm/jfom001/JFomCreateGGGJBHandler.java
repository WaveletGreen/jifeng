package com.connor.jifeng.plm.jfom001;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.teamcenter.rac.aif.AIFDesktop;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.aifrcp.SelectionHelper;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.ui.commands.Messages;
import com.teamcenter.rac.ui.commands.RACUICommandsActivator;
import com.teamcenter.rac.ui.commands.create.bo.NewBOModel;
import com.teamcenter.rac.ui.commands.create.bo.NewBOWizard;
import com.teamcenter.rac.util.SWTUIUtilities;
import com.teamcenter.rac.util.UIUtilities;
import com.teamcenter.rac.util.wizard.extension.BaseExternalWizardDialog;
import com.teamcenter.rac.util.wizard.extension.WizardExtensionHelper;

//public class JFomCreateGGGJBHandler extends AbstractHandler {

public class JFomCreateGGGJBHandler extends AbstractHandler implements
		IExecutableExtension {
	private class CreateNewBOSWTDialog implements Runnable {

		public void run() {
			NewBOWizard newbowizard = (NewBOWizard) getWizard();
			if (newbowizard == null)
				newbowizard = new NewBOWizard(wizardId);
			m_boModel.setSession(session);
			m_boModel.reInitializeTransientData();
			newbowizard.setBOModel(m_boModel);
			newbowizard.setShell(m_shell);
			newbowizard.setParentFrame(AIFUtility.getActiveDesktop());
			newbowizard.setTargetArray(selectedCmps);
			newbowizard.setCurrentSelection(m_currentSelection);
			newbowizard.setWindowTitle(getWizardTitle());
			newbowizard.setRevisionFlag(m_revisionFlag);
			newbowizard.setDefaultType(m_type);
			Shell shell = UIUtilities.getCurrentModalShell();
			dialog = new BaseExternalWizardDialog(m_shell, newbowizard);
			dialog.create();
			newbowizard.retrievePersistedDialogSettings(dialog);
			newbowizard.setWizardDialog(dialog);
			UIUtilities.setCurrentModalShell(dialog.getShell());
			dialog.open();
			dialog = null;
			m_boModel = null;
			UIUtilities.setCurrentModalShell(shell);
		}

		private final Shell m_shell;
		private final boolean m_revisionFlag;
		private final String m_type;
		final JFomCreateGGGJBHandler this$0;

		private CreateNewBOSWTDialog(Shell shell, boolean flag, String s) {
			super();
			this$0 = JFomCreateGGGJBHandler.this;

			m_shell = shell;
			m_revisionFlag = flag;
			m_type = s;

		}

		CreateNewBOSWTDialog(Shell shell, boolean flag, String s,
				CreateNewBOSWTDialog createnewboswtdialog) {
			this(shell, flag, s);
		}
	}

	public JFomCreateGGGJBHandler() {
	}

	public Object execute(ExecutionEvent executionevent)
			throws ExecutionException {
		if (executionevent == null)
			throw new IllegalArgumentException("Event can't be null");
		boolean flag = false;
		if (executionevent.getParameters() != null
				&& executionevent.getParameters().containsKey("selection")) {
			Object obj = executionevent.getParameters().get("selection");
			if (obj instanceof InterfaceAIFComponent[]) {
				selectedCmps = (InterfaceAIFComponent[]) obj;
				m_currentSelection = new StructuredSelection(selectedCmps);
				flag = true;
			}
		}
		if (!flag) {
			m_currentSelection = HandlerUtil
					.getCurrentSelection(executionevent);
			selectedCmps = SelectionHelper
					.getTargetComponents(m_currentSelection);
		}
		m_boModel = getBOModel();
		try {
			session = (TCSession) RACUICommandsActivator.getDefault()
					.getSession();
		} catch (Exception _ex) {
			session = (TCSession) AIFUtility.getDefaultSession();
		}
		launchWizard(executionevent);
		return null;
	}

	public void setInitializationData(
			IConfigurationElement iconfigurationelement, String s, Object obj)
			throws CoreException {
	}

	protected NewBOModel getBOModel() {
		if (m_boModel == null)
			m_boModel = new NewBOModel(this);
		return m_boModel;
	}

	public Wizard getWizard() {
		if (wizardId == null || wizardId.length() == 0)
			wizardId = "com.teamcenter.rac.ui.commands.create.bo.NewBOWizard";
		return WizardExtensionHelper.getWizard(wizardId);
	}

	public String getWizardTitle() {
		return Messages.getString("wizard.TITLE");
	}

	public void launchWizard() {
		launchWizard(null);
	}

	public void launchWizard(ExecutionEvent executionevent) {
		boolean flag = false;
		String s = obType;
		String s11 = pasteRelation;
		if (s11 != null) {
			String as[] = null;
			as = s11.split(",");
			m_boModel.setRelType(as[0]);
			m_boModel.setPreAssignedRelType(as);
		}
		if (executionevent != null && executionevent.getParameters() != null) {
			if (executionevent.getParameters().containsKey("revisionFlag"))
				flag = ((Boolean) executionevent.getParameters().get(
						"revisionFlag")).booleanValue();
			// s = (String) executionevent.getParameters().get("objectType");
			if (executionevent.getParameters().containsKey("pasteRelation")) {
				// String s1 = (String) executionevent.getParameters().get(
				// "pasteRelation");
				String s1 = pasteRelation;
				if (s1 != null) {
					String as[] = null;
					as = s1.split(",");
					m_boModel.setRelType(as[0]);
					m_boModel.setPreAssignedRelType(as);
				}
			}
		}
		AIFDesktop aifdesktop = AIFUtility.getActiveDesktop();
		Shell shell = aifdesktop.getShell();
		if (shell != null)
			SWTUIUtilities.asyncExec(new CreateNewBOSWTDialog(shell, flag, s,
					null));
	}

	protected void readDisplayParameters(NewBOWizard newbowizard,
			WizardDialog wizarddialog) {
		newbowizard.retrievePersistedDialogSettings(wizarddialog);
	}

	protected InterfaceAIFComponent selectedCmps[];
	protected ISelection m_currentSelection;
	protected String wizardId;
	protected WizardDialog dialog;
	protected TCSession session;
	protected NewBOModel m_boModel;
	public String obType = "JF3_GCGGGJB";
	public String pasteRelation = "JF3_GGgenjin";
}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from: F:\Teamcenter10.1
 * .4Env\portal\plugins\com.teamcenter.rac.ui.commands_10000.1.0.jar Total time:
 * 77 ms Jad reported messages/errors: Exit status: 0 Caught exceptions:
 */

// @Override
// public Object execute(ExecutionEvent arg0) throws ExecutionException {
// try {
// // AbstractAIFApplication app = AIFUtility.getCurrentApplication();
// // JFomCreateCommond commond = new JFomCreateCommond(app,
// // JFomUtil.GGGJB_ID);
// // commond.executeModal();
// // NewItemAction action =new
// // NewItemAction(app,AIFDesktop.getActiveDesktop(),
// // "NewItemAction");
// // new Thread(action).start();
// AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
// NewItemCommand commond = new NewItemCommand(
// AIFDesktop.getActiveDesktop(), app, "JF3_GCGGGJB",
// "JF3_GGgenjin", false);
// commond.executeModal();
// NewItemDialog dialog = new NewItemDialog(commond);
//
// } catch (Exception e) {
// e.printStackTrace();
// }
// return null;
// }
// }
