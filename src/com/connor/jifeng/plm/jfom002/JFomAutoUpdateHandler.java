package com.connor.jifeng.plm.jfom002;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class JFomAutoUpdateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		try {
			AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
			JFomAutoUpdateCommond commond = new JFomAutoUpdateCommond(app);
			commond.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
