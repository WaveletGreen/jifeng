package com.connor.jifeng.plm.jfom008;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class JFomExchangeBomAction extends AbstractAIFAction {

	private AbstractAIFUIApplication app;
	
	public JFomExchangeBomAction(AbstractAIFUIApplication abstractaifapplication, String s) {
		super(abstractaifapplication, s);
		this.app = abstractaifapplication;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			JFomExchangeBomCommond commond = new JFomExchangeBomCommond(app);
			commond.executeModal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
