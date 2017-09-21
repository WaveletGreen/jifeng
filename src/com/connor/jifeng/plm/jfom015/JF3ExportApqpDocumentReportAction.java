package com.connor.jifeng.plm.jfom015;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class JF3ExportApqpDocumentReportAction extends AbstractAIFAction {
	private AbstractAIFApplication abstractaifapplication;

	public JF3ExportApqpDocumentReportAction(
			AbstractAIFApplication abstractaifapplication, String s) {
		super(abstractaifapplication, s);
		this.abstractaifapplication = abstractaifapplication;
	}

	@Override
	public void run() {
		try {
			JF3ExportApqpDocumentReportCommand command = new JF3ExportApqpDocumentReportCommand(
					this.abstractaifapplication);

			command.executeModal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
