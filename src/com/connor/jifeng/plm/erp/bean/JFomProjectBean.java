package com.connor.jifeng.plm.erp.bean;

import java.util.Date;

public class JFomProjectBean {
	private String KEY_CODE;

	// ��Ŀ��� project_id
	private String PROJ_NUMBER;
	// ��Ŀ���� object_name
	private String PROJ_CODE;
	// ��Ŀ���� project_desc
	private String PROJ_NAME;
	// ��Ŀ�������� jf3_Xmjlrq
	private Date PROJ_CREATE_DATE;
	// ��Ŀ���� jf3_Xmxz
	private String PROJ_NATURE;
	// �ͻ����� jf3_Khjl
	private String CUSTOMER_MANAGER;
	// Ԥ�ƿ�ʼ���� jf3_Yjksrq
	private Date PLAN_START_DATE;
	// ������ jf3_Zjc
	private String ASSEMBLY_CUSTOMER;
	// ֱ�ӿͻ� jf3_Zjkh
	private String CUSTOMER;
	// ���� jf3_Cx
	private String MODELS;
	// ��Ŀ���� jf3_Xmlx
	private String PROJ_TYPE;
	// ������ jf3_Fzbm
	private String RESP_DEPT;
	// Ԥ��������� jf3_Yjwcrq
	private Date PLAN_FINISH_DATE;
	// ����˵�� jf3_Nrsm
	private String CONTENT_DESC;
	// ��Ŀ���� jf3_Xmjl
	private String PROJ_MANAGER;
	// ��Ŀ�������� jf3_Xmqdrq
	private Date PROJ_START_DATE;
	// OTS�����ṩ jf3_OTSyjtg
	private Date OTS_DATE;
	// PPAP jf3_PPAP
	private Date PPAP_DATE;
	// SOP�������� jf3_SOPplsc
	private Date SOP_DATE;
	// �������ڣ��꣩jf3_Smzq
	private String LIFE_DATE;
	// Ԥ������� jf3_Ycncl
	private String ANNUAL_OUTPUT;
	// �����滮ʱ�� jf3_Lcghsj
	private Date PLAN_PROD_DATE;
	// ����
	private Integer BATCH_ID;
	// ����״̬
	private Integer STATUS;
	// ��ϵͳ���Ƿ�����½�
	private Boolean createStatus = false;

	// 2016-10-18����
	// ��Ŀ������
	private String PROJ_ENGINEER = "";
	// ǰ����������ʦ
	private String PROJ_SQE = "";
	// ��Ŀ��Ҫ��
	private String PROJ_IMPORTANCE = "";
	// ������
	private String PROJ_PLACE = "";

	// ********************************************************

	public String getPROJ_NUMBER() {
		return PROJ_NUMBER;
	}

	public String getPROJ_ENGINEER() {
		return PROJ_ENGINEER;
	}

	public void setPROJ_ENGINEER(String pROJ_ENGINEER) {
		PROJ_ENGINEER = pROJ_ENGINEER;
	}

	public String getPROJ_SQE() {
		return PROJ_SQE;
	}

	public void setPROJ_SQE(String pROJ_SQE) {
		PROJ_SQE = pROJ_SQE;
	}

	public String getPROJ_IMPORTANCE() {
		return PROJ_IMPORTANCE;
	}

	public void setPROJ_IMPORTANCE(String pROJ_IMPORTANCE) {
		PROJ_IMPORTANCE = pROJ_IMPORTANCE;
	}

	public String getPROJ_PLACE() {
		return PROJ_PLACE;
	}

	public void setPROJ_PLACE(String pROJ_PLACE) {
		PROJ_PLACE = pROJ_PLACE;
	}

	public void setPROJ_NUMBER(String pROJ_NUMBER) {
		PROJ_NUMBER = pROJ_NUMBER;
	}

	public String getPROJ_CODE() {
		return PROJ_CODE;
	}

	public void setPROJ_CODE(String pROJ_CODE) {
		PROJ_CODE = pROJ_CODE;
	}

	public String getPROJ_NAME() {
		return PROJ_NAME;
	}

	public void setPROJ_NAME(String pROJ_NAME) {
		PROJ_NAME = pROJ_NAME;
	}

