package com.connor.jifeng.plm.jfom015;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;

public class JF3ExportApqpDocumentReportCommand extends AbstractAIFCommand {

	private AbstractAIFApplication app;

	public JF3ExportApqpDocumentReportCommand(AbstractAIFApplication app) {
		this.app = app;

	}

	@Override
	public void executeModal() throws Exception {

		JF3ExportApqpDocumentReportDialog dialog = new JF3ExportApqpDocumentReportDialog(
				app);
		setRunnable(dialog);

		super.executeModal();
	}

}
