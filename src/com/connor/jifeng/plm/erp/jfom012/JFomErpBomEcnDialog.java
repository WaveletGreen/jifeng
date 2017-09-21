package com.connor.jifeng.plm.erp.jfom012;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.connor.jifeng.plm.erp.bean.JFomBomBean;
import com.connor.jifeng.plm.erp.bean.JFomMaterialBean;
import com.connor.jifeng.plm.erp.ui.util.JFomErpDialog;
import com.connor.jifeng.plm.erp.util.JFomErpMethodUtil;
import com.connor.jifeng.plm.erp.util.JFomErpUtil;
import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.JFomUtil;
import com.connor.jifeng.plm.util.SqlUtil;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.DateButton;
import com.teamcenter.rac.util.MessageBox;

public class JFomErpBomEcnDialog extends JFomErpDialog {

	private JLabel ecnNo;
	private JTextField ecnText;
	private String[] bomLinePropName;
	private SimpleDateFormat sdf;
	private Date vDate;
	private String vDateStr;
	private List<TCComponentItemRevision> revList;
	private List<JFomMaterialBean> partBeanList;
	private DateButton overdiscardText;

	private List<String> propList;

	public JFomErpBomEcnDialog(String dialogName, AbstractAIFUIApplication app,
			TCSession session) {
		super("生效日期", dialogName, app, session);
		bomLinePropName = new String[] { "bl_item_item_id", "bl_quantity",
				"bl_sequence_no", "JF3_gdkl" };
		revList = new ArrayList<TCComponentItemRevision>();
		sdf = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM);
		partBeanList = new ArrayList<JFomMaterialBean>();
		// propList = new ArrayList<String>();
		initPropNameList();
	}

	private void initPropNameList() {
		this.propList = new ArrayList<String>();
		this.propList.add("item_id");
		this.propList.add("object_name");
		this.propList.add("item_revision_id");
		this.propList.add("owning_user");
		this.propList.add("creation_date");
	}

	/**
	 * 获取BOM中的物料属性
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
			TCProperty[][] propss = TCComponentType.getTCPropertiesSet(revList,
					this.propList.toArray(new String[this.propList.size()]));
			TCProperty[][] propssItem = TCComponentType.getTCPropertiesSet(
					itemList, new String[] { "uom_tag", "object_type",
							"object_name" });
			// 添加凭借规则后的属性
			TCProperty[][] propssForm = TCComponentType.getTCPropertiesSet(
					formList, this.erp_java_values);
			// 新增加的属性SHARE_USE,SHARE_MEMO
			TCProperty[][] propssForm2 = TCComponentType.getTCPropertiesSet(
					formList, new String[] { "jf3_sfgyj", "jf3_gyjbz" });

			/*
			 * TCProperty[][] propssForm = TCComponentType.getTCPropertiesSet(
			 * formList, new String[] { "jf3_YCLgg", "jf3_xmdh", "jf3_khljh", //
			 * 2 原材料 "jf3_YCLClph", "jf3_YCLclcj", // 4 成品 "jf3_CPcpjg",
			 * "jf3_CPcplx", "jf3_CPmlys", "jf3_CPmlcz", "jf3_CPfxys", // 9 半成品
			 * "jf3_BCPyt", "jf3_BCPcz", // 11 公用 "jf3_ys", "jf3_wl", "jf3_bmcl"
			 * });
			 */
			TCProperty[] props = null;
			for (int i = 0; i < propss.length; i++) {
				props = propss[i];
				JFomMaterialBean bean = new JFomMaterialBean();
				bean.setPART_NAMBER(props[0].getStringValue());
				// bean.setPART_NAME(props[1].getStringValue());
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
				bean.setPART_REV(props[2].getStringValue());
				bean.setINVALID_DATE(fqDate);
				bean.setCREATER(((TCComponentUser) props[3].getReferenceValue())
						.getUserId());
				bean.setCREATE_DATE(props[4].getDateValue());
				bean.setUNIT(propssItem[i][0].getDisplayableValue());
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

	private String patchIDStr = null;

	/**
	 * 传递数据到数据库
	 * 
	 * @param partBeanList
	 */
	public void transfBeanMessageToDB(List<JFomMaterialBean> partBeanList) {
		if (partBeanList == null || partBeanList.size() == 0) {
			return;
		}

		String commiteDateStr = this.sdf.format(new Date());

		patchIDStr = JFomErpMethodUtil.getPatchIdStr();
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
				JFomErpUtil.PART_NAME, JFomErpUtil.PART_REV,
				JFomErpUtil.STATUS,
				JFomErpUtil.BATCH_ID,
				"COMMIT_TIME",
				"ORIGINER", // ----
				"PART_SPEC", "CLASS_CODE", "SOURCE", "PROJ_CODE", "UNIT",
				"RELEASED_STATUS", "CUSTOMER_PART_NUMBER", "IS_STANDARD",
				"CREATER", "CREATE_DATE", "SHARE_USE", "SHARE_MEMO" };
		String insertSQL = SqlUtil.getInsertSql(JFomErpUtil.PART_TABLE_NAME,
				dbPropNames);
		SqlUtil.getConnection();
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

									bean.getCUSTOMER_PART_NUMBER(),
									bean.getIS_STANDARD(), bean.getCREATER(),
									this.sdf.format(bean.getCREATE_DATE()),
									bean.getSHARE_USE(), bean.getSHARE_MEMO() });
					if (SqlUtil.rs != null)
						msgSB.append("成功插入数据ID=[")
								.append(bean.getPART_NAMBER())
								.append("],REV=[").append(bean.getPART_REV())
								.append("]的数据\r\n");
					else
						msgSB.append("失败插入数据ID=[")
								.append(bean.getPART_NAMBER())
								.append("],REV=[").append(bean.getPART_REV())
								.append("]的数据\r\n");
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

	@Override
	public void setMidPanel() {
		// TODO Auto-generated method stub
		this.ecnNo = new JLabel("变更单号");
		this.ecnText = new JTextField(24);
		SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT);
		this.overdiscardText = new DateButton(sdf);
		this.midPanel.add("4.1.right.top.preferred.preferred", ecnNo);
		this.midPanel.add("4.2.left.top.preferred.preferred", ecnText);
		this.midPanel.add("3.1.right.top.preferred.preferred", new JLabel(
				"失效日期"));
		this.midPanel.add("3.2.left.top.preferred.preferred", overdiscardText);
		super.setMidPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.okBtn)) {
			okEvent();
		} else if (e.getSource().equals(this.celBtn)) {
			this.dispose();
		}
		super.actionPerformed(e);
	}

	/**
	 * 执行确定操作的事件
	 */
	public void okEvent() {

		String ecnNo = this.ecnText.getText();

		if (ecnNo == null || ecnNo.trim().isEmpty()) {
			MessageBox.post("请输入正确的ECN编号！", "错误", MessageBox.ERROR);
			return;
		}

		InterfaceAIFComponent[] interfaceComps = JFomMethodUtil
				.searchComponentsCollection(session,
						JFomErpUtil.ECN_QUERY_NAME, new String[] {
								JFomErpUtil.ECN_QUERY_TYPE,
								JFomErpUtil.ECN_QUERY_ID }, new String[] {
								JFomErpUtil.ECN_TYPE, ecnNo });

		if (interfaceComps == null || interfaceComps.length != 1) {
			MessageBox.post("输入的设变单号不存在！", "错误", MessageBox.ERROR);
			return;
		}

		vDate = this.discardText.getDate();
		if (vDate == null) {
			MessageBox.post("生效日期不能为空！", "错误", MessageBox.ERROR);
			return;
		}
		vDateStr = this.sdf.format(vDate);
		InterfaceAIFComponent[] targetComps = this.app.getTargetComponents();

		List<TCComponentItemRevision> revList = JFomMethodUtil
				.getAllRevComp(targetComps);
		// List<TCComponentItemRevision> revList = exchangeItemRev(targetComps);
		if (revList == null) {
			MessageBox.post("请选择物料版本后传递", "错误", MessageBox.ERROR);
			return;
		}
		List<JFomBomBean> beanList = getBomBeanList(revList);
		if (beanList == null || beanList.size() == 0) {
			MessageBox.post("所需要传入的数据数量为0，请重新选择需要传递的数据！", "错误",
					MessageBox.ERROR);
			return;
		}
		// 属性获取完毕，传入到数据库中。
		// MessageBox.post("传递数据", "错误", MessageBox.ERROR);
		if (!checkRevList(this.revList)) {
			MessageBox.post("请选择物料BOM后传递", "错误", MessageBox.ERROR);
			return;
		}
		getRevMessage(this.revList);
		transfBeanMessageToDB(this.partBeanList);
		sedBomListToDB(beanList, ecnNo);

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
	 * 
	 * @param beanList
	 */
	public void sedBomListToDB(List<JFomBomBean> beanList, String ECNNo) {
		String patchID = patchIDStr;// JFomErpMethodUtil.getPatchIdStr();
		String commiteDate = this.sdf.format(new Date());
		String userID = "";
		try {
			userID = this.session.getUser().getUserId();

		} catch (TCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MessageBox.post("获取用户ID出现异常！", "异常", MessageBox.ERROR);
			return;
		}
		StringBuffer logSb = new StringBuffer();
		SqlUtil.getConnection();
		for (int i = 0; i < beanList.size(); i++) {
			JFomBomBean bean = beanList.get(i);
			try {

				SqlUtil.update(JFomErpUtil.SQL_BOM_UPDATE, new Object[] { 4,
						commiteDate, bean.getP_NUMBER(), 0, patchID });
				SqlUtil.update(JFomErpUtil.SQL_BOM_UPDATE, new Object[] { 3,
						commiteDate, bean.getP_NUMBER(), 2, patchID });
				SqlUtil.write(
						JFomErpUtil.SQL_BOM_INSERT,
						new Object[] {
								patchID,
								userID,
								commiteDate,
								0,
								bean.getP_NUMBER(),
								bean.getSEQ_NUMBER(),
								bean.getPART_NUMBER(),
								bean.getQUANTITY(),
								bean.getASSEM_QUANTITY(),
								bean.getOTHERS(),
								bean.getULLAGE(),
								bean.getJOB_NUMBER(),
								bean.getREPLACE_TYPE(),
								bean.getVALID_DATE(),
								bean.getVALID_DATE(),
								this.overdiscardText.getDate() == null ? ""
										: this.sdf.format(this.overdiscardText
												.getDate()), bean.getJF3_GDKL() });
				// SqlUtil.write("insert into bom_table (p_number) values (123)");
				// MessageBox.post(
				// "==>" + bean.getP_NUMBER() + "|"
				// + bean.getPART_NUMBER() + "|"
				// + bean.getQUANTITY(), "异常", MessageBox.ERROR);
				if (SqlUtil.rs != null)
					logSb.append("传入BOM成功 :").append("P_NUMBER =")
							.append(bean.getP_NUMBER())
							.append(" | PART_NUMBER =")
							.append(bean.getPART_NUMBER()).append("\r\n");
				else
					logSb.append("传入BOM失败  :").append("P_NUMBER =")
							.append(bean.getP_NUMBER())
							.append(" | PART_NUMBER =")
							.append(bean.getPART_NUMBER()).append("\r\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// MessageBox.post("BOM变更信息传入到中间表的时候出现异常", "异常",
				// MessageBox.ERROR);
				logSb.append("传入BOM失败  :").append("P_NUMBER =")
						.append(bean.getP_NUMBER()).append(" | PART_NUMBER =")
						.append(bean.getPART_NUMBER()).append("\r\n");
			}
		}

		try {
			SqlUtil.update(JFomErpUtil.SQL_ECN_UPDATE, new Object[] { 4,
					this.ecnText.getText(), 0 });
			SqlUtil.update(JFomErpUtil.SQL_ECN_UPDATE, new Object[] { 3,
					this.ecnText.getText(), 2 });
			SqlUtil.write(JFomErpUtil.SQL_ECN_WRITE, new Object[] {
					this.ecnText.getText(), "", patchID, 0, this.vDateStr });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.disposeDialog();
		try {
			SqlUtil.connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlUtil.free();
		MessageBox.post("写入数据到中间表完成:\r\n" + logSb.toString(), "写入数据完成",
				MessageBox.INFORMATION);

	}

	/**
	 * 遍历BOM获取BOM结构和属性
	 * 
	 * @param topLine
	 * @param parentId
	 * @param beanList
	 */
	public void getBomBeanMsg(TCComponentBOMLine topLine, String parentId,
			List<JFomBomBean> beanList) {

		try {
			AIFComponentContext[] contexts = topLine.getChildren();
			this.revList.add(topLine.getItemRevision());
			if (contexts == null || contexts.length == 0) {
				// beanList = null;
				return;
			}

			List<TCComponentBOMLine> bomlineList = new ArrayList<>();
			// 添加获取bombean的逻辑
			for (AIFComponentContext context : contexts) {
				bomlineList.add((TCComponentBOMLine) context.getComponent());

			}
			if (bomlineList.size() != 0) {
				TCProperty[][] properties = TCComponentType.getTCPropertiesSet(
						bomlineList, this.bomLinePropName);
				String quantity = "1";
				int sequenceNo = 0;
				for (int i = 0; i < properties.length; i++) {
					TCProperty[] property = properties[i];
					getBomBeanMsg(bomlineList.get(i),
							property[0].getStringValue(), beanList);
					quantity = "1";
					sequenceNo = 0;
					JFomBomBean bomBean = new JFomBomBean();
					bomBean.setP_NUMBER(parentId);
					bomBean.setPART_NUMBER(property[0].getStringValue());
					if (!property[1].getStringValue().isEmpty()) {
						quantity = property[1].getStringValue();
					}
					if (!property[2].getStringValue().isEmpty()) {
						sequenceNo = Integer.parseInt(property[2]
								.getStringValue());
					}
					if (property[3] != null
							&& property[3].getStringValue() != null) {
						bomBean.setJF3_GDKL(property[3].getStringValue());
					}
					if (quantity != null && quantity.equals("0"))
						quantity = "1";
					bomBean.setQUANTITY(quantity);
					bomBean.setSEQ_NUMBER(sequenceNo);
					bomBean.setVALID_DATE(this.vDateStr);
					beanList.add(bomBean);
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取选中的各个版本的bom结构信息
	 * 
	 * @param revList
	 * @return
	 */
	public List<JFomBomBean> getBomBeanList(
			List<TCComponentItemRevision> revList) {
		// getTopLineByRev
		if (revList == null || revList.size() == 0) {
			MessageBox.post("请选择需要传递的零部件版本后再执行此操作！", "错误", MessageBox.ERROR);
			return null;
		}
		List<JFomBomBean> bomBeanList = new ArrayList<JFomBomBean>();
		TCProperty[][] properties = null;
		try {
			properties = TCComponentType.getTCPropertiesSet(revList,
					new String[] { "item_id" });
		} catch (TCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (properties == null) {
			return null;
		}
		List<TCComponentBOMLine> bomLineList = new ArrayList<TCComponentBOMLine>();
		//
		for (int i = 0; i < revList.size(); i++) {
			TCComponentBOMLine topLine = JFomMethodUtil.getTopLineByRev(revList
					.get(i));
			if (topLine == null) {
				MessageBox.post("请选择BVR已经发布的零组件版本！", "错误", MessageBox.ERROR);
				return null;
			}
			bomLineList.add(topLine);
			try {
				AIFComponentContext[] context = topLine.getChildren();
				if (context == null || context.length == 0) {
					MessageBox.post("请选择带有BOM结构的零组件版本后操作！", "错误",
							MessageBox.ERROR);
					return null;
				}
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageBox.post("获取BOM结构出现异常请重新执行！", "错误", MessageBox.ERROR);
				return null;
			}
		}
		// 添加获取bean的逻辑
		for (int i = 0; i < bomLineList.size(); i++) {
			// 添加获取bombean的逻辑
			// properties[i][0].getStringValue();
			getBomBeanMsg(bomLineList.get(i),
					properties[i][0].getStringValue(), bomBeanList);
		}

		return bomBeanList;
	}

	/**
	 * 将选择的对象转换为版本对象
	 * 
	 * @param targetComps
	 * @return
	 */
	public List<TCComponentItemRevision> exchangeItemRev(
			InterfaceAIFComponent[] targetComps) {
		List<TCComponentItemRevision> compRevList = new ArrayList<TCComponentItemRevision>();
		if (targetComps == null || targetComps.length == 0) {
			MessageBox.post("请选择零组件版本后执行此操作", "错误", MessageBox.ERROR);
			return null;
		}
		for (InterfaceAIFComponent comp : targetComps) {
			if (comp instanceof TCComponentItemRevision) {
				compRevList.add((TCComponentItemRevision) comp);
			} else {
				MessageBox.post("请选择零组件版本后执行此操作", "错误", MessageBox.ERROR);
				return null;
			}
		}
		return compRevList;
	}
}