package com.connor.jifeng.plm.jfom009;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class JFomBomExportAction extends AbstractAIFAction {
	private AbstractAIFUIApplication app;
	
	public JFomBomExportAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		this.app = arg0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			JFomBomExportCommond commond = new JFomBomExportCommond(this.app);
			commond.executeModal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
