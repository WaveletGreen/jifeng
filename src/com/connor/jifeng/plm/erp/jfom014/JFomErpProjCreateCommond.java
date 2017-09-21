package com.connor.jifeng.plm.erp.jfom014;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.connor.jifeng.plm.erp.bean.JFomProjectBean;
import com.connor.jifeng.plm.erp.util.JFomErpUtil;
import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.SqlUtil;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentProject;
import com.teamcenter.rac.kernel.TCComponentProjectType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCComponentUserType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.Registry;

public class JFomErpProjCreateCommond extends AbstractAIFCommand {
	private final AbstractAIFUIApplication app;
	private final TCSession session;
	private TCComponentUser user;
	private final List<JFomProjectBean> projBeanList;
	private final Registry reg = Registry
			.getRegistry(JFomErpProjCreateCommond.class);

	private SimpleDateFormat sdf;
	private SimpleDateFormat sdf2;

	private HashMap<String, String> projXMXZMap;
	private HashMap<String, String> projKHJLMap;
	private HashMap<String, String> projXMLXMap;
	private HashMap<String, String> projXMJLMap;
	private String[] projMembers;

	public JFomErpProjCreateCommond(AbstractAIFUIApplication app,
			TCSession session) {
		this.app = app;
		this.session = session;
		this.projBeanList = new ArrayList<JFomProjectBean>();
		this.sdf = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM);
		this.sdf2 = new SimpleDateFormat(JFomErpUtil.SQL_DATE_FM_PROJ);
		getProjLovMapping();
		// execCommond();
	}

	public void getProjLovMapping() {
		projXMXZMap = JFomMethodUtil.getPrefStrArrayReturn(
				"Cust_ERP_PLM_PROJ_XMXZ", "@");
		projKHJLMap = JFomMethodUtil.getPrefStrArrayReturn(
				"Cust_ERP_PLM_PROJ_KHJL", "@");
		projXMLXMap = JFomMethodUtil.getPrefStrArrayReturn(
				"Cust_ERP_PLM_PROJ_XMLX", "@");
		projXMJLMap = JFomMethodUtil.getPrefStrArray("Cust_ERP_PLM_Proj_XMJL",
				"@");
		projMembers = JFomMethodUtil.getPrefStrArray("Cust_ERP_PROJ_MEMBERS");
	}

	@Override
	public void executeModal() throws Exception {
		execCommond();

		createProj();

		super.executeModal();
	}

	public void createProj() throws TCException {
		if (projBeanList == null || projBeanList.size() == 0) {
			MessageBox.post("当前没有需要创建的项目", "提示", MessageBox.INFORMATION);
			return;
		}
		List<TCComponent> members = new ArrayList<>();
		TCComponentUserType userType = (TCComponentUserType) session
				.getTypeComponent("User");
		if (userType != null && projMembers != null) {
			for (String str : projMembers) {

				TCComponentUser userT = userType.find(str);
				if (userT != null) {
					members.add(userT);
				}
			}
		}
		// InterfaceAIFComponent comp = this.app.getTargetComponent();
		// comp.getType();
		TCComponentProjectType projecttype = (TCComponentProjectType) session
				.getTypeComponent("TC_Project");
		StringBuffer successSb = new StringBuffer();
		String updateProjSql = SqlUtil.getUpdataSQL(
				JFomErpUtil.PROJ_TABLE_NAME, new String[] { JFomErpUtil.STATUS,
						"OPERATE_TIME" }, new String[] {
						JFomErpUtil.PROJ_NUMBER, JFomErpUtil.BATCH_ID });
		String commiteDate = this.sdf.format(new Date());
		SqlUtil.getConnection();
		try {
			if (projecttype != null) {
				for (JFomProjectBean bean : projBeanList) {

					TCComponentProject proj = projecttype.find(bean
							.getPROJ_NUMBER());
					if (proj == null) {

						// 创建项目
						try {
							members.add(user);
							proj = projecttype.create(bean.getPROJ_NUMBER(),
									bean.getPROJ_CODE(), bean.getPROJ_NAME(),
									members.toArray(new TCComponent[members
											.size()]), user,
									new TCComponentUser[] {});// new
							// TCComponent[]
							// {
							// user
							// }
							proj.lock();
							boolean isOk = setProjProps(proj, bean);
							proj.save();
							proj.unlock();
							proj.refresh();
							if (isOk) {
								successSb.append("项目ID=[")
										.append(bean.getPROJ_NUMBER())
										.append("]创建成功\r\n");
								SqlUtil.update(updateProjSql, new Object[] { 1,
										commiteDate, bean.getPROJ_NUMBER(),
										bean.getBATCH_ID() });
							} else {
								successSb.append("项目ID=[")
										.append(bean.getPROJ_NUMBER())
										.append("]创建成功,但属性更新失败，请重新执行\r\n");
							}
						} catch (Exception e) {
							// 项目创建失败
							e.printStackTrace();
							successSb.append("项目ID=[")
									.append(bean.getPROJ_NUMBER())
									.append("]创建失败\r\n");
						}

					} else {
						// TODO 更新项目属性 添加更新项目属性的逻辑

						successSb.append("项目ID=[")
								.append(bean.getPROJ_NUMBER())
								.append("]已经存在！\r\n");
						SqlUtil.update(
								updateProjSql,
								new Object[] { 1, commiteDate,
										bean.getPROJ_NUMBER(),
										bean.getBATCH_ID() });
					}
				}
				MessageBox.post("项目创建结束\r\n" + successSb.toString(), "项目创建结束",
						MessageBox.INFORMATION);
			} else {
				MessageBox.post("创建项目失败，请重新创建！", "创建项目失败", MessageBox.ERROR);
			}
		} catch (Exception e) {
			MessageBox.post("创建项目失败，请重新创建！", "创建项目失败", MessageBox.ERROR);
			e.printStackTrace();
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

	/**
	 * "jf3_xmjlrq1", "jf3_xmxz1", "jf3_khjl1", "jf3_yjksrq1", "jf3_zjc1",
	 * "jf3_zjkh1", "jf3_cx1", "jf3_xmlx1", "jf3_fzbm1", "jf3_yjwcrq1",
	 * "jf3_nrsm1", "jf3_xmjl1", "jf3_xmqdrq1", "jf3_OTSyjtg1", "jf3_PPAP1",
	 * "jf3_SOPplsc1", "jf3_smzq1", "jf3_ycncl1", "jf3_lcghsj1"
	 */
	public boolean setProjProps(TCComponentProject proj, JFomProjectBean bean) {
		boolean isOk = true;
		HashMap<String, String> propMp = new HashMap<String, String>();
		if (bean.getPROJ_CREATE_DATE() != null)

			propMp.put("jf3_xmjlrq1", sdf2.format(bean.getPROJ_CREATE_DATE()));

		if (this.projXMXZMap.containsKey(bean.getPROJ_NATURE()))
			propMp.put("jf3_xmxz1", this.projXMXZMap.get(bean.getPROJ_NATURE()));
		else
			propMp.put("jf3_xmxz1",
					bean.getPROJ_NATURE() == null ? "" : bean.getPROJ_NATURE());
		if (this.projKHJLMap.containsKey(bean.getCUSTOMER_MANAGER()))
			propMp.put("jf3_khjl1",
					this.projKHJLMap.get(bean.getCUSTOMER_MANAGER()));
		else
			propMp.put("jf3_khjl1", bean.getCUSTOMER_MANAGER() == null ? ""
					: bean.getCUSTOMER_MANAGER());
		if (bean.getPLAN_START_DATE() != null)

			propMp.put("jf3_yjksrq1", sdf2.format(bean.getPLAN_START_DATE()));

		propMp.put(
				"jf3_zjc1",
				bean.getASSEMBLY_CUSTOMER() == null ? "" : bean
						.getASSEMBLY_CUSTOMER());

		propMp.put("jf3_zjkh1",
				bean.getCUSTOMER() == null ? "" : bean.getCUSTOMER());
		propMp.put("jf3_cx1", bean.getMODELS() == null ? "" : bean.getMODELS());
		if (this.projXMLXMap.containsKey(bean.getPROJ_TYPE()))
			propMp.put("jf3_xmlx1", this.projXMLXMap.get(bean.getPROJ_TYPE()));
		else
			propMp.put("jf3_xmlx1",
					bean.getPROJ_TYPE() == null ? "" : bean.getPROJ_TYPE());

		propMp.put("jf3_fzbm1",
				bean.getRESP_DEPT() == null ? "" : bean.getRESP_DEPT());
		if (bean.getPLAN_FINISH_DATE() != null)

			propMp.put("jf3_yjwcrq1", sdf2.format(bean.getPLAN_FINISH_DATE()));

		propMp.put("jf3_nrsm1",
				bean.getCONTENT_DESC() == null ? "" : bean.getCONTENT_DESC());
		propMp.put("jf3_xmjl1",
				bean.getPROJ_MANAGER() == null ? "" : bean.getPROJ_MANAGER());
		if (bean.getPROJ_START_DATE() != null)

			propMp.put("jf3_xmqdrq1", sdf2.format(bean.getPROJ_START_DATE()));
		if (bean.getOTS_DATE() != null)

			propMp.put("jf3_OTSyjtg1", sdf2.format(bean.getOTS_DATE()));
		if (bean.getPPAP_DATE() != null)

			propMp.put("jf3_PPAP1", sdf2.format(bean.getPPAP_DATE()));
		if (bean.getSOP_DATE() != null)

			propMp.put("jf3_SOPplsc1", sdf2.format(bean.getSOP_DATE()));
		propMp.put("jf3_smzq1",
				bean.getLIFE_DATE() == null ? "" : bean.getLIFE_DATE());
		// =============
		propMp.put("jf3_Lcghd1",
				bean.getPROJ_PLACE() == null ? "" : bean.getPROJ_PLACE());
		propMp.put("jf3_Xmfzr1",
				bean.getPROJ_ENGINEER() == null ? "" : bean.getPROJ_ENGINEER());
		propMp.put("jf3_Qqzlgcs1",
				bean.getPROJ_SQE() == null ? "" : bean.getPROJ_SQE());
		propMp.put("jf3_Xmzyd1",
				bean.getLIFE_DATE() == null ? "" : bean.getLIFE_DATE());

		propMp.put("jf3_ycncl1",
				bean.getANNUAL_OUTPUT() == null ? "" : bean.getANNUAL_OUTPUT());
		if (bean.getPLAN_PROD_DATE() != null)
			propMp.put("jf3_lcghsj1", sdf2.format(bean.getPLAN_PROD_DATE()));

		try {
			proj.setProperties(propMp);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isOk = false;
		}
		return isOk;
	}

	/**
	 * 将数据库查询到的数据转换为需要的数据
	 * 
	 * @param resultSet
	 */
	public void exchangeReslutToBeanMsg(ResultSet resultSet) {
		try {
			if (resultSet != null)
				while (resultSet.next()) {
					JFomProjectBean bean = new JFomProjectBean();
					bean.setPROJ_NUMBER(resultSet.getString(1));
					bean.setPROJ_CODE(resultSet.getString(2));
					bean.setPROJ_NAME(resultSet.getString(3));
					bean.setPROJ_CREATE_DATE(resultSet.getDate(4));
					bean.setPROJ_NATURE(resultSet.getString(5));
					bean.setCUSTOMER_MANAGER(resultSet.getString(6));
					bean.setPLAN_START_DATE(resultSet.getDate(7));
					bean.setASSEMBLY_CUSTOMER(resultSet.getString(8));
					bean.setCUSTOMER(resultSet.getString(9));
					bean.setMODELS(resultSet.getString(10));
					bean.setPROJ_TYPE(resultSet.getString(11));
					bean.setRESP_DEPT(resultSet.getString(12));
					bean.setPLAN_FINISH_DATE(resultSet.getDate(13));
					bean.setCONTENT_DESC(resultSet.getString(14));
					bean.setPROJ_MANAGER(resultSet.getString(15));
					bean.setPROJ_START_DATE(resultSet.getDate(16));
					bean.setOTS_DATE(resultSet.getDate(17));
					bean.setPPAP_DATE(resultSet.getDate(18));
					bean.setSOP_DATE(resultSet.getDate(19));
					bean.setLIFE_DATE(resultSet.getInt(20) + "");
					bean.setANNUAL_OUTPUT(resultSet.getInt(21) + "");
					bean.setPLAN_PROD_DATE(resultSet.getDate(22));
					bean.setBATCH_ID(resultSet.getInt(23));
					bean.setSTATUS(resultSet.getInt(24));
					// 2016-10-18
					bean.setPROJ_ENGINEER(resultSet.getString(25));
					bean.setPROJ_SQE(resultSet.getString(26));
					bean.setPROJ_IMPORTANCE(resultSet.getString(27));
					bean.setPROJ_PLACE(resultSet.getString(28));

					projBeanList.add(bean);

				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 执行的具体逻辑
	 */
	public void execCommond() {
		this.user = this.session.getUser();

		if (user != null) {
			try {
				String userId = user.getUserId();
				String[] userIds = null;
				if (this.projXMJLMap.containsKey(userId)) {

					String userIdTemp = this.projXMJLMap.get(userId);
					userIds = userIdTemp.split("\\|");

				}
				String dbPropStr = reg.getString("DB_PROPS");
				if (dbPropStr != null) {
					String[] dbProps = dbPropStr.split(",");
					String sqlStr = SqlUtil.getSelectSql(
							JFomErpUtil.PROJ_TABLE_NAME, dbProps,
							JFomErpUtil.PROJ_MANAGER, JFomErpUtil.STATUS,
							"ERP_PLM");
					System.out.println("SQL : =>" + sqlStr);
					// 连接数据库
					SqlUtil.getConnection();
					try {
						if (userIds == null) {
							java.sql.ResultSet set = SqlUtil.read(sqlStr,
									new Object[] { userId, 0, "ERP" });
							exchangeReslutToBeanMsg(set);
						} else {
							for (String user : userIds) {
								java.sql.ResultSet set = SqlUtil.read(sqlStr,
										new Object[] { user, 0, "ERP" });
								exchangeReslutToBeanMsg(set);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						// 释放数据库资源
						try {
							SqlUtil.connection.commit();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SqlUtil.free();
					}
				}

			} catch (TCException e) {
				e.printStackTrace();
			}

		} else {
			MessageBox.post("没有找到当前登录的人员！", "Error", MessageBox.ERROR);
		}
	}
}
