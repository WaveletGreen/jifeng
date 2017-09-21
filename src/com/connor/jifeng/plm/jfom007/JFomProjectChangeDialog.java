package com.connor.jifeng.plm.jfom007;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.connor.jifeng.plm.util.ExcelUtil;
import com.connor.jifeng.plm.util.ExcelUtil07;
import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.JFomUtil;
import com.teamcenter.rac.aif.AbstractAIFDialog;
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
import com.teamcenter.rac.util.DateButton;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

//import com.teamcenter.soa.exceptions.NotLoadedException;

public class JFomProjectChangeDialog extends AbstractAIFDialog {

	private AbstractAIFUIApplication app;
	private TCSession session;
	private JPanel panel;
	private JPanel panel2;
	private JPanel rootPanel;
	private JComboBox<String> sourceBox;
	private JComboBox<String> custormBox;
	private JTextField projectID;
	// private DateButton startDateButton1;
	// private DateButton startDateButton2;
	private DateButton closeDateButton1;
	private DateButton closeDateButton2;
	private JButton fileButton;
	private JTextField filePathText;
	private JButton okButton;
	private JButton celButton;
	private JFileChooser jfc;
	// *************
	private HashMap<String, String> customMap;
	private HashMap<String, String> projectMap;
	private List<String> customEN;
	private List<String> customCH;
	private List<String> projectEN;
	private List<String> projectCH;
	private List<String> reportMsgList;

	public JFomProjectChangeDialog(AbstractAIFUIApplication app) {
		super(true);
		this.app = app;
		this.session = (TCSession) app.getSession();
		customEN = new ArrayList<String>();
		customCH = new ArrayList<String>();
		projectEN = new ArrayList<String>();
		projectCH = new ArrayList<String>();
		init();
	}

