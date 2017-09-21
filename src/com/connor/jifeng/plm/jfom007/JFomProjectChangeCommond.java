package com.connor.jifeng.plm.jfom007;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCSession;

public class JFomProjectChangeCommond extends AbstractAIFCommand {
	private AbstractAIFUIApplication app;
	private TCSession session;

	public JFomProjectChangeCommond(AbstractAIFUIApplication app) {
		this.app = app;
		this.session = (TCSession) app.getSession();
		execCommond();
	}

	public void execCommond() {
		new Thread() {
			public void run() {
				JFomProjectChangeDialog dialog = new JFomProjectChangeDialog(
						app);
			};
		}.start();

		// setRunnable(dialog);

	}

}
