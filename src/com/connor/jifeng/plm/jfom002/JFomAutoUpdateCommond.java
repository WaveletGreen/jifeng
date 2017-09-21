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
		TransfProgressBarThread bar = new TransfProgressBarThread("修订版本", "修订版本中......");
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
				MessageBox.post("修订版本结束","Info",MessageBox.INFORMATION);
			} else {
				MessageBox.post("所选择的对象不正确，请选择[" + registry.getString("autoUpdateCompType") + "]对象", "Error",
						MessageBox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bar.stopBar();
			MessageBox.post("修订版本出错，请重新执行","Error",MessageBox.ERROR);
		}
	}

	/**
	 * 从变更前对象中筛选出零部件版本对象
	 * 
	 * @param comps
	 *            变更前对象
	 * @return 零部件版本
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
	 * 将变更前对象升版后添加到升版后关系中
	 * 
	 * @param rev
	 *            目标对象
	 * @param revComps
	 *            变更前对象
	 * @throws TCException
	 */
	public void updateComps(TCComponentItemRevision rev, List<TCComponentItemRevision> revCompList) throws TCException {
		if (revCompList.size() == 0) {
			System.out.println("变更前的对象有0个");
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
				// TODO 变更后对象中不包含前对象
				TCComponentItemRevision revTemp = revCompList.get(i);
				TCComponentItemRevision revTemp2 = revTemp.saveAs("");
				revTemp2.save();
				rev.add(JFomUtil.JF3_BGHduixiang, revTemp2);
			} else {
				// 变更后对象中包含前对象，不做处理
				System.out.println("变更后对象中包含前对象，不做处理");
			}
		}
	}

}
