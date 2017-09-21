package com.connor.jifeng.plm.erp.jfom013;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.connor.jifeng.plm.erp.bean.JFomProjectBean;
import com.connor.jifeng.plm.erp.ui.util.JFomErpDialog;
import com.connor.jifeng.plm.erp.util.JFomErpMethodUtil;
import com.connor.jifeng.plm.erp.util.JFomErpUtil;
import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.SqlUtil;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentProject;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class JFomErpProjEcnDialog extends JFomErpDialog {

	/**��������*/
	private JLabel ecnNo;
	private JTextField ecnText;
	private SimpleDateFormat sdf;
	private SimpleDateFormat sdf2;
	private HashMap<String, String> projXMXZMap;
	private HashMap<String, String> projKHJLMap;
	private HashMap<String, String> projXMLXMap;

	public JFomErpProjEcnDialog(String dialogName,
			AbstractAIFUIApplication app, TCSession session) {
		super("��Ч����", dialogName, app, session);
		this.sdf = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM);
		this.sdf2 = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM_PROJ);
		getProjLovMapping();
	}

	public void getProjLovMapping() {
		projXMXZMap = JFomMethodUtil.getPrefStrArray("Cust_ERP_PLM_PROJ_XMXZ",
				"@");
		projKHJLMap = JFomMethodUtil.getPrefStrArray("Cust_ERP_PLM_PROJ_KHJL",
				"@");
		projXMLXMap = JFomMethodUtil.getPrefStrArray("Cust_ERP_PLM_PROJ_XMLX",
				"@");
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
			// this.dispose();
			okEvent();
		} else if (e.getSource().equals(this.celBtn)) {
			this.dispose();
		}
		//

		super.actionPerformed(e);
	}

	/**
	 * ȷ����ť����¼�
	 */
	public void okEvent() {
		// Date vDate = this.discardLabel

		InterfaceAIFComponent[] comps = this.app.getTargetComponents();
		List<TCComponentProject> projList = new ArrayList<TCComponentProject>();
		if (comps == null || comps.length == 0) {
			MessageBox.post("��ѡ����Ŀ��ִ�иò���", "����", 1);
			return;
		}
		for (InterfaceAIFComponent comp : comps) {
			if (comp instanceof TCComponentProject) {
				projList.add((TCComponentProject) comp);
			}
		}
		getPropListProp(projList);

	}

	/**
	 * ��ȡ��Ŀ������
	 * 
	 * @param projList
	 */
	public void getPropListProp(List<TCComponentProject> projList) {
		if (projList == null || projList.size() == 0) {
			MessageBox.post("��ѡ����Ŀ��ִ�иò���", "����", 1);
			return;
		}

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

		List<JFomProjectBean> beanList = new ArrayList<JFomProjectBean>();
		// String[] propNames = new String[] { "project_id", "object_name",
		// "object_desc", "owning_user" };
		String[] propNames = new String[] { "project_id", "object_name",
				"object_desc", "owning_user", "jf3_xmjlrq1", "jf3_xmxz1",
				"jf3_khjl1", "jf3_yjksrq1", "jf3_zjc1", "jf3_zjkh1", "jf3_cx1",
				"jf3_xmlx1", "jf3_fzbm1", "jf3_yjwcrq1", "jf3_nrsm1",
				"jf3_xmjl1", "jf3_xmqdrq1", "jf3_OTSyjtg1", "jf3_PPAP1",
				"jf3_SOPplsc1", "jf3_smzq1", "jf3_ycncl1", "jf3_lcghsj1" };
		try {
			TCProperty[][] props = TCComponentType.getTCPropertiesSet(projList,
					propNames);
			StringBuffer logSB = new StringBuffer();
			boolean isError = false;
			if (props != null) {
				for (int i = 0; i < props.length; i++) {
					JFomProjectBean bean = new JFomProjectBean();
					bean.setPROJ_NUMBER(props[i][0].getStringValue());
					bean.setPROJ_CODE(props[i][1].getStringValue());
					bean.setPROJ_NAME(props[i][2].getStringValue());
					bean.setPROJ_MANAGER(((TCComponentUser) props[i][3]
							.getReferenceValue()).getUserId());

					try {
						if (props[i][4].getStringValue() != null
								&& !props[i][4].getStringValue().isEmpty())
							bean.setPROJ_CREATE_DATE(sdf2.parse(props[i][4]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[��Ŀ��������]������д����\r\n");
					}
					if (this.projXMXZMap.containsKey(props[i][5]
							.getStringValue()))
						bean.setPROJ_NATURE(this.projXMXZMap.get(props[i][5]
								.getStringValue()));
					else
						bean.setPROJ_NATURE(props[i][5].getStringValue());
					if (this.projKHJLMap.containsKey(props[i][6]
							.getStringValue()))
						bean.setCUSTOMER_MANAGER(this.projKHJLMap
								.get(props[i][6].getStringValue()));
					else
						bean.setCUSTOMER_MANAGER(props[i][6].getStringValue());
					try {
						if (props[i][7].getStringValue() != null
								&& !props[i][7].getStringValue().isEmpty())
							bean.setPLAN_START_DATE(sdf2.parse(props[i][7]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[��Ŀ��ʼ����]������д����\r\n");
					}
					bean.setASSEMBLY_CUSTOMER(props[i][8].getStringValue());
					bean.setCUSTOMER(props[i][9].getStringValue());
					bean.setMODELS(props[i][10].getStringValue());
					if (this.projXMLXMap.containsKey(props[i][11]
							.getStringValue()))
						bean.setPROJ_TYPE(this.projXMLXMap.get(props[i][11]
								.getStringValue()));
					else
						bean.setPROJ_TYPE(props[i][11].getStringValue());
					bean.setRESP_DEPT(props[i][12].getStringValue());
					try {
						if (props[i][13].getStringValue() != null
								&& !props[i][13].getStringValue().isEmpty())
							bean.setPLAN_FINISH_DATE(sdf2.parse(props[i][13]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[��Ŀ�������]������д����\r\n");
					}
					// bean.setPROJ_MANAGER();
					bean.setCONTENT_DESC(props[i][14].getStringValue());
					bean.setPROJ_MANAGER(props[i][15].getStringValue());
					try {
						if (props[i][16].getStringValue() != null
								&& !props[i][16].getStringValue().isEmpty())
							bean.setPROJ_START_DATE(sdf2.parse(props[i][16]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[��Ŀ��������]������д����\r\n");
					}
					try {
						if (props[i][17].getStringValue() != null
								&& !props[i][17].getStringValue().isEmpty())
							bean.setOTS_DATE(sdf2.parse(props[i][17]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[OTS�����ṩ]������д����\r\n");
					}
					try {
						if (props[i][18].getStringValue() != null
								&& !props[i][18].getStringValue().isEmpty())
							bean.setPPAP_DATE(sdf2.parse(props[i][18]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[PPAP]������д����\r\n");
					}
					try {
						if (props[i][19].getStringValue() != null
								&& !props[i][19].getStringValue().isEmpty())
							bean.setSOP_DATE(sdf2.parse(props[i][19]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[SOP��������]������д����\r\n");
					}
					bean.setLIFE_DATE(props[i][20].getStringValue());
					bean.setANNUAL_OUTPUT(props[i][21].getStringValue());
					try {
						if (props[i][22].getStringValue() != null
								&& !props[i][22].getStringValue().isEmpty())
							bean.setPLAN_PROD_DATE(sdf2.parse(props[i][22]
									.getStringValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						isError = true;
						logSB.append("��ĿID=[" + props[i][0].getStringValue()
								+ "]��[�����滮ʱ��]������д����\r\n");
					}
					beanList.add(bean);
				}
			}
			if (isError) {
				MessageBox.post("��Ŀ���Դ������⣬�����´��ݣ�\r\n" + logSB.toString(), "����",
						MessageBox.WARNING);
				// return;
			}
			sendProjMSGToDB(beanList);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendProjMSGToDB(List<JFomProjectBean> beanList) {
		if (beanList == null || beanList.size() == 0) {
			MessageBox.post("��ѡ����Ŀ��ִ�д˲���", "����", MessageBox.ERROR);
			return;
		}

		String userID = "";
		try {
			userID = this.session.getUser().getUserId();

		} catch (TCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MessageBox.post("��ȡ�û�ID�����쳣��", "�쳣", MessageBox.ERROR);
			return;
		}

		String patchID = JFomErpMethodUtil.getPatchIdStr();
		Date vaileDate = this.discardText.getDate();
		// SimpleDateFormat sdf = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM);
		String commitDateStr = this.sdf.format(new Date());
		if (vaileDate == null) {
			MessageBox.post("��ѡ����Ч����", "����", MessageBox.ERROR);
			return;
		}
		SqlUtil.getConnection();
		StringBuffer logSb = new StringBuffer();

		for (int i = 0; i < beanList.size(); i++) {
			JFomProjectBean bean = beanList.get(i);
			try {

				SqlUtil.update(JFomErpUtil.SQL_PROJ_UPDATE, new Object[] { 4,
						commitDateStr, bean.getPROJ_NUMBER(), 0 });
				SqlUtil.update(JFomErpUtil.SQL_PROJ_UPDATE, new Object[] { 3,
						commitDateStr, bean.getPROJ_NUMBER(), 2 });
				SqlUtil.write(
						JFomErpUtil.SQL_PROJ_INSERT,
						new Object[] {
								bean.getPROJ_NUMBER(),
								bean.getPROJ_CODE(),
								bean.getPROJ_NAME(),
								patchID,
								bean.getPROJ_MANAGER(),
								commitDateStr,
								userID,
								"PLM",
								bean.getPROJ_CREATE_DATE() == null ? "" : sdf
										.format(bean.getPROJ_CREATE_DATE()),
								bean.getPROJ_NATURE(),
								bean.getCUSTOMER_MANAGER(),
								bean.getPLAN_START_DATE() == null ? "" : sdf
										.format(bean.getPLAN_START_DATE()),
								bean.getASSEMBLY_CUSTOMER(),
								bean.getCUSTOMER(),
								bean.getMODELS(),
								bean.getPROJ_TYPE(),
								bean.getRESP_DEPT(),
								bean.getPLAN_FINISH_DATE() == null ? "" : sdf
										.format(bean.getPLAN_FINISH_DATE()),
								bean.getCONTENT_DESC(),
								bean.getPROJ_START_DATE() == null ? "" : sdf
										.format(bean.getPROJ_START_DATE()),
								bean.getOTS_DATE() == null ? "" : sdf
										.format(bean.getOTS_DATE()),
								bean.getPPAP_DATE() == null ? "" : sdf
										.format(bean.getPPAP_DATE()),
								bean.getSOP_DATE() == null ? "" : sdf
										.format(bean.getSOP_DATE()),
								bean.getLIFE_DATE(),
								bean.getANNUAL_OUTPUT(),
								bean.getPLAN_PROD_DATE() == null ? "" : sdf
										.format(bean.getPLAN_PROD_DATE()) });
				if (SqlUtil.rs != null)
					logSb.append("�ɹ�������Ŀ[ID=").append(bean.getPROJ_NUMBER())
							.append(" NAME=").append(bean.getPROJ_NAME())
							.append(" CODE=").append(bean.getPROJ_CODE())
							.append("]\r\n");
				else
					logSb.append("ʧ�ܲ�����Ŀ[ID=").append(bean.getPROJ_NUMBER())
							.append(" NAME=").append(bean.getPROJ_NAME())
							.append(" CODE=").append(bean.getPROJ_CODE())
							.append("]\r\n");

			} catch (Exception e) {
				e.printStackTrace();
				logSb.append("ʧ�ܲ�����Ŀ[ID=").append(bean.getPROJ_NUMBER())
						.append(" NAME=").append(bean.getPROJ_NAME())
						.append(" CODE=").append(bean.getPROJ_CODE())
						.append("]\r\n");
			}
		}

		try {
			SqlUtil.update(JFomErpUtil.SQL_ECN_UPDATE, new Object[] { 4,
					this.ecnText.getText(), 0 });
			SqlUtil.update(JFomErpUtil.SQL_ECN_UPDATE, new Object[] { 3,
					this.ecnText.getText(), 2 });
			SqlUtil.write(
					JFomErpUtil.SQL_ECN_WRITE,
					new Object[] { this.ecnText.getText(), "", patchID, 0,
							sdf.format(vaileDate) });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			SqlUtil.connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlUtil.free();
		MessageBox.post("д�����ݵ��м�����:\r\n" + logSb.toString(), "д���������",
				MessageBox.INFORMATION);

	}
}