	public Date getPROJ_CREATE_DATE() {
		return PROJ_CREATE_DATE;
	}

	public void setPROJ_CREATE_DATE(Date pROJ_CREATE_DATE) {
		PROJ_CREATE_DATE = pROJ_CREATE_DATE;
	}

	public String getPROJ_NATURE() {
		return PROJ_NATURE;
	}

	public void setPROJ_NATURE(String pROJ_NATURE) {
		PROJ_NATURE = pROJ_NATURE;
	}

	public String getCUSTOMER_MANAGER() {
		return CUSTOMER_MANAGER;
	}

	public void setCUSTOMER_MANAGER(String cUSTOMER_MANAGER) {
		CUSTOMER_MANAGER = cUSTOMER_MANAGER;
	}

	public Date getPLAN_START_DATE() {
		return PLAN_START_DATE;
	}

	public void setPLAN_START_DATE(Date pLAN_START_DATE) {
		PLAN_START_DATE = pLAN_START_DATE;
	}

	public String getASSEMBLY_CUSTOMER() {
		return ASSEMBLY_CUSTOMER;
	}

	public void setASSEMBLY_CUSTOMER(String aSSEMBLY_CUSTOMER) {
		ASSEMBLY_CUSTOMER = aSSEMBLY_CUSTOMER;
	}

	public String getCUSTOMER() {
		return CUSTOMER;
	}

	public void setCUSTOMER(String cUSTOMER) {
		CUSTOMER = cUSTOMER;
	}

	public String getMODELS() {
		return MODELS;
	}

	public void setMODELS(String mODELS) {
		MODELS = mODELS;
	}

	public String getPROJ_TYPE() {
		return PROJ_TYPE;
	}

	public void setPROJ_TYPE(String pROJ_TYPE) {
		PROJ_TYPE = pROJ_TYPE;
	}

	public String getRESP_DEPT() {
		return RESP_DEPT;
	}

	public void setRESP_DEPT(String rESP_DEPT) {
		RESP_DEPT = rESP_DEPT;
	}

	public Date getPLAN_FINISH_DATE() {
		return PLAN_FINISH_DATE;
	}

	public void setPLAN_FINISH_DATE(Date pLAN_FINISH_DATE) {
		PLAN_FINISH_DATE = pLAN_FINISH_DATE;
	}

	public String getCONTENT_DESC() {
		return CONTENT_DESC;
	}

	public void setCONTENT_DESC(String cONTENT_DESC) {
		CONTENT_DESC = cONTENT_DESC;
	}

	public String getPROJ_MANAGER() {
		return PROJ_MANAGER;
	}

	public void setPROJ_MANAGER(String pROJ_MANAGER) {
		PROJ_MANAGER = pROJ_MANAGER;
	}

	public Date getPROJ_START_DATE() {
		return PROJ_START_DATE;
	}

	public void setPROJ_START_DATE(Date pROJ_START_DATE) {
		PROJ_START_DATE = pROJ_START_DATE;
	}

	public Date getOTS_DATE() {
		return OTS_DATE;
	}

	public void setOTS_DATE(Date oTS_DATE) {
		OTS_DATE = oTS_DATE;
	}

	public Date getPPAP_DATE() {
		return PPAP_DATE;
	}

	public void setPPAP_DATE(Date pPAP_DATE) {
		PPAP_DATE = pPAP_DATE;
	}

	public Date getSOP_DATE() {
		return SOP_DATE;
	}

	public void setSOP_DATE(Date sOP_DATE) {
		SOP_DATE = sOP_DATE;
	}

	public String getLIFE_DATE() {
		return LIFE_DATE;
	}

	public void setLIFE_DATE(String lIFE_DATE) {
		LIFE_DATE = lIFE_DATE;
	}

	public String getANNUAL_OUTPUT() {
		return ANNUAL_OUTPUT;
	}

	public void setANNUAL_OUTPUT(String aNNUAL_OUTPUT) {
		ANNUAL_OUTPUT = aNNUAL_OUTPUT;
	}

	public Date getPLAN_PROD_DATE() {
		return PLAN_PROD_DATE;
	}

