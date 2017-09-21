package com.connor.jifeng.plm.jfom015;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class JF3ExportApqpDocumentReportHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// TODO Auto-generated method stub
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();

		JF3ExportApqpDocumentReportAction action = new JF3ExportApqpDocumentReportAction(
				app, null);
		new Thread(action).start();

		return null;
	}

}
