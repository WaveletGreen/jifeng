package com.connor.jifeng.plm.jfom008;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.JFomUtil;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class JFomExchangeBomCommond extends AbstractAIFCommand {

	private AbstractAIFUIApplication app;
	private TCSession session;
	private HashMap<String, JFomExchangeBomBean> parentChildMap;

	private String firstParent = "";
	private JFomExchangeBomBean firstBean = new JFomExchangeBomBean();

	// private JFomExchangeBomBean topBean ;

	public JFomExchangeBomCommond(AbstractAIFUIApplication app) {
		this.app = app;
		this.session = (TCSession) app.getSession();
		this.parentChildMap = new HashMap<String, JFomExchangeBomBean>();
		// this.topBean = new JFomExchangeBomBean();
		execCommond();
	}

	public void execCommond() {
		Boolean isOk = true;
		InterfaceAIFComponent comp = this.app.getTargetComponent();
		if (comp instanceof TCComponentItemRevision) {
			isOk = checkWL((TCComponentItemRevision) comp);
			if (!isOk) {
				MessageBox.post("检查图纸BOM中存在没有关联物料的图纸", "Error",
						MessageBox.ERROR);
			} else {
				// TODO 添加具体的逻辑
				// MessageBox.post("111","11",MessageBox.INFORMATION);
				JFomExchangeBomDialog dialog = new JFomExchangeBomDialog(
						this.app, this.session, this.parentChildMap,
						this.firstParent, this.firstBean);
				// setRunnable(dislog);
			}
		} else {
			MessageBox.post("请选择图纸对象版本进行操作", "Error", MessageBox.ERROR);
		}
	}

	public Boolean checkWL(TCComponentItemRevision rev) {
		if (rev == null) {
			return false;
		}
		Boolean isOk = true;

		// try {
		// AIFComponentContext[] aifContext = rev
		// .whereReferencedByTypeRelation(
		// new String[] { JFomUtil.JF3_BOM_DRAWING_WL_BCP,
		// JFomUtil.JF3_BOM_DRAWING_WL_CP,
		// JFomUtil.JF3_BOM_DRAWING_WL_YCL },
		// new String[] { JFomUtil.JF3_BOM_DRAWING_WL_GRM });
		// if (aifContext == null || aifContext.length == 0) {
		// return false;
		// }
		//
		// } catch (TCException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// TODO 添加判断物料的逻辑
		TCComponentBOMLine line = JFomMethodUtil.getTopLineByRev(rev);

		isOk = checkBomLine(line, isOk, true);

		// Set<Entry<String,JFomExchangeBomBean>> set =
		// parentChildMap.entrySet();
		// for(Entry<String,JFomExchangeBomBean> entry:set){
		// MessageBox.post("parent ="+entry.getKey()+" | material count = "+
		// entry.getValue().getMaterialRevs().size()+" | children count = " +
		// entry.getValue().getChildrenUIDs().size() ,"",MessageBox.ERROR);
		// }
		return isOk;

	}

	public Boolean checkBomLine(TCComponentBOMLine line, Boolean isOk,
			Boolean isfirst) {
		try {
			if (isOk == false || line == null) {
				return false;
			}
			TCComponentBOMLine[] packedLines = null;
			// TCComponentBOMLine tempLine = null;
			List<String> packedLineUids = new ArrayList<String>();
			JFomExchangeBomBean bean = new JFomExchangeBomBean();
			if (line.isPacked()) {
				packedLines = line.getPackedLines();
				line.unpack();
				// MessageBox.post("packed uid = " + line.getUid(), "PID", 1);
				for (TCComponentBOMLine l : packedLines) {
					packedLineUids.add(l.getUid());
					// MessageBox.post("unpacked uid = " + l.getUid(), "PID",
					// 1);
					// tempLine = l;
					// pchildrenUID.add(l.getUid());
					// psequenceNoList.add(l.getSequenceNumber());
					// pcountNoList.add(l.getProperty(JFomUtil.JF3_BOM_COUNT_NO));
				}
			}
			List<String> childrenUID = new ArrayList<String>();
			List<String> sequenceNoList = new ArrayList<String>();
			List<String> countNoList = new ArrayList<String>();
			List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
			// 获取对应的ITEMREVISION所关联的物料
			TCComponentItemRevision rev = line.getItemRevision();
			AIFComponentContext[] aifContext = rev
					.whereReferencedByTypeRelation(new String[] {
							JFomUtil.JF3_BOM_DRAWING_WL_BCP,
							JFomUtil.JF3_BOM_DRAWING_WL_CP,
							JFomUtil.JF3_BOM_DRAWING_WL_YCL },
							new String[] { JFomUtil.JF3_BOM_DRAWING_WL_GRM });
			if (aifContext == null || aifContext.length == 0) {
				return false;
			}
			for (AIFComponentContext context : aifContext) {

				revList.add((TCComponentItemRevision) context.getComponent());

			}
			String lineUid = line.getUid();
			// String lineUid = line.getItemRevision().getUid();
			AIFComponentContext[] contexts = new AIFComponentContext[0];
			// 判断BOM是否发部
			// if(!JFomMethodUtil.isRevBomReleased(line.getItemRevision()))
			contexts = line.getChildren();
			for (AIFComponentContext context : contexts) {
				TCComponentBOMLine line2 = (TCComponentBOMLine) context
						.getComponent();
				String sequenceNo = line2.getSequenceNumber();
				String quantityNo = line2
						.getProperty(JFomUtil.JF3_BOM_COUNT_NO);
				if (line2.isPacked()) {
					packedLines = line2.getPackedLines();
					if (packedLines != null)
						for (TCComponentBOMLine tempLine : packedLines) {
							quantityNo = tempLine
									.getProperty(JFomUtil.JF3_BOM_COUNT_NO);
							countNoList.add(quantityNo);
							childrenUID.add(tempLine.getUid());
							sequenceNoList.add(sequenceNo);
						}
				}

				// String childLineUid = line2.getItemRevision().getUid();
				String childLineUid = line2.getUid();
				childrenUID.add(childLineUid);
				sequenceNoList.add(sequenceNo);
				countNoList.add(quantityNo);
				isOk = checkBomLine(line2, isOk, false);
				if (isOk == false) {
					return false;
				}
			}
			bean.setRev(rev);
			bean.setChildrenUIDs(childrenUID);
			bean.setQueryNoList(sequenceNoList);
			bean.setCountList(countNoList);
			bean.setMaterialRevs(revList);
			parentChildMap.put(lineUid, bean);

			for (String pacedeLineUid : packedLineUids) {
				parentChildMap.put(pacedeLineUid, bean);
			}

			// if (packedLines != null) {
			// for (TCComponentBOMLine packedLine : packedLines) {
			// parentChildMap.put(packedLine.getUid(), bean);
			// // MessageBox.post(lineUid + " | " + packedLine.getUid(),
			// // "",
			// // 1);
			// }
			// }
			if (isfirst) {
				this.firstParent = lineUid;
				this.firstBean = bean;
			}
			// for (AIFComponentContext context : contexts) {
			// TCComponentBOMLine line2 = (TCComponentBOMLine)
			// context.getComponent();
			// //String childLineUid = line2.getItemRevision().getUid();
			// //childrenUID.add(childLineUid);
			// isOk = checkBomLine(line2, isOk);
			// if (isOk == false){
			// return false;
			// }
			// }

		} catch (TCException e) {
			e.printStackTrace();
		}

		return isOk;
	}

}
