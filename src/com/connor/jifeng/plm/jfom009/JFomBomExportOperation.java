package com.connor.jifeng.plm.jfom009;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.connor.jifeng.plm.erp.util.JFomErpMethodUtil;
import com.connor.jifeng.plm.util.ExcelUtil07;
import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.JFomUtil;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentGroupMember;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentProject;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class JFomBomExportOperation extends AbstractAIFOperation {

	private TCComponentBOMLine topLine;
	private List<JFomBomExportBean> beanList;
	private List<TCComponentBOMLine> bomlineList;
	private List<TCComponentItemRevision> revList;
	private List<TCComponentForm> formList;
	// 图纸版本可能没有
	private List<Integer> indexList;
	private List<Integer> drawIndexList;
	private List<TCComponentItemRevision> drawRevList;
	// private HashMap<Integer, TCComponentItemRevision> drawRevMap;
	//
	private List<TCComponentForm> drawFormList;
	private List<TCComponentDataset> pictureList;
	private String exportBomPath;
	private String projID;
	private TCSession session;
	private JFomBomExportProjBean projectBean;

	// private HashMap<Integer, TCComponentForm> drawFormMap;
	protected String[] cp_name_values;
	protected String[] bcp_name_values;
	protected String[] ycl_name_values;
	protected String[] cp_gg_values;
	protected String[] bcp_gg_values;
	protected String[] erp_java_values;

	public void getErpNameAndGG() {
		TCPreferenceService service = this.session.getPreferenceService();
		cp_name_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_CP_Name_Props");
		cp_gg_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_CP_GG_Props");
		bcp_name_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_BCP_Name_Props");
		bcp_gg_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_BCP_GG_Props");
		ycl_name_values = service.getStringArray(
				TCPreferenceService.TC_preference_site,
				"Cust_ERP_PLM_YCL_NAME_Props");
		erp_java_values = service
				.getStringArray(TCPreferenceService.TC_preference_site,
						"Cust_ERP_PLM_WL_Props");
	}

	public JFomBomExportOperation(TCComponentBOMLine topLine,
			String exportBomPath, String projID, TCSession session) {
		this.topLine = topLine;
		// excel表格信息
		this.beanList = new ArrayList<JFomBomExportBean>();
		// bomlline 缓存
		this.bomlineList = new ArrayList<TCComponentBOMLine>();
		// 物料缓存
		this.revList = new ArrayList<TCComponentItemRevision>();
		// 物料表单缓存
		this.formList = new ArrayList<TCComponentForm>();
		// 图纸缓存
		this.drawRevList = new ArrayList<TCComponentItemRevision>();
		// this.drawRevMap = new HashMap<Integer, TCComponentItemRevision>();
		// 图纸表单缓存
		this.drawFormList = new ArrayList<TCComponentForm>();
		this.pictureList = new ArrayList<TCComponentDataset>();
		this.drawIndexList = new ArrayList<Integer>();
		this.indexList = new ArrayList<Integer>();
		this.exportBomPath = exportBomPath;
		this.projID = projID;
		this.session = session;
		getErpNameAndGG();

		// this.drawFormMap = new HashMap<Integer, TCComponentForm>();

		// JFomBomExportBean bean = new JFomBomExportBean();
		// bean.setIndexStr(0);
		// beanList.add(bean);
		// bomlineList.add(topLine);

	}

	/**
	 * 
	 * 遍历所有的BOM将全部的BOMLINE存入缓存
	 * 
	 * @param index
	 * @param bomline
	 * @throws TCException
	 */
	public void checkAllBomLine(int index, TCComponentBOMLine bomline)
			throws TCException {
		if (bomline == null) {
			return;
		}
		JFomBomExportBean bean = new JFomBomExportBean();
		AIFComponentContext[] contexts = bomline.getChildren();
		bean.setIndexStr(index);
		bean.setIndex(beanList.size() + 1);
		if (index == 0) {
			bean.setIndexExt("1");
		} else if (index == 1) {
			bean.setIndexExt("1.1");
		}
		this.beanList.add(bean);
		this.bomlineList.add(bomline);

		for (AIFComponentContext context : contexts) {
			TCComponentBOMLine childLine = (TCComponentBOMLine) context
					.getComponent();
			checkAllBomLine(index + 1, childLine);
		}
	}

	public void printMsg() {
		for (JFomBomExportBean bean : beanList) {
			MessageBox.post("" + bean.toString(), "INFO", MessageBox.ERROR);
		}
	}

	/**
	 * 获取项目信息
	 * 
	 * @param projName
	 * @return
	 */
	public JFomBomExportProject getProjMsg(String projName) {
		JFomBomExportProject proj = null;

		return proj;
	}

	/**
	 * 获取项目成员
	 * 
	 * @param projTeam
	 * @return
	 */
	public String projectMember(TCComponent projTeam) {
		StringBuffer memNames = new StringBuffer();
		if (projTeam != null) {
			try {
				TCProperty prop = projTeam.getTCProperty("project_members");
				if (prop != null) {
					TCComponent[] users = prop.getReferenceValueArray();
					if (users != null) {
						for (int i = 0; i < users.length; i++) {
							TCComponentGroupMember member = (TCComponentGroupMember) users[i];
							String usrName = member.getUser()
									.getStringProperty("user_name");
							memNames.append(usrName);
							if (i != users.length - 1)
								memNames.append("、");
						}
					}
				}
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return memNames.toString();
	}

	/**
	 * 获取项目的信息
	 * 
	 * @return
	 */
	public boolean getProjMsg() {
		boolean isOk = true;
		try {
			InterfaceAIFComponent[] resultComps = JFomMethodUtil
					.searchComponentsCollection(this.session,
							JFomUtil.PROJ_QUERY, new String[] { "project_id" },
							new String[] { this.projID });
			if (resultComps != null && resultComps.length > 0) {
				TCComponentProject project = (TCComponentProject) resultComps[0];
				List<TCComponentProject> listProjs = new ArrayList<TCComponentProject>();
				listProjs.add(project);

				TCProperty[][] props = TCComponentType.getTCPropertiesSet(
						listProjs, new String[] { "project_name", // 项目名称
								"jf3_zjkh1",// 直接客户
								"jf3_cx1",// 机型
								"jf3_SOPplsc1",// SOP日期
								"jf3_smzq1",// 生命周期 SOP - SM +1
								"project_team" });// 项目成员
				if (props != null && props.length != 0) {
					String ksrq = null;
					String wcrq = null;
					String lifeTime = "";
					this.projectBean = new JFomBomExportProjBean();
					if (props[0][0] != null)

						this.projectBean.setPROJ_NAME(props[0][0]
								.getStringValue());
					if (props[0][1] != null)
						this.projectBean.setPROJ_CUSTOMER(props[0][1]
								.getDisplayValue());
					if (props[0][2] != null)
						this.projectBean.setPROJ_MODE(props[0][2]
								.getDisplayValue());
					int sopYear = 0;
					int lifeYear = 0;
					if (props[0][3] != null) {
						ksrq = props[0][3].getStringValue();
						if (ksrq != null && ksrq.length() > 4) {
							try {
								sopYear = Integer
										.parseInt(ksrq.substring(0, 4));
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					if (props[0][4] != null) {
						wcrq = props[0][4].getStringValue();
						if (wcrq != null && !wcrq.isEmpty()) {
							try {
								lifeYear = Integer.parseInt(wcrq);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					if (sopYear != 0) {
						this.projectBean.setPRODUCT_LIFE(sopYear + "-"
								+ (sopYear + lifeYear - 1));
					}
					if (props[0][5] != null) {
						TCComponent projectTeam = props[0][5]
								.getReferenceValue();
						this.projectBean
								.setPROJ_MEMBER(projectMember(projectTeam));
					}

				} else {
					isOk = false;
				}
			} else {

				// return false;
				isOk = false;
			}
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return isOk;
	}

	@Override
	public void executeOperation() throws Exception {

		if (!getProjMsg()) {
			MessageBox.post("查找不到项目ID=[" + this.projID + "]的项目", "错误", 0);
			return;
		} else {
			System.out.println(this.projectBean.toString());
		}

		checkAllBomLine(0, this.topLine);
		try {
			TCProperty[][] propsList = TCComponentType.getTCPropertiesSet(
					bomlineList, JFomUtil.JF3_BOMLINE_MSG);
			// MessageBox.post("" + propsList.length, "error",
			// MessageBox.ERROR);
			for (int i = 0; i < propsList.length; i++) {
				JFomBomExportBean bean = beanList.get(i);
				bean.setWlNo(propsList[i][0].getStringValue());
				// if()
				if (propsList[i][0].getStringValue().startsWith("1.05")) {
					bean.setMPS("S");
				} else if (propsList[i][0].getStringValue().startsWith("1.06")) {
					bean.setMPS("P");
				} else {
					bean.setMPS("M");
				}
				if (propsList[i][4] != null) {
					bean.setJingzhong(propsList[i][4].getStringValue());
				}
				if (propsList[i][5] != null) {
					bean.setMaozhong(propsList[i][5].getStringValue());
				}
				// if (propsList[i][6] != null) {
				// bean.setGongyi(propsList[i][6].getStringValue());
				// }
				bean.setWlName(propsList[i][1].getStringValue());
				bean.setWlNumber(propsList[i][3].getDisplayValue() + "");
				revList.add((TCComponentItemRevision) propsList[i][2]
						.getReferenceValue());
			}
			propsList = TCComponentType.getTCPropertiesSet(revList,
					JFomUtil.JF3_REV_MSG);
			for (int i = 0; i < propsList.length; i++) {
				// 获取版本主属性表单
				TCComponent[] forms = propsList[i][0].getReferenceValueArray();
				if (forms != null && forms.length > 0) {
					formList.add((TCComponentForm) forms[0]);
				} else {
					formList.add(null);
				}
				// 获取图纸版本
				if (propsList[i][1] != null) {
					TCComponent[] revs = propsList[i][1]
							.getReferenceValueArray();
					if (revs != null && revs.length > 0) {
						drawRevList.add((TCComponentItemRevision) revs[0]);
						indexList.add(i);
						// drawRevMap.put(i, (TCComponentItemRevision) revs[0]);
					}
				}
				JFomBomExportBean bean = beanList.get(i);
				if (propsList[i][2] != null) {
					bean.setK3code(propsList[i][2].getStringValue());
				}
				if (propsList[i][3] != null) {
					bean.setName(propsList[i][3].getStringValue());
				}
				if (propsList[i][4] != null) {
					bean.setType(propsList[i][4].getStringValue());
				}
			}
			propsList = TCComponentType.getTCPropertiesSet(formList,
					JFomUtil.JF3_REV_MASTER_MSG);

			TCProperty[][] erpNameProps = TCComponentType.getTCPropertiesSet(
					formList, this.erp_java_values);
			for (int i = 0; i < propsList.length; i++) {
				JFomBomExportBean bean = beanList.get(i);
				if (propsList[i][0] != null)
					bean.setCustomerNo(propsList[i][0].getDisplayableValue());
				if (propsList[i][1] != null)
					bean.setMaterialSize(propsList[i][1].getDisplayableValue());
				if (propsList[i][2] != null)
					bean.setMaterialPark(propsList[i][2].getDisplayableValue());
				if (propsList[i][3] != null)
					bean.setGongyingshang(propsList[i][3].getDisplayableValue());
				if (propsList[i][4] != null)
					bean.setGongyi(propsList[i][4].getDisplayableValue());
				if (propsList[i][5] != null) {
					bean.setMaterialStander(propsList[i][5]
							.getDisplayableValue());
				}
				if (bean.getType().equals("JF3_YCLRevision")) {
					bean.setMaterialName(bean.getName());
					bean.setWlName(JFomErpMethodUtil.getErpName(
							erpNameProps[i], this.erp_java_values,
							this.ycl_name_values, bean.getName()));
				} else if (bean.getType().equals("JF3_CPRevision")) {
					bean.setWlName(JFomErpMethodUtil.getErpName(
							erpNameProps[i], this.erp_java_values,
							this.cp_name_values, bean.getName()));
				} else if (bean.getType().equals("JF3_BCPRevision")) {
					bean.setWlName(JFomErpMethodUtil.getErpName(
							erpNameProps[i], this.erp_java_values,
							this.bcp_name_values, bean.getName()));
				}
			}

			// 通过图片版本获取属性
			propsList = TCComponentType.getTCPropertiesSet(drawRevList,
					JFomUtil.JF3_DRAW_REV_MSG);
			// MessageBox.post("11111111111", "a", MessageBox.ERROR);
			if (propsList != null) {
				for (int i = 0; i < propsList.length; i++) {
					JFomBomExportBean bean = beanList.get(indexList.get(i));
					bean.setDrawingNo(propsList[i][0].getStringValue());
					if (propsList[i][1] != null) {
						TCComponent[] picComps = propsList[i][1]
								.getReferenceValueArray();
						for (TCComponent picComp : picComps) {
							String tempType = picComp.getType();
							if (tempType.equals(JFomUtil.JF3_DRAWING_PIC_TYPE)) {
								// 获取图片，并下载图片
								String picPath = JFomMethodUtil
										.downLoadFile(picComp);
								bean.setPicturePath(picPath);
							}
						}
					}
					TCComponent[] drawFormComps = propsList[i][2]
							.getReferenceValueArray();
					drawFormList.add((TCComponentForm) drawFormComps[0]);
				}
				// MessageBox.post("2222222222", "a", MessageBox.ERROR);
				propsList = TCComponentType.getTCPropertiesSet(drawFormList,
						JFomUtil.JF3_DRAW_REV_MASTER_MSG);
				for (int i = 0; i < propsList.length; i++) {
					JFomBomExportBean bean = beanList.get(indexList.get(i));
					if (propsList[i][0] != null)
						bean.setMaterialName(propsList[i][0].getStringValue());
					// if (propsList[i][1] != null)
					// bean.setMaterialStander(propsList[i][1]
					// .getStringValue());
					if (propsList[i][2] != null)
						bean.setWlWeight(propsList[i][2].getStringValue());

				}
			}
			//
			// printMsg();
			InputStream is = JFomBomExportOperation.class
					.getResourceAsStream(JFomUtil.JF3_EXPORT_BOM_EXCEL);
			ExcelUtil07.writeExcelBomExport(exportBomPath + "\\"
					+ JFomUtil.JF3_EXPORT_BOM_EXCEL, is, beanList,
					this.projectBean);
			//
			MessageBox.post("BOM导出完成！", "Info", MessageBox.INFORMATION);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

		}

	}
}
