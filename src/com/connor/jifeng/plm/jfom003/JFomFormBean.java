package com.connor.jifeng.plm.jfom003;

import java.util.List;

public class JFomFormBean {
	public JFomFormBean() {

	}

	public JFomFormBean(String wlCode, String wlName, String wlGuige,
			String wlCount) {
		// this.index = index;
		this.wlCode = wlCode;
		this.wlName = wlName;
		this.wlGuige = wlGuige;
		this.wlCount = wlCount;
	}

	public JFomFormBean(String wlCode, String wlName, String wlGuige) {
		// this.index = index;
		this.wlCode = wlCode;
		this.wlName = wlName;
		this.wlGuige = wlGuige;
	}

	// private String index = "";
	private String wlCode = "";
	private String wlName = "";
	private String wlGuige = "";
	private String wlCount = "";
	private List<String> countList;
	private List<String> ckList;

	public List<String> getCountList() {
		return countList;
	}

	public void setCountList(List<String> countList) {
		this.countList = countList;
	}

	public List<String> getCkList() {
		return ckList;
	}

	public void setCkList(List<String> ckList) {
		this.ckList = ckList;
	}

	// public String getIndex() {
	// return index;
	// }
	// public void setIndex(String index) {
	// this.index = index;
	// }
	public String getWlCode() {
		return wlCode;
	}

	public void setWlCode(String wlCode) {
		this.wlCode = wlCode;
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	public String getWlGuige() {
		return wlGuige;
	}

	public void setWlGuige(String wlGuige) {
		this.wlGuige = wlGuige;
	}

	public String getWlCount() {
		return wlCount;
	}

	public void setWlCount(String wlCount) {
		this.wlCount = wlCount;
	}

}
