package com.connor.jifeng.plm.jfom009;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCSession;

public class JFomBomExportCommond extends AbstractAIFCommand {

	private AbstractAIFUIApplication app;
	private TCSession session;

	public JFomBomExportCommond(AbstractAIFUIApplication app) {

		this.app = app;
		this.session = (TCSession) app.getSession();
		execCommond();
	}

	public void execCommond() {

		new Thread() {
			public void run() {
				JFomBomExportDialog dialog = new JFomBomExportDialog(app,
						session);
			};

		}.start();

	}

}
