package com.connor.jifeng.plm.jfom001;

import com.connor.jifeng.plm.util.JFomUtil;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.commands.newitem.NewItemPanel;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class JFomCreateCommond extends AbstractAIFCommand {

	public JFomCreateCommond(AbstractAIFUIApplication app, int commondID) {

		TCSession session = (TCSession) app.getSession();
		switch (commondID) {
		case JFomUtil.BGTZD_ID:
			//TODO 添加变更通知单的逻辑
			//MessageBox.post("添加变更通知单的逻辑","INFO",MessageBox.INFORMATION);
			JFomCreateDialog dialog = new JFomCreateDialog(null, session, true);
			NewItemPanel panel = new NewItemPanel(null, session, dialog);
			//setRunnable(panel);
			break;

		case JFomUtil.GGGJB_ID:
			//TODO 添加更改跟进表的逻辑
			MessageBox.post("添加更改跟进表的逻辑","INFO",MessageBox.INFORMATION);
			break;

		case JFomUtil.WLDCB_ID:
			//TODO 添加物料调查表的逻辑
			MessageBox.post("添加物料调查表的逻辑","INFO",MessageBox.INFORMATION);
			break;
			
		default:
			
			break;

		}

	}
	

}
