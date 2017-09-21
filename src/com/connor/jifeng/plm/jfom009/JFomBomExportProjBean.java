package com.connor.jifeng.plm.jfom009;

public class JFomBomExportProjBean {
	private String PROJ_NAME = "";// project_name
	private String PROJ_CUSTOMER = "";// jf3_zjkh1
	private String PROJ_MODE = "";// jf3_cx1
	private String PRODUCT_LIFE = "";// jf3_yjksrq jf3_yjwcrq
	private String PROJ_MEMBER = "";// project_team project_members

	public String getPROJ_NAME() {
		return PROJ_NAME;
	}

	public void setPROJ_NAME(String pROJ_NAME) {
		PROJ_NAME = pROJ_NAME;
	}

	public String getPROJ_CUSTOMER() {
		return PROJ_CUSTOMER;
	}

	public void setPROJ_CUSTOMER(String pROJ_CUSTOMER) {
		PROJ_CUSTOMER = pROJ_CUSTOMER;
	}

	public String getPROJ_MODE() {
		return PROJ_MODE;
	}

	public void setPROJ_MODE(String pROJ_MODE) {
		PROJ_MODE = pROJ_MODE;
	}

	public String getPRODUCT_LIFE() {
		return PRODUCT_LIFE;
	}

	public void setPRODUCT_LIFE(String pRODUCT_LIFE) {
		PRODUCT_LIFE = pRODUCT_LIFE;
	}

	public String getPROJ_MEMBER() {
		return PROJ_MEMBER;
	}

	public void setPROJ_MEMBER(String pROJ_MEMBER) {
		PROJ_MEMBER = pROJ_MEMBER;
	}

	@Override
	public String toString() {
		return "JFomBomExportProjBean [PROJ_NAME=" + PROJ_NAME
				+ ", PROJ_CUSTOMER=" + PROJ_CUSTOMER + ", PROJ_MODE="
				+ PROJ_MODE + ", PRODUCT_LIFE=" + PRODUCT_LIFE
				+ ", PROJ_MEMBER=" + PROJ_MEMBER + "]";
	}

}