	/**
	 * 界面设计
	 */
	public void init() {
		this.setTitle("工程更改记录");
		this.setPreferredSize(new Dimension(450, 250));
		customMap = JFomMethodUtil
				.getPrefStrArray(JFomUtil.JF3_GGGGJLREF1, ":");

		projectMap = JFomMethodUtil.getPrefStrArray(JFomUtil.JF3_GGGGJLREF2,
				":");
		Set<Entry<String, String>> projectSet = customMap.entrySet();

		customEN.add("");
		customCH.add("");
		for (Entry<String, String> entry : projectSet) {
			customEN.add(entry.getKey());
			customCH.add(entry.getValue());
		}
		projectSet.clear();
		projectSet = projectMap.entrySet();
		projectEN.add("");
		projectCH.add("");
		for (Entry<String, String> entry : projectSet) {
			projectEN.add(entry.getKey());
			projectCH.add(entry.getValue());
		}

		sourceBox = new JComboBox<>(
				customCH.toArray(new String[customCH.size()]));
		custormBox = new JComboBox<>(projectCH.toArray(new String[projectCH
				.size()]));

		projectID = new JTextField(14);
		SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT);
		// startDateButton1 = new DateButton(sdf);
		// startDateButton2 = new DateButton(sdf);
		closeDateButton1 = new DateButton(sdf);
		closeDateButton2 = new DateButton(sdf);
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("c:\\"));// 文件选择器的初始目录定为d盘
		fileButton = new JButton("...");
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectFileButtonEvent(e);
			}
		});
		filePathText = new JTextField(14);
		filePathText.setText("c:\\");
		panel = new JPanel(new PropertyLayout());
		panel.add("1.1.right.top.preferred.preferred", new JLabel("发起方"));
		panel.add("1.2.left.top.preferred.preferred", sourceBox);
		panel.add("2.1.right.top.preferred.preferred", new JLabel("变更分类"));
		panel.add("2.2.left.top.preferred.preferred", custormBox);
		panel.add("3.1.right.top.preferred.preferred", new JLabel("项目"));
		panel.add("3.2.left.top.preferred.preferred", projectID);
		panel.add("4.1.right.top.preferred.preferred", new JLabel("申请日期"));
		// panel.add("4.2.left.top.preferred.preferred", startDateButton1);
		// panel.add("4.3.left.top.preferred.preferred", new JLabel(" 到  "));
		// panel.add("4.4.left.top.preferred.preferred", startDateButton2);
		// panel.add("5.1.right.top.preferred.preferred", new JLabel("关闭日期"));
		panel.add("4.2.left.top.preferred.preferred", closeDateButton1);
		panel.add("4.3.left.top.preferred.preferred", new JLabel(" 到  "));
		panel.add("4.4.left.top.preferred.preferred", closeDateButton2);
		panel.add("5.1.right.top.preferred.preferred", new JLabel("报表导出路径:"));
		panel.add("5.2.left.top.preferred.preferred", filePathText);
		panel.add("5.3.left.top.preferred.preferred", fileButton);
		// **********************************
		okButton = new JButton("确定");
		celButton = new JButton("取消");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						okEvent();
						super.run();
					}
				}.start();

			}
		});
		celButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				celEvent(e);
			}
		});
		rootPanel = new JPanel(new BorderLayout());
		panel2 = new JPanel(new FlowLayout());
		panel2.add(okButton);
		panel2.add(new JLabel("    "));
		panel2.add(celButton);
		rootPanel.add(panel, BorderLayout.CENTER);
		rootPanel.add(panel2, BorderLayout.SOUTH);
		this.add(rootPanel, "Center");
		this.pack();
		this.centerToScreen();
		this.showDialog();
		this.setAutoRequestFocus(true);

	}

	/**
	 * 文件夹选择按钮事件
	 * 
	 * @param e
	 */
	public void selectFileButtonEvent(ActionEvent e) {
		jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
		int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return;
		} else {
			File f = jfc.getSelectedFile();// f为选择到的目录
			filePathText.setText(f.getAbsolutePath());
		}
	}

	/**
	 * 添加确定按钮的监听事件
	 * 
	 * @param e
	 */
	public void okEvent() {
		// this.setVisible(false);
		String filePath = filePathText.getText();
		if (!filePath.trim().equals("") && !(new File(filePath)).isDirectory()) {
			MessageBox.post("请选择文件夹后再进行此操作", "警告", MessageBox.WARNING);
			return;
		}
		String revType = JFomUtil.JF3_QUERY_TYPE;
		int index = sourceBox.getSelectedIndex();
		String revProp1 = null;
		TCProperty[][] compTcProp = null;
		List<String> statusList = new ArrayList<String>();
		List<String> interEcrList = new ArrayList<String>();
		List<String> fqfList = new ArrayList<String>();
		List<String> owningUserList = new ArrayList<String>();
		List<JFomProjectChangeBean> beanList = new ArrayList<JFomProjectChangeBean>();
		if (index != -1) {
			revProp1 = customEN.get(index);
		} else {
			revProp1 = "*";
		}
		index = custormBox.getSelectedIndex();
		String revProp2 = null;
		if (index != -1) {
			revProp2 = projectEN.get(index);
		} else {
			revProp2 = "*";
		}
		String projectID = this.projectID.getText();
		SimpleDateFormat sdf = new SimpleDateFormat(JFomUtil.TIME_FORMAT3);
		SimpleDateFormat sdf2 = new SimpleDateFormat(JFomUtil.TIME_FORMAT4);
		// Date taskStartDate = this.startDateButton1.getDate();
		// Date taskEndDate = this.startDateButton2.getDate();
		Date releaseStartDate = this.closeDateButton1.getDate();
		Date releaseEndDate = this.closeDateButton2.getDate();
		// String time1 = "*";
		// String time2 = "*";
		String time3 = "*";
		String time4 = "*";
		// String[] keys = { JFomUtil.JF3_QUERY_PROJECT_ID,
		// JFomUtil.JF3_QUERY_ITEM_TYPE, JFomUtil.JF3_QUERY_PROP_1,
		// JFomUtil.JF3_QUERY_PROP_2, JFomUtil.JF3_QUERY_CREATE_DATE_AFT,
		// JFomUtil.JF3_QUERY_CREATE_DATE_BEF,
		// JFomUtil.JF3_QUERY_RELEASE_DATE_AFT,
		// JFomUtil.JF3_QUERY_RELEASE_DATE_BEF };
		// String[] keys = null;
		// String[] values = null;
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();

		keyList.add(JFomUtil.JF3_QUERY_ITEM_TYPE);
		valueList.add(revType);

		if (!projectID.trim().isEmpty()) {
			keyList.add(JFomUtil.JF3_QUERY_PROJECT_ID);
			valueList.add(projectID);

		}
		if (!revProp1.trim().isEmpty()) {
			keyList.add(JFomUtil.JF3_QUERY_PROP_1);
			valueList.add(revProp1);
		}
		if (!revProp2.trim().isEmpty()) {
			keyList.add(JFomUtil.JF3_QUERY_PROP_2);
			valueList.add(revProp2);
		}

		if (releaseStartDate != null) {
			time3 = sdf.format(releaseStartDate);
			keyList.add(JFomUtil.JF3_QUERY_RELEASE_DATE_AFT);
			valueList.add(time3);
		}
		if (releaseEndDate != null) {
			time4 = sdf2.format(releaseEndDate);
			keyList.add(JFomUtil.JF3_QUERY_RELEASE_DATE_BEF);
			valueList.add(time4);
		}

		// if (releaseStartDate != null && releaseEndDate != null) {
		// keys = new String[] { JFomUtil.JF3_QUERY_PROJECT_ID,
		// JFomUtil.JF3_QUERY_ITEM_TYPE, JFomUtil.JF3_QUERY_PROP_1,
		// JFomUtil.JF3_QUERY_PROP_2,
		// JFomUtil.JF3_QUERY_RELEASE_DATE_AFT,
		// JFomUtil.JF3_QUERY_RELEASE_DATE_BEF };
		// values = new String[] { projectID, revType, revProp1, revProp2,
		// time3, time4 };
		// } else if (releaseStartDate == null && releaseEndDate != null) {
		// keys = new String[] { JFomUtil.JF3_QUERY_PROJECT_ID,
		// JFomUtil.JF3_QUERY_ITEM_TYPE, JFomUtil.JF3_QUERY_PROP_1,
		// JFomUtil.JF3_QUERY_PROP_2,
		//
		// JFomUtil.JF3_QUERY_RELEASE_DATE_BEF };
		// values = new String[] { projectID, revType, revProp1, revProp2,
		// time4 };
		// } else if (releaseStartDate != null && releaseEndDate == null) {
		// keys = new String[] { JFomUtil.JF3_QUERY_PROJECT_ID,
		// JFomUtil.JF3_QUERY_ITEM_TYPE, JFomUtil.JF3_QUERY_PROP_1,
		// JFomUtil.JF3_QUERY_PROP_2,
		//
		// JFomUtil.JF3_QUERY_RELEASE_DATE_AFT };
		// values = new String[] { projectID, revType, revProp1, revProp2,
		// time3 };
		// } else {
		// keys = new String[] { JFomUtil.JF3_QUERY_PROJECT_ID,
		// JFomUtil.JF3_QUERY_ITEM_TYPE, JFomUtil.JF3_QUERY_PROP_1,
		// JFomUtil.JF3_QUERY_PROP_2 };
		// values = new String[] { projectID, revType, revProp1, revProp2 };
		// }

		// ============================================================

		// MessageBox.post(JFomUtil.JF3_QUERY_PROJECT_ID+"|"+JFomUtil.JF3_QUERY_ITEM_TYPE
		// + "|"+JFomUtil.JF3_QUERY_PROP_1+"|"+JFomUtil.JF3_QUERY_PROP_2
		// +"|"+JFomUtil.JF3_QUERY_CREATE_DATE_AFT+"|"+JFomUtil.JF3_QUERY_CREATE_DATE_BEF+"|"+JFomUtil.JF3_QUERY_RELEASE_DATE_AFT+"|"+JFomUtil.JF3_QUERY_RELEASE_DATE_BEF,
		// "NAME", MessageBox.ERROR);
		// MessageBox.post(projectID +
		// "|"+revType+"|"+revProp1+"|"+revProp2+"|"+sdf.format(taskStartDate)+"|"+sdf2.format(taskEndDate)+"|"+sdf.format(releaseStartDate)+"|"+sdf2.format(releaseEndDate),
		// "VALUE", MessageBox.ERROR);
		//

		InterfaceAIFComponent[] comps = JFomMethodUtil
				.searchComponentsCollection(session, JFomUtil.JF3_QUERY_NAME,
						keyList.toArray(new String[keyList.size()]),
						valueList.toArray(new String[valueList.size()]));
		// TODO 当搜索结果为0的时候结束
		if (comps.length == 0) {
			MessageBox.post("搜索出来的申请评审单的个数为0个,请重新输入查询条件并查询", "警告",
					MessageBox.ERROR);
			// this.setVisible(true);
			return;
		}

		List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
		String owningUserStr = "";
		for (InterfaceAIFComponent comp : comps) {
			revList.add((TCComponentItemRevision) comp);
			// try {
			// AIFComponentContext[] taskContext =
			// ((TCComponentItemRevision)comp).whereReferencedByTypeRelation(new
			// String[]{"EPMTask"}, new
			// String[]{JFomUtil.JF3_GGGG_EPMTASK_ATTACHEMENTS});
			// if(taskContext!= null && taskContext.length!=0){
			// owningUserStr =((TCComponentTask)
			// taskContext[0].getComponent()).getTCProperty("owning_user").getTCComponent().getPropertyDisplayableValue("user_name");
			// }else{
			// MessageBox.post("","aaaa",MessageBox.ERROR);
			// }
			// } catch (TCException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (NotLoadedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		}
		// TODO 添加获取属性的逻辑
		// 获取更改通知两种关系下的对象
		try {
			compTcProp = TCComponentType.getTCPropertiesSet(revList,
					new String[] { JFomUtil.JF3_GGTZ_GRM,
							JFomUtil.JF3_GGGG_MASTER_FORM,
							JFomUtil.JF3_GGGG_GGPSD_EXCEL,
							JFomUtil.JF3_GGGG_ALLWORKFLOWS });

		} catch (TCException e1) {
			e1.printStackTrace();
		}

		if (compTcProp == null) {
			MessageBox.post("查询不到更改通知，请查看数据源是否正确", "警告", MessageBox.WARNING);
			return;
		}
		revList.clear();

		// 获取全部的工程变更单
		for (int i = 0; i < compTcProp.length; i++) {
			TCComponent[] compTemp = compTcProp[i][0].getReferenceValueArray();
			// MessageBox.post("compTemp  length =" + compTemp.length, "", 1);
			String tempStatus = "";
			String fqfStr = "";
			try {
				TCComponentForm masterform = (TCComponentForm) compTcProp[i][1]
						.getReferenceValueArray()[0];
				tempStatus = masterform
						.getStringProperty(JFomUtil.JF3_GGGG_PART_STATUS);
				fqfStr = masterform.getTCProperty(JFomUtil.JF3_GGGG_GGFQF)
						.getDisplayableValue();
			} catch (TCException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}// JFomUtil.JF3_GGGG_PART_STATUS
			TCComponent[] excelComps = compTcProp[i][2]
					.getReferenceValueArray();
			String ecrIdStr = "";
			if (excelComps != null) {
				for (TCComponent excel : excelComps) {
					String tempPath = "";
					if (excel.getType().equals("MSExcelX")) {
						// TODO
						tempPath = JFomMethodUtil.downLoadFile(excel);
						List<String> ecrList = ExcelUtil07
								.getExcelNamedCellValue(tempPath,
										new String[] { "ECR" });
						ecrIdStr = ecrList.get(0);
						// interEcrList.add(e)
					} else {
						tempPath = JFomMethodUtil.downLoadFile(excel);
					}
				}
			}
			TCComponent[] workflows = compTcProp[i][3].getReferenceValueArray();
			if (workflows != null && workflows.length != 0) {
				try {

					owningUserStr = ((TCComponentUser) (workflows[0]
							.getReferenceProperty("owning_user")))
							.getOSUserName();
					// MessageBox.post(workflows[0].getTCProperty("owning_user").getTCComponent().getClassType()
					// +"=== >> "+owningUserStr,"bbb",MessageBox.ERROR);
				} catch (TCException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			for (int j = 0; j < compTemp.length; j++) {
				if (compTemp[j] instanceof TCComponentItem) {
					try {
						revList.add(((TCComponentItem) compTemp[j])
								.getLatestItemRevision());
					} catch (TCException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (compTemp[j] instanceof TCComponentItemRevision) {
					revList.add((TCComponentItemRevision) compTemp[j]);
				} else {
					continue;
				}
				statusList.add(tempStatus);
				interEcrList.add(ecrIdStr);
				owningUserList.add(owningUserStr);
				fqfList.add(fqfStr);
			}
		}
		if (revList.size() == 0) {
			MessageBox.post("工程变更通知单的数量为0结束报表", "错误", MessageBox.ERROR);
			return;
		}

		try {
			compTcProp = TCComponentType.getTCPropertiesSet(revList,
					new String[] { JFomUtil.JF3_GGTZ_GGH_GRM,
							JFomUtil.JF3_GGTZD_PROP_ITEM_ID,
							JFomUtil.JF3_GGTZD_PROP_DATE_RELEASED,
							JFomUtil.JF3_GGTZD_EXCEL });
		} catch (TCException e1) {
			e1.printStackTrace();
		}

		if (compTcProp == null) {
			MessageBox.post("获取属性错误，程序异常，退出报表", "错误", MessageBox.ERROR);
			return;
		}
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		// TODO 循环遍历所有的工程更改单
		for (int i = 0; i < compTcProp.length; i++) {
			JFomProjectChangeBean bean = new JFomProjectChangeBean();
			bean.setIndexStr(i + "");
			bean.setInitiator(fqfList.get(i));// bean.setInternalCode("");//发起放
			bean.setPartStatus(statusList.get(i));
			bean.setChargePerson(owningUserList.get(i));// 设置责任人
			bean.setECRCode(interEcrList.get(i));// 设置内部ECR编号
			TCComponent[] comp = compTcProp[i][0].getReferenceValueArray();
			String itemID = compTcProp[i][1].getStringValue();
			bean.setInternalCode(itemID);// 内部编号
			Date dateReleased = compTcProp[i][2].getDateValue();
			if (dateReleased != null) {
				bean.setSureDate(dateF.format(dateReleased));
			}
			TCComponent[] excelComp = compTcProp[i][3].getReferenceValueArray();
			Date partDateReleased = new Date();
			if (comp != null && comp.length > 0) {
				try {
					partDateReleased = comp[0]
							.getDateProperty(JFomUtil.JF3_GGTZD_PROP_DATE_RELEASED);
					if (partDateReleased != null)
						bean.setDrawingNo(dateF.format(partDateReleased));
				} catch (TCException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// 从EXCEL中捞取ECN，变更前描述，和变更描述
			if (excelComp != null) {
				for (int j = 0; j < excelComp.length; j++) {
					String typeT = excelComp[j].getType();
					if (typeT.equals("MSExcel")) {
						String filePathT = JFomMethodUtil
								.downLoadFile(excelComp[j]);
						if (!filePathT.trim().isEmpty()
								&& new File(filePathT).isFile()) {
							List<String> excelMsgList = ExcelUtil
									.getExcelNamedCellValue(
											filePathT,
											new String[] {
													JFomUtil.JF3_GGGGTZD_ECN,
													JFomUtil.JF3_GGGGTZD_BGQMS,
													JFomUtil.JF3_GGGGTZD_BGHMS });
							if (excelMsgList.size() == 3) {
								// bean.setECRCode(excelMsgList.get(0));
								bean.setChangeMsgBef(excelMsgList.get(1));
								bean.setChangeMsg(excelMsgList.get(2));
							}
						}
					} else if (typeT.equals("MSExcelX")) {
						String filePathT = JFomMethodUtil
								.downLoadFile(excelComp[j]);
						if (!filePathT.trim().isEmpty()
								&& new File(filePathT).isFile()) {
							List<String> excelMsgList = ExcelUtil07
									.getExcelNamedCellValue(
											filePathT,
											new String[] {
													JFomUtil.JF3_GGGGTZD_ECN,
													JFomUtil.JF3_GGGGTZD_BGQMS,
													JFomUtil.JF3_GGGGTZD_BGHMS });
							if (excelMsgList.size() == 3) {
								// bean.setECRCode(excelMsgList.get(0));
								bean.setChangeMsgBef(excelMsgList.get(1));
								bean.setChangeMsg(excelMsgList.get(2));
							}

						}
					}
				}
			}
			beanList.add(bean);
		}
		// TODO
		InputStream is = JFomProjectChangeDialog.class
				.getResourceAsStream(JFomUtil.JF3_INPUTFILESTREAM);
		try {
			ExcelUtil.writeExcel(
					filePath + "\\" + JFomUtil.JF3_INPUTFILESTREAM, is,
					beanList);
			MessageBox.post("导出报表成功", "INFO", MessageBox.INFORMATION);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MessageBox.post("导出报表失败", "ERROR", MessageBox.ERROR);
		}
	}

	/**
	 * 
	 * @param comps
	 * @return
	 * @throws TCException
	 */
	public List<String> getChangeMsg(InterfaceAIFComponent[] comps)
			throws TCException {
		List<String> msgList = new ArrayList<>();
		if (comps == null) {
			return msgList;
		}
		List<TCComponentItemRevision> revList = new ArrayList<TCComponentItemRevision>();
		// TODO 添加逻辑
		for (InterfaceAIFComponent comp : comps) {
			TCComponentItemRevision rev = (TCComponentItemRevision) comp;
			revList.add(rev);
		}
		String[] props = new String[] { "item_id",
				JFomUtil.JF3_GGGG_GGTZ_GG_GRM };
		TCProperty[][] tcProps = TCComponentType.getTCPropertiesSet(revList,
				props);
		for (int i = 0; i < tcProps.length; i++) {

		}
		return msgList;
	}

	/**
	 * 添加取消按钮的监听事件
	 * 
	 * @param e
	 */
	public void celEvent(ActionEvent e) {
		this.dispose();

	}

}