	public void setPLAN_PROD_DATE(Date pLAN_PROD_DATE) {
		PLAN_PROD_DATE = pLAN_PROD_DATE;
	}

	public Integer getBATCH_ID() {
		return BATCH_ID;
	}

	public void setBATCH_ID(Integer bATCH_ID) {
		BATCH_ID = bATCH_ID;
	}

	public Integer getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}

	public Boolean getCreateStatus() {
		return createStatus;
	}

	public void setCreateStatus(Boolean createStatus) {
		this.createStatus = createStatus;
	}

	@Override
	public String toString() {
		return "JFomProjectBean [PROJ_NUMBER=" + PROJ_NUMBER + ", PROJ_CODE="
				+ PROJ_CODE + ", PROJ_NAME=" + PROJ_NAME
				+ ", PROJ_CREATE_DATE=" + PROJ_CREATE_DATE + ", PROJ_NATURE="
				+ PROJ_NATURE + ", CUSTOMER_MANAGER=" + CUSTOMER_MANAGER
				+ ", PLAN_START_DATE=" + PLAN_START_DATE
				+ ", ASSEMBLY_CUSTOMER=" + ASSEMBLY_CUSTOMER + ", CUSTOMER="
				+ CUSTOMER + ", MODELS=" + MODELS + ", PROJ_TYPE=" + PROJ_TYPE
				+ ", RESP_DEPT=" + RESP_DEPT + ", PLAN_FINISH_DATE="
				+ PLAN_FINISH_DATE + ", CONTENT_DESC=" + CONTENT_DESC
				+ ", PROJ_MANAGER=" + PROJ_MANAGER + ", PROJ_START_DATE="
				+ PROJ_START_DATE + ", OTS_DATE=" + OTS_DATE + ", PPAP_DATE="
				+ PPAP_DATE + ", SOP_DATE=" + SOP_DATE + ", LIFE_DATE="
				+ LIFE_DATE + ", ANNUAL_OUTPUT=" + ANNUAL_OUTPUT
				+ ", PLAN_PROD_DATE=" + PLAN_PROD_DATE + ", BATCH_ID="
				+ BATCH_ID + ", STATUS=" + STATUS + ", createStatus="
				+ createStatus + ", getPROJ_NUMBER()=" + getPROJ_NUMBER()
				+ ", getPROJ_CODE()=" + getPROJ_CODE() + ", getPROJ_NAME()="
				+ getPROJ_NAME() + ", getPROJ_CREATE_DATE()="
				+ getPROJ_CREATE_DATE() + ", getPROJ_NATURE()="
				+ getPROJ_NATURE() + ", getCUSTOMER_MANAGER()="
				+ getCUSTOMER_MANAGER() + ", getPLAN_START_DATE()="
				+ getPLAN_START_DATE() + ", getASSEMBLY_CUSTOMER()="
				+ getASSEMBLY_CUSTOMER() + ", getCUSTOMER()=" + getCUSTOMER()
				+ ", getMODELS()=" + getMODELS() + ", getPROJ_TYPE()="
				+ getPROJ_TYPE() + ", getRESP_DEPT()=" + getRESP_DEPT()
				+ ", getPLAN_FINISH_DATE()=" + getPLAN_FINISH_DATE()
				+ ", getCONTENT_DESC()=" + getCONTENT_DESC()
				+ ", getPROJ_MANAGER()=" + getPROJ_MANAGER()
				+ ", getPROJ_START_DATE()=" + getPROJ_START_DATE()
				+ ", getOTS_DATE()=" + getOTS_DATE() + ", getPPAP_DATE()="
				+ getPPAP_DATE() + ", getSOP_DATE()=" + getSOP_DATE()
				+ ", getLIFE_DATE()=" + getLIFE_DATE()
				+ ", getANNUAL_OUTPUT()=" + getANNUAL_OUTPUT()
				+ ", getPLAN_PROD_DATE()=" + getPLAN_PROD_DATE()
				+ ", getBATCH_ID()=" + getBATCH_ID() + ", getSTATUS()="
				+ getSTATUS() + ", getCreateStatus()=" + getCreateStatus()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	// ********************************************************************

}
