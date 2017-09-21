package com.connor.jifeng.plm.erp.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.connor.jifeng.plm.util.JFomMethodUtil;
import com.connor.jifeng.plm.util.SqlUtil;
import com.teamcenter.rac.kernel.TCProperty;

public class JFomErpMethodUtil {

	public static void main(String[] args) {
		// getPatchIdStr();
		String str = "1234567";

		System.out.println(str.substring(1, 3));
	}

	private static HashMap<String, String> codeMap;
	private static List<Integer> indexLIST;
	static {
		if (codeMap == null) {
			codeMap = JFomMethodUtil.getPrefStrArray(
					"Cust_ERP_PLM_Class_Code_Mapping", "@");
		}
		if (indexLIST == null) {
			indexLIST = JFomMethodUtil
					.getPrefIntArray("Cust_ERP_PLM_Class_Code_Mapping_Index");
		}
	}

	/**
	 * 获取ERP的分群码
	 * 
	 * @param itemId
	 * @return
	 */
	public static String getErpClassCode(String itemId) {
		String erpCode = null;
		if (indexLIST == null || codeMap == null) {
			return null;
		}
		for (int index : indexLIST) {
			try {
				String indexStr = itemId.substring(0, index);
				if (codeMap.containsKey(indexStr)) {
					erpCode = codeMap.get(indexStr);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return erpCode;

	}

	/**
	 * 拼接ERP名称
	 * 
	 * @param name
	 * @return
	 */
	/*
	 * public static String getErpName(String... names) { if (names == null) {
	 * return " ";
	 * 
	 * } String erpName = null;
	 * 
	 * StringBuffer nameSb = new StringBuffer(); for (int i = 0; i <
	 * names.length; i++) { if (names[i] != null && !names[i].isEmpty()) { if
	 * (nameSb.toString().isEmpty()) { nameSb.append(names[i]); } else {
	 * nameSb.append("\\/").append(names[i]); } } } return erpName; }
	 */

	/**
	 * 获取批次号
	 * 
	 * @return
	 */
	public static String getPatchIdStr() {
		String patchIDStr = null;
		try {
			SqlUtil.getConnection();
			ResultSet set = SqlUtil.read(JFomErpUtil.SQL_PATCH_ID);
			while (set.next()) {
				patchIDStr = set.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlUtil.free();
		}
		return patchIDStr;
	}

	/**
	 * 拼接传递erp的品名
	 * 
	 * @param props
	 * @param propNameList
	 * @param name
	 * @return
	 */
	public static String getErpName(TCProperty[] props, String[] propNameLists,
			String[] names, String objName) {
		StringBuffer erpNameSb = new StringBuffer();
		List<String> propNameList = new ArrayList<>();
		for (String name : propNameLists) {
			propNameList.add(name);
			System.out.println(" NAME1 =" + name);
		}
		if (names != null) {
			for (int i = 0; i < names.length; i++) {
				System.out.println(" NAME2 =" + names[i]);
				if (propNameList.contains(names[i])) {
					int index = propNameList.indexOf(names[i]);
					System.out
							.println(" PROP1 =" + props[index] == null ? "NULL"
									: props[index].getDisplayableValue());
					// 如果取object_name那么需要做特殊处理
					if (names[i].equals("object_name")) {
						if (!erpNameSb.toString().isEmpty()) {
							erpNameSb.append("/").append(objName);
						} else {
							erpNameSb.append(objName);
						}
					} else if (props[index] != null
							&& props[index].getDisplayableValue() != null
							&& !props[index].getDisplayableValue().isEmpty()) {

						if (!erpNameSb.toString().isEmpty()) {
							erpNameSb.append("/").append(
									props[index].getDisplayableValue());
						} else {
							erpNameSb
									.append(props[index].getDisplayableValue());
						}
					}
				}
			}
		}
		// String erpName = erpNameSb.toString();
		System.out.println(" NAME3 =" + erpNameSb.toString());
		return erpNameSb.toString();
	}
}
