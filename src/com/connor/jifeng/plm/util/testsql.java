package com.connor.jifeng.plm.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class testsql {
	public static void main2(String[] args) {
		// String dbprops =
		// "PROJ_NUMBER,PROJ_CODE,PROJ_NAME,PROJ_CREATE_DATE,PROJ_NATURE,CUSTOMER_MANAGER,PLAN_START_DATE,ASSEMBLY_CUSTOMER,CUSTOMER,MODELS,PROJ_TYPE,RESP_DEPT,PLAN_FINISH_DATE,CONTENT_DESC,PROJ_MANAGER,PROJ_START_DATE,OTS_DATE,PPAP_DATE,SOP_DATE,LIFE_DATE,ANNUAL_OUTPUT,PLAN_PROD_DATE,BATCH_ID,STATUS";
		// String[] strs = dbprops.split(",");
		// System.out.println(strs.length);
		// IMG01 物料编码，IMA02物料名称，IMA021规格，SUM_IMG10总量，IMG02仓库代码，IMD02数量
		String sql = "select IMA02,IMA021 ,SUM_IMG10,IMD02 from v_stock_plm where IMG01=?";// SqlUtilT.getSelectSql("PROJ_INFO_TABLE",
		// strs, "PROJ_MANAGER");
		System.out.println(sql);
		SqlUtilT.getConnection("jdbc:oracle:thin:@192.168.1.248:1521:TOPPROD",
				"jfgroup", "jfgroup");
		try {
			ResultSet set = SqlUtilT.read(sql, new String[] { "1.01.01.002" });
			if (set != null)
				while (set.next()) {
					String count = set.getString(3);
					// Float.parseFloat(count);
					System.out.println(Float.parseFloat(count));

					// System.out.println(set.last());
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlUtilT.free();

		String tt = "123.0";
		System.out.println(tt.substring(0, tt.lastIndexOf(".0")));
	}

	public static void main(String[] args) {
		SqlUtilT.getConnection("jdbc:oracle:thin:@192.168.31.122:1521:wtbtc",
				"tc10", "infodba");

		// MessageBox.post("bbbbbb","kkkkk",1);

		try {
			// MessageBox.post("dddddd","kkkkk",1);
			String sqlStr = "SELECT * FROM PROJ_INFO_TABLE";
			ResultSet set = SqlUtilT.read(sqlStr);
			System.out.println("11111111111");
			while (set.next()) {
				System.out.println("22222  => " + set.getString(1));
			}
			// MessageBox.post("ccccccc","kkkkk",1);
			// exchangeReslutToBeanMsg(set);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// MessageBox.post(e.getMessage(),"kkkkk",1);
		} finally {
			// 释放数据库资源
			SqlUtilT.free();
			// MessageBox.post("eeeeeee","kkkkk",1);
		}

	}

}
