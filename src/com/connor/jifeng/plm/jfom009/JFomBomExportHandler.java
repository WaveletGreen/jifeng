package com.connor.jifeng.plm.jfom009;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class JFomBomExportHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {

		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		
		JFomBomExportAction action = new JFomBomExportAction(app, null);
		
		new Thread(action).start();
		
		
		return null;
	}

}
