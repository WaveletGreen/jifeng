package com.connor.jifeng.plm.jfom002;

import java.util.ArrayList;
import java.util.List;

import com.connor.jifeng.plm.util.JFomUtil;
import com.connor.jifeng.plm.util.TransfProgressBarThread;
import com.sun.mail.handlers.message_rfc822;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.Registry;

public class JFomAutoUpdateCommond extends AbstractAIFCommand {
	private Registry registry = Registry.getRegistry(this);

	public JFomAutoUpdateCommond(AbstractAIFUIApplication app) {
		TransfProgressBarThread bar = new TransfProgressBarThread("�޶��汾", "�޶��汾��......");
		try {
			
			InterfaceAIFComponent comp = app.getTargetComponent();
			String objectType = comp.getType();
			System.out.println("target type = " + objectType);
			if (objectType != null && objectType.equals(registry.getString("autoUpdateCompType"))) {
				TCComponentItemRevision rev = (TCComponentItemRevision) comp;
				TCComponent[] bgqComps = rev.getReferenceListProperty(JFomUtil.JF3_BGQduixiang);
				bar.start();
				updateComps(rev, selectItemRevisionComps(bgqComps));
				bar.stopBar();
				MessageBox.post("�޶��汾����","Info",MessageBox.INFORMATION);
			} else {
				MessageBox.post("��ѡ��Ķ�����ȷ����ѡ��[" + registry.getString("autoUpdateCompType") + "]����", "Error",
						MessageBox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bar.stopBar();
			MessageBox.post("�޶��汾����������ִ��","Error",MessageBox.ERROR);
		}
	}

	/**
	 * �ӱ��ǰ������ɸѡ���㲿���汾����
	 * 
	 * @param comps
	 *            ���ǰ����
	 * @return �㲿���汾
	 */
	public List<TCComponentItemRevision> selectItemRevisionComps(TCComponent[] comps) {
		List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
		for (TCComponent comp : comps) {
			if (comp instanceof TCComponentItemRevision) {
				revList.add((TCComponentItemRevision) comp);
			}
		}
		return revList;
	}

	/**
	 * �����ǰ�����������ӵ�������ϵ��
	 * 
	 * @param rev
	 *            Ŀ�����
	 * @param revComps
	 *            ���ǰ����
	 * @throws TCException
	 */
	public void updateComps(TCComponentItemRevision rev, List<TCComponentItemRevision> revCompList) throws TCException {
		if (revCompList.size() == 0) {
			System.out.println("���ǰ�Ķ�����0��");
			return;
		}
		TCComponent[] comps = rev.getReferenceListProperty(JFomUtil.JF3_BGHduixiang);
		List<TCComponent> compList = new ArrayList<TCComponent>();
		List<String> hidList = new ArrayList<String>();
		if (comps != null) {
			for (TCComponent comp : comps) {
				compList.add(comp);
			}
		}
		String[][] itemHIds = TCComponentType.getPropertiesSet(compList, new String[] { "item_id" });
		String[][] itemQIds = TCComponentType.getPropertiesSet(revCompList, new String[] { "item_id" });
		if (itemHIds != null)
			for (int i = 0; i < itemHIds.length; i++) {
				hidList.add(itemHIds[i][0]);
			}
		for (int i = 0; i < itemQIds.length; i++) {
			if (itemQIds[i][0] != null && !itemQIds[i][0].isEmpty() && !hidList.contains(itemQIds[i][0])) {
				// TODO ���������в�����ǰ����
				TCComponentItemRevision revTemp = revCompList.get(i);
				TCComponentItemRevision revTemp2 = revTemp.saveAs("");
				revTemp2.save();
				rev.add(JFomUtil.JF3_BGHduixiang, revTemp2);
			} else {
				// ���������а���ǰ���󣬲�������
				System.out.println("���������а���ǰ���󣬲�������");
			}
		}
	}

}
