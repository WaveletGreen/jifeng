package com.connor.jifeng.plm.erp.bean;

public class JFomBomBean {
	private int SEQ_NUMBER;// ���
	private String P_NUMBER;// �����ϱ���
	private String RELEASED_DATE;// �������
	private String PART_NUMBER;// ���ϱ���
	private String VALID_DATE;// �������ڡ�����ǰ
	private String INVALID_DATE;// ʧЧ����
	private String QUANTITY = "1";// �������
	private int ASSEM_QUANTITY = 1;// ��������
	private int OTHERS = 0;// ����������ʵȣ�
	private int ULLAGE = 0;// �����
	private String JOB_NUMBER = " ";// ��ҵ���
	private String REPLACE_TYPE = "0";// ȡ�������
	private String JF3_GDKL = "";

	public String getJF3_GDKL() {
		return JF3_GDKL;
	}

	public void setJF3_GDKL(String jF3_GDKL) {
		JF3_GDKL = jF3_GDKL;
	}

	// *********************************************************
	public int getSEQ_NUMBER() {
		return SEQ_NUMBER;
	}

	public void setSEQ_NUMBER(int sEQ_NUMBER) {
		SEQ_NUMBER = sEQ_NUMBER;
	}

	public String getP_NUMBER() {
		return P_NUMBER;
	}

	public void setP_NUMBER(String p_NUMBER) {
		P_NUMBER = p_NUMBER;
	}

	public String getPART_NUMBER() {
		return PART_NUMBER;
	}

	public void setPART_NUMBER(String pART_NUMBER) {
		PART_NUMBER = pART_NUMBER;
	}

	public String getQUANTITY() {
		return QUANTITY;
	}

	public String getRELEASED_DATE() {
		return RELEASED_DATE;
	}

	public void setRELEASED_DATE(String rELEASED_DATE) {
		RELEASED_DATE = rELEASED_DATE;
	}

	public String getVALID_DATE() {
		return VALID_DATE;
	}

	public void setVALID_DATE(String vALID_DATE) {
		VALID_DATE = vALID_DATE;
	}

	public String getINVALID_DATE() {
		return INVALID_DATE;
	}

	public void setINVALID_DATE(String iNVALID_DATE) {
		INVALID_DATE = iNVALID_DATE;
	}

	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}

	public int getASSEM_QUANTITY() {
		return ASSEM_QUANTITY;
	}

	public void setASSEM_QUANTITY(int aSSEM_QUANTITY) {
		ASSEM_QUANTITY = aSSEM_QUANTITY;
	}

	public int getOTHERS() {
		return OTHERS;
	}

	public void setOTHERS(int oTHERS) {
		OTHERS = oTHERS;
	}

	public int getULLAGE() {
		return ULLAGE;
	}

	public void setULLAGE(int uLLAGE) {
		ULLAGE = uLLAGE;
	}

	public String getJOB_NUMBER() {
		return JOB_NUMBER;
	}

	public void setJOB_NUMBER(String jOB_NUMBER) {
		JOB_NUMBER = jOB_NUMBER;
	}

	public String getREPLACE_TYPE() {
		return REPLACE_TYPE;
	}

	public void setREPLACE_TYPE(String rEPLACE_TYPE) {
		REPLACE_TYPE = rEPLACE_TYPE;
	}

}
