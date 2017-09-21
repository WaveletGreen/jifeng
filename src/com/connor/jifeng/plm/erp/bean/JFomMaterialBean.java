package com.connor.jifeng.plm.erp.bean;

import java.util.Date;

public class JFomMaterialBean {
	// �����
	private String PART_NAMBER;
	// ����
	private String PART_NAME;
	// �汾
	private String PART_REV;
	// �ͻ������
	private String CUSTOMER_PART_NUMBER;
	// ���
	private String PART_SPEC;
	// ��Դ
	private String SOURCE = "M";
	// ����
	private String MATERIAL;
	// ��ɫ
	private String COLOR;
	// ����
	private String FABRIC;
	// Ƥ��
	private String PATTERN;
	// ���洦��
	private String SURFACE_TREATMENT;
	// �ƺ�
	private String GRADE;
	// �Ƿ��׼��
	private String IS_STANDARD = "N";
	// ����
	private int BATCH_ID;
	// ����״̬
	private int STATUS;
	// ����״̬
	private String RELEASED_STATUS = "N";
	// ʧЧ����
	private Date INVALID_DATE;

	private String CREATER;

	private Date CREATE_DATE;

	private String UNIT;

	private String PROJ_NAME;

	private String PROJ_CODE;

	private String CLASS_CODE;

	private String SHARE_USE;
	private String SHARE_MEMO;

	// -----------------------------------------------------

	public String getCLASS_CODE() {
		return CLASS_CODE;
	}

	public String getSHARE_USE() {
		return SHARE_USE;
	}

	public void setSHARE_USE(String sHARE_USE) {
		SHARE_USE = sHARE_USE;
	}

	public String getSHARE_MEMO() {
		return SHARE_MEMO;
	}

	public void setSHARE_MEMO(String sHARE_MEMO) {
		SHARE_MEMO = sHARE_MEMO;
	}

	public void setCLASS_CODE(String cLASS_CODE) {
		CLASS_CODE = cLASS_CODE;
	}

	public String getPROJ_NAME() {
		return PROJ_NAME;
	}

	public void setPROJ_NAME(String pROJ_NAME) {
		PROJ_NAME = pROJ_NAME;
	}

	public String getPROJ_CODE() {
		return PROJ_CODE;
	}

	public void setPROJ_CODE(String pROJ_CODE) {
		PROJ_CODE = pROJ_CODE;
	}

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	public String getCREATER() {
		return CREATER;
	}

	public void setCREATER(String cREATER) {
		CREATER = cREATER;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getPART_NAMBER() {
		return PART_NAMBER;
	}

	public void setPART_NAMBER(String pART_NAMBER) {
		PART_NAMBER = pART_NAMBER;
	}

	public String getPART_NAME() {
		return PART_NAME;
	}

	public void setPART_NAME(String pART_NAME) {
		PART_NAME = pART_NAME;
	}

	public String getPART_REV() {
		return PART_REV;
	}

	public void setPART_REV(String pART_REV) {
		PART_REV = pART_REV;
	}

	public String getCUSTOMER_PART_NUMBER() {
		return CUSTOMER_PART_NUMBER;
	}

	public void setCUSTOMER_PART_NUMBER(String cUSTOMER_PART_NUMBER) {
		CUSTOMER_PART_NUMBER = cUSTOMER_PART_NUMBER;
	}

	public String getPART_SPEC() {
		return PART_SPEC;
	}

	public void setPART_SPEC(String pART_SPEC) {
		PART_SPEC = pART_SPEC;
	}

	public String getSOURCE() {
		return SOURCE;
	}

	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}

	public String getMATERIAL() {
		return MATERIAL;
	}

	public void setMATERIAL(String mATERIAL) {
		MATERIAL = mATERIAL;
	}

	public String getCOLOR() {
		return COLOR;
	}

	public void setCOLOR(String cOLOR) {
		COLOR = cOLOR;
	}

	public String getFABRIC() {
		return FABRIC;
	}

	public void setFABRIC(String fABRIC) {
		FABRIC = fABRIC;
	}

	public String getPATTERN() {
		return PATTERN;
	}

	public void setPATTERN(String pATTERN) {
		PATTERN = pATTERN;
	}

	public String getSURFACE_TREATMENT() {
		return SURFACE_TREATMENT;
	}

	public void setSURFACE_TREATMENT(String sURFACE_TREATMENT) {
		SURFACE_TREATMENT = sURFACE_TREATMENT;
	}

	public String getGRADE() {
		return GRADE;
	}

	public void setGRADE(String gRADE) {
		GRADE = gRADE;
	}

	public String getIS_STANDARD() {
		return IS_STANDARD;
	}

	public void setIS_STANDARD(String iS_STANDARD) {
		IS_STANDARD = iS_STANDARD;
	}

	public int getBATCH_ID() {
		return BATCH_ID;
	}

	public void setBATCH_ID(int bATCH_ID) {
		BATCH_ID = bATCH_ID;
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}

	public String getRELEASED_STATUS() {
		return RELEASED_STATUS;
	}

	public void setRELEASED_STATUS(String rELEASED_STATUS) {
		RELEASED_STATUS = rELEASED_STATUS;
	}

	public Date getINVALID_DATE() {
		return INVALID_DATE;
	}

	public void setINVALID_DATE(Date iNVALID_DATE) {
		INVALID_DATE = iNVALID_DATE;
	}

}
