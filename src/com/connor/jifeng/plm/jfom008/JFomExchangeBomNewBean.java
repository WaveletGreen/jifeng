package com.connor.jifeng.plm.jfom008;

import java.util.List;

import com.teamcenter.rac.kernel.TCComponentItemRevision;

public class JFomExchangeBomNewBean {

	private List<String> queryNoList;
	private List<String> countList;
	private List<TCComponentItemRevision> childRevs;

	public List<String> getQueryNoList() {
		return queryNoList;
	}

	public void setQueryNoList(List<String> queryNoList) {
		this.queryNoList = queryNoList;
	}

	public List<String> getCountList() {
		return countList;
	}

	public void setCountList(List<String> countList) {
		this.countList = countList;
	}

	public List<TCComponentItemRevision> getChildRevs() {
		return childRevs;
	}

	public void setChildRevs(List<TCComponentItemRevision> childRevs) {
		this.childRevs = childRevs;
	}

}
