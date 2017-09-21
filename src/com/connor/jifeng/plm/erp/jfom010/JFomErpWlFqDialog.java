package com.connor.jifeng.plm.erp.jfom010;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.connor.jifeng.plm.erp.bean.JFomMaterialBean;
import com.connor.jifeng.plm.erp.ui.util.JFomErpDialog;
import com.connor.jifeng.plm.erp.util.JFomErpMethodUtil;
import com.connor.jifeng.plm.erp.util.JFomErpUtil;
import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.JFomUtil;
import com.connor.jifeng.plm.util.SqlUtil;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class JFomErpWlFqDialog extends JFomErpDialog {

	private List<JFomMaterialBean> partBeanList;

	private List<String> propList;

	private SimpleDateFormat sdf;

	public JFomErpWlFqDialog(String dialogName, AbstractAIFUIApplication app,
			TCSession session) {
		super("失效日期", dialogName, app, session);
		// this.app = app;
		// this.session = session;
		this.partBeanList = new ArrayList<JFomMaterialBean>();
		this.sdf = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM);
		getErpNameAndGG();
		initPropNameList();
	}

	/**
	 * 
	 * @param comp
	 * @return
	 */
	private boolean isObsolete2(TCComponent comp) {
		boolean isd = false;
		try {
			TCComponent[] compList = comp.getTCProperty("release_status_list")
					.getReferenceValueArray();
			if (compList != null && compList.length != 0) {
				for (int i = 0; i < compList.length; i++) {
					TCComponent compS = compList[i];
					String name = compS.getTCProperty("object_name")
							.getStringValue();
					if (name.equals("Obsolete") || name.equals("废弃")) {
						isd = true;
					}
				}
			}
			// else{
			// return true;
			// }
		} catch (TCException e) {
			e.printStackTrace();
		}
		return isd;
	}

	/**
	 * 
	 * @param comp
	 * @return
	 */
	private boolean isObsolete(TCComponent comp) {
		boolean isd = false;
		try {
			TCComponent[] compList = comp.getTCProperty("release_status_list")
					.getReferenceValueArray();
			if (compList != null) {
				for (int i = 0; i < compList.length; i++) {
					TCComponent compS = compList[i];
					String name = compS.getTCProperty("object_name")
							.getStringValue();
					if (name.equals("Obsolete") || name.equals("废弃")) {
						isd = true;
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		return isd;
	}

	private void initPropNameList() {
		this.propList = new ArrayList<String>();
		this.propList.add("item_id");
		this.propList.add("object_name");
		this.propList.add("item_revision_id");
		this.propList.add("owning_user");
		this.propList.add("creation_date");
		// object_name/jf3_YCLClph/jf3_YCLgg/jf3_ys/jf3_wl/jf3_bmcl/jf3_YCLclcj/jf3_xmdh
		// （原材料） 物料名称/材料牌号/规格/颜色/纹理/表面处理/材料厂家/项目代号
		// jf3_xmdh/jf3_CPcpjg/jf3_CPcplx/jf3_CPmlys/jf3_CPmlcz/jf3_CPfxys/jf3_bmcl
		// (成品) 项目代号\产品结构\产品类型\面料颜色\面料材质\缝线颜色\表面处理
		// jf3_xmdh/object_name/jf3_BCPyt/jf3_ys/jf3_wl/jf3_BCPcz/jf3_bmcl
		// (半成品) 项目代号/物料名称/用途/颜色/纹理/材质/表面处理
		// {"jf3_YCLClph","jf3_YCLgg","jf3_YCLclcj","jf3_ys","jf3_wl","jf3_bmcl","jf3_xmdh",
		// "jf3_CPcpjg","jf3_CPcplx","jf3_CPmlys","jf3_CPmlcz","jf3_CPfxys",
		// "jf3_BCPyt",jf3_BCPcz}
	}

	@Override
	public void setMidPanel() {
		// 添加信息中间层的布局
		super.setMidPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(this.okBtn)) {
			okEvent();
			this.dispose();
		} else if (e.getSource().equals(this.celBtn)) {

			this.dispose();
		}
		super.actionPerformed(e);
	}

	// ****************************
	public void okEvent() {
		InterfaceAIFComponent[] comps = this.app.getTargetComponents();
		List<TCComponentItemRevision> revList = JFomMethodUtil
				.getAllRevComp(comps);
		if (revList == null) {
			MessageBox.post("请选择物料版本后传递", "错误", MessageBox.ERROR);
			return;
		}

		if (!checkRevList(revList)) {
			MessageBox.post("请选择物料（成品、半成品、原材料）后传递", "错误", MessageBox.ERROR);
			return;
		}
		if (!checkRevIsLast(revList)) {
			MessageBox.post("请选择物料最新版本进行传递", "错误", MessageBox.ERROR);
			return;
		}

		for (TCComponentItemRevision rev : revList) {
			if (!isObsolete(rev)) {
				MessageBox.post("请选择废弃的物料后传递", "错误", MessageBox.ERROR);
				return;
			}

		}
		getRevMessage(revList);
		transfBeanMessageToDB(this.partBeanList);

	}

	public boolean checkRevIsLast(List<TCComponentItemRevision> revList) {
		boolean isOk = true;
		for (TCComponentItemRevision rev : revList) {
			try {
				TCComponentItemRevision rev2 = rev.getItem()
						.getLatestItemRevision();
				if (!rev2.getUid().equals(rev.getUid())) {
					return false;
				}
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return isOk;
	}

	public boolean checkRevList(List<TCComponentItemRevision> revList) {
		boolean isOk = true;
		if (revList == null || revList.size() == 0) {
			return false;
		}
		TCProperty[][] props = null;
		try {
			props = TCComponentType.getTCPropertiesSet(revList,
					new String[] { "object_type" });

			for (int i = 0; i < props.length; i++) {
				String type = props[i][0].getStringValue();
				if (!(type.equals("JF3_CPRevision")
						|| type.equals("JF3_BCPRevision") || type
							.equals("JF3_YCLRevision"))) {
					return false;
				}
			}

		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return isOk;
	}

	/**
	 * 获取属性
	 * 
	 * @param revList
	 */
	public void getRevMessage(List<TCComponentItemRevision> revList) {
		if (revList == null) {
			MessageBox.post("请选择需要废弃的物料后传递！", "错误", 1);
			return;
		}
		List<TCComponentItem> itemList = new ArrayList<TCComponentItem>();
		List<TCComponentForm> formList = new ArrayList<TCComponentForm>();
		for (TCComponentItemRevision rev : revList) {
			try {
				itemList.add(rev.getItem());
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.partBeanList.clear();
				MessageBox.post("从版本查找对象错误！", "错误", MessageBox.ERROR);
				return;
			}
		}
		Date fqDate = this.discardText.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT);
		String fqDateStr = fqDate == null ? "" : sdf.format(fqDate);

		try {
			TCProperty[][] propss1 = TCComponentType.getTCPropertiesSet(
					revList, new String[] { "IMAN_master_form_rev" });
			for (TCProperty[] propss1_1 : propss1) {
				formList.add((TCComponentForm) (propss1_1[0]
						.getReferenceValueArray())[0]);
			}
			//根据上面的初始化，获取propList中一组属性
			TCProperty[][] propss = TCComponentType.getTCPropertiesSet(revList,
					this.propList.toArray(new String[this.propList.size()]));
			//获取itemList的一组属性
			TCProperty[][] propssItem = TCComponentType.getTCPropertiesSet(
					itemList, new String[] { "uom_tag", "object_type",
							"object_name" });
			// 添加凭借规则后的属性
			TCProperty[][] propssForm = TCComponentType.getTCPropertiesSet(
					formList, this.erp_java_values);
			TCProperty[][] propssForm2 = TCComponentType.getTCPropertiesSet(
					formList, new String[] { "jf3_sfgyj", "jf3_gyjbz" });// 新增加的属性SHARE_USE,SHARE_MEMO
			// 添加拼接规则前的属性
			/*
			 * TCProperty[][] propssForm = TCComponentType.getTCPropertiesSet(
			 * formList, new String[] { "jf3_YCLgg", "jf3_xmdh", "jf3_khljh", //
			 * 2 原材料 "jf3_YCLClph", "jf3_YCLclcj", // 4 成品 "jf3_CPcpjg",
			 * "jf3_CPcplx", "jf3_CPmlys", "jf3_CPmlcz", "jf3_CPfxys", // 9 半成品
			 * "jf3_BCPyt", "jf3_BCPcz", // 11 公用 "jf3_ys", "jf3_wl",
			 * "jf3_bmcl", "jf3_xmdh" });
			 */
			// {"jf3_YCLClph","jf3_YCLgg","jf3_YCLclcj","jf3_ys","jf3_wl","jf3_bmcl","jf3_xmdh",
			// "jf3_CPcpjg","jf3_CPcplx","jf3_CPmlys","jf3_CPmlcz","jf3_CPfxys",
			// "jf3_BCPyt","jf3_BCPcz"}
			// object_name/jf3_YCLClph/jf3_YCLgg/jf3_ys/jf3_wl/jf3_bmcl/jf3_YCLclcj/jf3_xmdh
			// （原材料） 物料名称/材料牌号/规格/颜色/纹理/表面处理/材料厂家/项目代号
			// jf3_xmdh/jf3_CPcpjg/jf3_CPcplx/jf3_CPmlys/jf3_CPmlcz/jf3_CPfxys/jf3_bmcl
			// (成品) 项目代号\产品结构\产品类型\面料颜色\面料材质\缝线颜色\表面处理
			// jf3_xmdh/object_name/jf3_BCPyt/jf3_ys/jf3_wl/jf3_BCPcz/jf3_bmcl
			// (半成品) 项目代号/物料名称/用途/颜色/纹理/材质/表面处理

			TCProperty[] props = null;
			for (int i = 0; i < propss.length; i++) {
				props = propss[i];
				JFomMaterialBean bean = new JFomMaterialBean();
				bean.setPART_NAMBER(props[0].getStringValue());
				// bean.setPART_NAME(props[1].getStringValue());
				bean.setPART_REV(props[2].getStringValue());
				bean.setINVALID_DATE(fqDate);
				bean.setCREATER(((TCComponentUser) props[3].getReferenceValue())
						.getUserId());
				bean.setCREATE_DATE(props[4].getDateValue());
				bean.setUNIT(propssItem[i][0].getDisplayableValue());
				bean.setRELEASED_STATUS(isObsolete(revList.get(i)) ? "Y" : "N");
				// 新增加的属性jf3_sfgyj
				if (propssForm2[i][0] != null
						&& propssForm2[i][0].getStringValue() != null) {
					bean.setSHARE_USE(propssForm2[i][0].getStringValue());

				}
				// 新增加的属性jf3_gyjbz
				if (propssForm2[i][1] != null
						&& propssForm2[i][1].getStringValue() != null) {
					bean.setSHARE_MEMO(propssForm2[i][1].getStringValue());

				}
				if (propssItem[i][1].getStringValue().equals("JF3_YCL")) {
					bean.setPART_NAME(JFomErpMethodUtil.getErpName(
							propssForm[i], this.erp_java_values,
							this.ycl_name_values,
							propssItem[i][2].getStringValue()));
				} else if (propssItem[i][1].getStringValue().equals("JF3_CP")) {
					bean.setPART_NAME(JFomErpMethodUtil.getErpName(
							propssForm[i], this.erp_java_values,
							this.cp_name_values,
							propssItem[i][2].getStringValue()));

				} else if (propssItem[i][1].getStringValue().equals("JF3_BCP")) {
					bean.setPART_NAME(JFomErpMethodUtil.getErpName(
							propssForm[i], this.erp_java_values,
							this.bcp_name_values,
							propssItem[i][2].getStringValue()));
				} else {
					bean.setPART_NAME(props[1].getStringValue());
				}

				if (propssForm[i][0] != null)
					bean.setPART_SPEC(propssForm[i][0].getStringValue());
				else
					bean.setPART_SPEC("");
				if (propssItem[i][1].getStringValue().equals("JF3_CP")) {
					bean.setPART_SPEC(JFomErpMethodUtil.getErpName(
							propssForm[i], this.erp_java_values,
							this.cp_gg_values,
							propssItem[i][2].getStringValue()));
				} else if (propssItem[i][1].getStringValue().equals("JF3_BCP")) {
					bean.setPART_SPEC(JFomErpMethodUtil.getErpName(
							propssForm[i], this.erp_java_values,
							this.bcp_gg_values,
							propssItem[i][2].getStringValue()));
				}
				if (propssForm[i][1] != null)
					bean.setPROJ_CODE(propssForm[i][1].getStringValue());
				else
					bean.setPROJ_CODE("");
				if (propssForm[i][2] != null)
					bean.setCUSTOMER_PART_NUMBER(propssForm[i][2]
							.getStringValue());
				else
					bean.setCUSTOMER_PART_NUMBER("");
				if (propssItem[i][1].getStringValue().endsWith("JF3_CP")) {
					bean.setCLASS_CODE("5");
				} else {
					if (propssItem[i][1].getStringValue().endsWith("JF3_YCL")) {
						if (props[0].getStringValue().startsWith("1.05")) {
							bean.setSOURCE("S");
						} else if (props[0].getStringValue().startsWith("1.06")) {
							bean.setSOURCE("P");
						} else {
							bean.setSOURCE("M");
						}
					}
					String classCode = JFomErpMethodUtil
							.getErpClassCode(props[0].getStringValue());
					if (classCode == null) {
						MessageBox.post("查找不到分群码！", "错误", MessageBox.ERROR);
						this.partBeanList.clear();
						return;
					} else {
						bean.setCLASS_CODE(classCode);
					}
				}
				bean.setSOURCE(formList.get(i).getStringProperty("jf3_wllym"));
				if (bean.getSOURCE() == null
						|| bean.getSOURCE().trim().isEmpty()) {
					if (props[0].getStringValue().startsWith("1.05")) {
						bean.setSOURCE("S");
					} else if (props[0].getStringValue().startsWith("1.06")) {
						bean.setSOURCE("P");
					} else {
						bean.setSOURCE("M");
					}
				}

				this.partBeanList.add(bean);
			}
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 传递到ERP的拼接规则
	 * 
	 * @param names
	 * @return
	 */
	public String erpName(String... names) {
		StringBuffer nameBf = new StringBuffer();
		for (int i = 0; i < names.length; i++) {
			if (names[i] != null && !names[i].trim().isEmpty()) {
				// TODO
			}
			// nameBf.append(b);
		}

		return nameBf.toString();
	}

	public void transfBeanMessageToDB(List<JFomMaterialBean> partBeanList) {
		if (partBeanList == null || partBeanList.size() == 0) {
			return;
		}

		String commiteDateStr = this.sdf.format(new Date());

		String patchIDStr = JFomErpMethodUtil.getPatchIdStr();
		if (patchIDStr == null) {
			MessageBox.post("程序错误请重新执行", "错误", 1);
			return;
		}
		String userID = "";
		try {
			userID = this.session.getUser().getUserId();

		} catch (TCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MessageBox.post("获取用户ID出现异常！", "异常", MessageBox.ERROR);
			return;
		}

		StringBuffer msgSB = new StringBuffer();
		String[] dbPropNames = new String[] { JFomErpUtil.PART_NUMBER,
				JFomErpUtil.PART_NAME,
				JFomErpUtil.PART_REV,
				JFomErpUtil.STATUS,
				JFomErpUtil.BATCH_ID,
				"COMMIT_TIME",
				"ORIGINER", // ----
				"PART_SPEC", "CLASS_CODE", "SOURCE", "PROJ_CODE", "UNIT",
				"RELEASED_STATUS", "INVALID_DATE", "CUSTOMER_PART_NUMBER",
				"IS_STANDARD", "CREATER", "CREATE_DATE", "SHARE_USE",
				"SHARE_MEMO" };
		String insertSQL = SqlUtil.getInsertSql(JFomErpUtil.PART_TABLE_NAME,
				dbPropNames);
		SqlUtil.getConnection();
		boolean isOk = true;
		try {
			for (JFomMaterialBean bean : partBeanList) {
				// ResultSet set = SqlUtil.read(
				// JFomErpUtil.SELECT_PART_SQL,
				// new Object[] { bean.getPART_NAMBER(),
				// bean.getPART_REV() });
				// if (!set.next()) {
				try {
					// 当数据库中存在状态为0的时候更新数据状态为1，并新插入数据
					SqlUtil.update(
							JFomErpUtil.UPDATE_PART_SQL,
							new Object[] { 4, commiteDateStr,
									bean.getPART_NAMBER(), bean.getPART_REV(),
									0 });
					SqlUtil.update(
							JFomErpUtil.UPDATE_PART_SQL,
							new Object[] { 3, commiteDateStr,
									bean.getPART_NAMBER(), bean.getPART_REV(),
									2 });
					SqlUtil.write(
							insertSQL,
							new Object[] { bean.getPART_NAMBER(),
									bean.getPART_NAME(), bean.getPART_REV(), 0,
									patchIDStr, commiteDateStr, userID,
									bean.getPART_SPEC(), bean.getCLASS_CODE(),
									bean.getSOURCE(), bean.getPROJ_CODE(),
									bean.getUNIT(), bean.getRELEASED_STATUS(),
									this.sdf.format(bean.getINVALID_DATE()),
									bean.getCUSTOMER_PART_NUMBER(),
									bean.getIS_STANDARD(), bean.getCREATER(),
									this.sdf.format(bean.getCREATE_DATE()),
									bean.getSHARE_USE(), bean.getSHARE_MEMO() });
					if (SqlUtil.rs != null) {
						msgSB.append("成功插入数据ID=[")
								.append(bean.getPART_NAMBER())
								.append("],REV=[").append(bean.getPART_REV())
								.append("]的数据\r\n");
						// SqlUtil.connection.commit();
					} else {
						msgSB.append("失败插入数据ID=[")
								.append(bean.getPART_NAMBER())
								.append("],REV=[").append(bean.getPART_REV())
								.append("]的数据\r\n");

					}
				} catch (Exception e) {

					e.printStackTrace();
					msgSB.append("失败插入数据ID=[").append(bean.getPART_NAMBER())
							.append("],REV=[").append(bean.getPART_REV())
							.append("]的数据\r\n");
				}
				// }
			}
			MessageBox.post("写入数据到中间表完成:\r\n" + msgSB.toString(), "写入数据完成",
					MessageBox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox.post("写入数据到中间表失败！" + msgSB.toString(), "写入数据失败",
					MessageBox.ERROR);

		} finally {
			try {
				SqlUtil.connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SqlUtil.free();
		}
	}

	public void celEvent() {
		this.dispose();
	}

}
