package com.connor.jifeng.plm.erp.jfom011;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

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

public class JFomErpWlEcnDialog extends JFomErpDialog {

	private JLabel ecnNo;
	private JTextField ecnText;

	private List<JFomMaterialBean> partBeanList;

	private List<String> propList;

	public JFomErpWlEcnDialog(String dialogName, AbstractAIFUIApplication app,
			TCSession session) {
		super("��Ч����", dialogName, app, session);
		this.partBeanList = new ArrayList<JFomMaterialBean>();
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

	@Override
	public void setMidPanel() {
		// TODO Auto-generated method stub
		this.ecnNo = new JLabel("�������");
		this.ecnText = new JTextField(24);
		this.midPanel.add("3.1.right.top.preferred.preferred", ecnNo);
		this.midPanel.add("3.2.left.top.preferred.preferred", ecnText);
		super.setMidPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.okBtn)) {
			this.setVisible(false);
			okEvent();

		} else if (e.getSource().equals(this.celBtn)) {
			// celEvent();
			this.dispose();
		}

		super.actionPerformed(e);
	}

	/**
	 * ִ��ȷ������������䵥���в�ѯ
	 * 
	 * ִ�е�ʱ��ѡ��ѯ��䵥���Ƿ����
	 * 
	 */
	public void okEvent() {
		String ecnNo = this.ecnText.getText();

		if (ecnNo == null || ecnNo.trim().isEmpty()) {
			MessageBox.post("��������ȷ��ECN��ţ�", "����", MessageBox.ERROR);
			return;
		}

		InterfaceAIFComponent[] interfaceComps = JFomMethodUtil
				.searchComponentsCollection(session,
						JFomErpUtil.ECN_QUERY_NAME, new String[] {
								JFomErpUtil.ECN_QUERY_TYPE,
								JFomErpUtil.ECN_QUERY_ID }, new String[] {
								JFomErpUtil.ECN_TYPE, ecnNo });

		if (interfaceComps == null || interfaceComps.length != 1) {
			MessageBox.post("�������䵥�Ų����ڣ�", "����", MessageBox.ERROR);
			return;
		}

		InterfaceAIFComponent[] comps = this.app.getTargetComponents();
		List<TCComponentItemRevision> revList = JFomMethodUtil
				.getAllRevComp(comps);

		if (revList == null) {
			MessageBox.post("��ѡ�����ϰ汾�󴫵�", "����", MessageBox.ERROR);
			return;
		}
		if (!checkRevList(revList)) {
			MessageBox.post("��ѡ�����ϣ���Ʒ�����Ʒ��ԭ���ϣ��󴫵�", "����", MessageBox.ERROR);
			return;
		}
		if (!checkRevIsLast(revList)) {
			MessageBox.post("��ѡ���������°汾���д���", "����", MessageBox.ERROR);
			return;
		}

		// for (TCComponentItemRevision rev : revList) {
		// if (isObsolete(rev)) {
		// MessageBox.post("��ѡ��û�з��������Ϻ󴫵�", "����", MessageBox.ERROR);
		// return;
		// }
		// }
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
					if (name.equals("Obsolete") || name.equals("����")) {
						isd = true;
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		return isd;
	}

	/**
	 * ��ȡ����
	 * 
	 * @param revList
	 */
	public void getRevMessage(List<TCComponentItemRevision> revList) {
		if (revList == null) {
			MessageBox.post("��ѡ����Ҫ���������Ϻ󴫵ݣ�", "����", 1);
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
				MessageBox.post("�Ӱ汾���Ҷ������", "����", MessageBox.ERROR);
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
			// ���ƾ�����������
			TCProperty[][] propssForm = TCComponentType.getTCPropertiesSet(
					formList, this.erp_java_values);
			TCProperty[][] propssForm2 = TCComponentType.getTCPropertiesSet(
					formList, new String[] { "jf3_sfgyj", "jf3_gyjbz" });// �����ӵ�����SHARE_USE,SHARE_MEMO
			// û�����ƴ�ӹ����ʱ�������
			/*
			 * TCProperty[][] propssForm = TCComponentType.getTCPropertiesSet(
			 * formList, new String[] { "jf3_YCLgg", "jf3_xmdh", "jf3_khljh", //
			 * 2 ԭ���� "jf3_YCLClph", "jf3_YCLclcj", // 4 ��Ʒ "jf3_CPcpjg",
			 * "jf3_CPcplx", "jf3_CPmlys", "jf3_CPmlcz", "jf3_CPfxys", // 9 ���Ʒ
			 * "jf3_BCPyt", "jf3_BCPcz", // 11 ���� "jf3_ys", "jf3_wl", "jf3_bmcl"
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

				// �����ӵ�����jf3_sfgyj
				if (propssForm2[i][0] != null
						&& propssForm2[i][0].getStringValue() != null) {
					bean.setSHARE_USE(propssForm2[i][0].getStringValue());

				}
				// �����ӵ�����jf3_gyjbz
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
				bean.setRELEASED_STATUS(isObsolete(revList.get(i)) ? "Y" : "N");
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
						MessageBox.post("���Ҳ�����Ⱥ�룡", "����", MessageBox.ERROR);
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
	 * д�����ݵ��м��
	 * 
	 * @param partBeanList
	 */
	public void transfBeanMessageToDB(List<JFomMaterialBean> partBeanList) {
		if (partBeanList == null || partBeanList.size() == 0) {
			MessageBox.post("��ѡ����ʵĶ����ִ�д˲���", "��ʾ", MessageBox.WARNING);
			return;
		}
		String batchID = JFomErpMethodUtil.getPatchIdStr();
		Date vaileDate = this.discardText.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM);
		String commiteDateStr = sdf.format(new Date());
		String userID = "";
		try {
			userID = this.session.getUser().getUserId();

		} catch (TCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MessageBox.post("��ȡ�û�ID�����쳣��", "�쳣", MessageBox.ERROR);
			return;
		}
		if (vaileDate == null) {
			MessageBox.post("��ѡ����Ч����", "����", MessageBox.ERROR);
			return;
		}
		// java.sql.Date sqlDate = new java.sql.Date(vaileDate.getTime());
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
		try {
			for (JFomMaterialBean bean : partBeanList) {
				// ResultSet set = SqlUtil.read(
				// JFomErpUtil.SELECT_PART_SQL,
				// new Object[] { bean.getPART_NAMBER(),
				// bean.getPART_REV() });
				// if (!set.next()) {} else {
				try {
					// �����ݿ��д���״̬Ϊ0��ʱ���������״̬Ϊ1�����²�������
					SqlUtil.update(
							JFomErpUtil.UPDATE_PART_SQL,
							new Object[] { 3, commiteDateStr,
									bean.getPART_NAMBER(), bean.getPART_REV(),
									2 });
					SqlUtil.update(
							JFomErpUtil.UPDATE_PART_SQL,
							new Object[] { 4, commiteDateStr,
									bean.getPART_NAMBER(), bean.getPART_REV(),
									0 });
					SqlUtil.write(
							insertSQL,
							new Object[] { bean.getPART_NAMBER(),
									bean.getPART_NAME(), bean.getPART_REV(), 0,
									batchID, commiteDateStr, userID,
									bean.getPART_SPEC(), bean.getCLASS_CODE(),
									bean.getSOURCE(), bean.getPROJ_CODE(),
									bean.getUNIT(), bean.getRELEASED_STATUS(),
									sdf.format(bean.getINVALID_DATE()),
									bean.getCUSTOMER_PART_NUMBER(),
									bean.getIS_STANDARD(), bean.getCREATER(),
									sdf.format(bean.getCREATE_DATE()),
									bean.getSHARE_USE(), bean.getSHARE_MEMO() });
					if (SqlUtil.rs != null) {
						msgSB.append("�ɹ���������ID=[")
								.append(bean.getPART_NAMBER())
								.append("],REV=[").append(bean.getPART_REV())
								.append("]������\r\n");

					} else {
						msgSB.append("ʧ�ܲ�������ID=[")
								.append(bean.getPART_NAMBER())
								.append("],REV=[").append(bean.getPART_REV())
								.append("]������\r\n");
					}
				} catch (Exception e) {

					e.printStackTrace();
					msgSB.append("ʧ�ܲ�������ID=[").append(bean.getPART_NAMBER())
							.append("],REV=[").append(bean.getPART_REV())
							.append("]������\r\n");
				}
				// }
			}
			SqlUtil.update(JFomErpUtil.SQL_ECN_UPDATE, new Object[] { 4,
					this.ecnText.getText(), 0 });
			SqlUtil.update(JFomErpUtil.SQL_ECN_UPDATE, new Object[] { 3,
					this.ecnText.getText(), 2 });
			SqlUtil.write(
					JFomErpUtil.SQL_ECN_WRITE,
					new Object[] { this.ecnText.getText(), "", batchID, 0,
							sdf.format(vaileDate) });

			MessageBox.post("д�����ݵ��м�����:\r\n" + msgSB.toString(), "д���������",
					MessageBox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox.post("д�����ݵ��м��ʧ�ܣ�" + msgSB.toString(), "д������ʧ��",
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
