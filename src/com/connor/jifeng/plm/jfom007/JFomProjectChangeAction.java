package com.connor.jifeng.plm.jfom007;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class JFomProjectChangeAction extends AbstractAIFAction {

	private AbstractAIFUIApplication app;
	public JFomProjectChangeAction(AbstractAIFUIApplication abstractaifapplication, String s) {
		super(abstractaifapplication, s);
		this.app = abstractaifapplication;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		JFomProjectChangeDialog dialog = new JFomProjectChangeDialog(app);
		
//		try {
//			JFomProjectChangeCommond commond = new JFomProjectChangeCommond(app);
//			commond.executeModal();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
