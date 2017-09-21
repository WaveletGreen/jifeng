package com.connor.jifeng.plm.erp.util;

public class JFomErpUtil {

	public static final String SQL_DATE_FM = "yyyy-MM-dd HH:mm:ss";

	public static final String SQL_DATE_FM_PROJ = "yyyyMMdd";

	public static final String PROJ_TABLE_NAME = "PROJ_INFO_TABLE";// 项目中间表
	public static final String PROJ_MANAGER = "PROJ_MANAGER";// 项目经理
	public static final String PROJ_NUMBER = "PROJ_NUMBER";// 项目编号
	public static final String BATCH_ID = "BATCH_ID";// 批次
	public static final String STATUS = "STATUS";// 接受状态

	// ================================================================

	public static final String PART_TABLE_NAME = "PART_INFO_TABLE";// 物料中间表
	public static final String PART_NUMBER = "PART_NUMBER";// 物料ID
	public static final String PART_NAME = "PART_NAME";// 物料名称
	public static final String PART_REV = "PART_REV";// 物料版本
	public static final String PART_INVALID_DATE = "INVALID_DATE";// 废弃
	// public static final String SELECT_PART_SQL =
	// "SELECT PART_NUMBER FROM PART_INFO_TABLE WHERE PART_NUMBER=? AND PART_REV =? AND STATUS =0 ";
	public static final String UPDATE_PART_SQL = "UPDATE PART_INFO_TABLE SET STATUS=?,OPERATE_TIME=? WHERE PART_NUMBER=? AND PART_REV =? AND STATUS=?";

	// public static final String updateProjSQL = "UPDATE PROJ_INFO_TABLE SET ";
	// ===============================================================
	public static final String ECN_QUERY_NAME = "cust_query_ecn_by_id";
	public static final String ECN_QUERY_ID = "ID";
	public static final String ECN_QUERY_TYPE = "类型";
	public static final String ECN_TYPE = "JF3_GGSQPSD";
	public static final String ECN_TABLE_NAME = "CHANGE_TABLE";
	public static final String ECN_ID = "CHANGE_ID";
	public static final String ECN_INFO = "CHANGE_INFO";
	public static final String ECN_VALID_DATE = "VALID_DATE";
	public static final String SQL_ECN_READ = "select * from change_table t where t.change_id=? and t.status=0";
	public static final String SQL_ECN_UPDATE = "UPDATE change_table SET STATUS=?  WHERE CHANGE_ID=? AND STATUS=?";
	public static final String SQL_ECN_WRITE = "insert into change_table (change_id,change_info,batch_id,status,valid_date) values(?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'))";

	// ===================获取批次=============================================

	public static final String SQL_PATCH_ID = "SELECT batch_id_sequence.nextval FROM DUAL";

	// ====================项目中间表（项目变更）============================================
	public static final String SQL_PROJ_UPDATE = "UPDATE PROJ_INFO_TABLE SET STATUS=? ,OPERATE_TIME=? WHERE PROJ_NUMBER=? AND STATUS=?";
	public static final String SQL_PROJ_INSERT = "INSERT INTO PROJ_INFO_TABLE (PROJ_NUMBER,PROJ_CODE,PROJ_NAME,batch_id,STATUS,PROJ_MANAGER,COMMIT_TIME,ORIGINER,ERP_PLM,"
			+ "PROJ_CREATE_DATE,PROJ_NATURE,CUSTOMER_MANAGER,PLAN_START_DATE,ASSEMBLY_CUSTOMER,CUSTOMER,MODELS,PROJ_TYPE,RESP_DEPT,PLAN_FINISH_DATE,CONTENT_DESC,PROJ_START_DATE,OTS_DATE,PPAP_DATE,SOP_DATE,LIFE_DATE,ANNUAL_OUTPUT,PLAN_PROD_DATE) VALUES (?,?,?,?,0,?,?,?,?"
			+ ",to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),to_date(?,'yyyy-MM-dd HH24:mi:ss'),to_date(?,'yyyy-MM-dd HH24:mi:ss'),to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'))";

	// =======================BOM变更=========================================================
	public static final String SQL_BOM_UPDATE = "UPDATE BOM_TABLE SET STATUS = ? ,OPERATE_TIME=? WHERE P_NUMBER=? AND STATUS=? AND BATCH_ID !=?";
	public static final String SQL_BOM_INSERT = "INSERT INTO BOM_TABLE (BATCH_ID,ORIGINER,COMMIT_TIME,STATUS,P_NUMBER,SEQ_NUMBER,PART_NUMBER,QUANTITY,ASSEM_QUANTITY,OTHERS,ULLAGE,JOB_NUMBER,REPLACE_TYPE,RELEASED_DATE,VALID_DATE,INVALID_DATE,WORK_RADIO"
			+ ") "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),to_date(?,'yyyy-MM-dd HH24:mi:ss'),to_date(?,'yyyy-MM-dd HH24:mi:ss'),?"
			+ ")";

}
