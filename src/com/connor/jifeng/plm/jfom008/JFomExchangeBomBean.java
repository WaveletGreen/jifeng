package com.connor.jifeng.plm.jfom008;

import java.util.ArrayList;
import java.util.List;

import com.teamcenter.rac.kernel.TCComponentItemRevision;

public class JFomExchangeBomBean {

	//

	private List<String> childrenUIDs;
	private List<String> queryNoList;
	private List<String> countList;
	private List<TCComponentItemRevision> materialRevs;
	private TCComponentItemRevision rev;
	private Boolean isCreate = true;

	public JFomExchangeBomBean() {
		childrenUIDs = new ArrayList<String>();
		materialRevs = new ArrayList<TCComponentItemRevision>();
	}

	public Boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Boolean isCreate) {
		this.isCreate = isCreate;
	}

	public TCComponentItemRevision getRev() {
		return rev;
	}

	public void setRev(TCComponentItemRevision rev) {
		this.rev = rev;
	}

	public List<String> getChildrenUIDs() {
		return childrenUIDs;
	}

	public void setChildrenUIDs(List<String> childrenUIDs) {
		this.childrenUIDs = childrenUIDs;
	}

	public List<TCComponentItemRevision> getMaterialRevs() {
		return materialRevs;
	}

	public void setMaterialRevs(List<TCComponentItemRevision> materialRevs) {
		this.materialRevs = materialRevs;
	}

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

}
