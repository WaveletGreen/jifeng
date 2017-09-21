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
			//TODO ��ӱ��֪ͨ�����߼�
			//MessageBox.post("��ӱ��֪ͨ�����߼�","INFO",MessageBox.INFORMATION);
			JFomCreateDialog dialog = new JFomCreateDialog(null, session, true);
			NewItemPanel panel = new NewItemPanel(null, session, dialog);
			//setRunnable(panel);
			break;

		case JFomUtil.GGGJB_ID:
			//TODO ��Ӹ��ĸ�������߼�
			MessageBox.post("��Ӹ��ĸ�������߼�","INFO",MessageBox.INFORMATION);
			break;

		case JFomUtil.WLDCB_ID:
			//TODO ������ϵ������߼�
			MessageBox.post("������ϵ������߼�","INFO",MessageBox.INFORMATION);
			break;
			
		default:
			
			break;

		}

	}
	

}
