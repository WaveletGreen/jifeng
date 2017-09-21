package com.connor.jifeng.plm.jfom009;

public class JFomBomExportBean {

	// --------------------
	public String getIndexExt() {
		return indexExt;
	}

	public void setIndexExt(String indexExt) {
		if (indexExt != null)
			this.indexExt = indexExt;
	}

	public String getMPS() {
		return MPS;
	}

	public void setMPS(String mPS) {
		if (indexExt != null)
			MPS = mPS;
	}

	public String getPingming() {
		return pingming;
	}

	public void setPingming(String pingming) {
		if (pingming != null)
			this.pingming = pingming;
	}

	public String getJingzhong() {
		return jingzhong;
	}

	public void setJingzhong(String jingzhong) {
		if (jingzhong != null)
			this.jingzhong = jingzhong;
	}

	public String getMaozhong() {
		return maozhong;
	}

	public void setMaozhong(String maozhong) {
		this.maozhong = maozhong;
	}

	public String getGongyi() {
		return gongyi;
	}

	public void setGongyi(String gongyi) {
		this.gongyi = gongyi;
	}

	public String getGongyingshang() {
		return gongyingshang;
	}

	public void setGongyingshang(String gongyingshang) {
		this.gongyingshang = gongyingshang;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String type = "";
	private String name = "";

	private int index = 1;

	private int indexStr = 0;// 序号 0
	private String wlNo = "";// 级别 1-6
	private String customerNo = "";// 零件号 9
	private String drawingNo = "";//
	private String picturePath = "";// 图片
	private String wlName = "";//
	private String materialName = "";// 材料名称
	private String materialSize = "";// 材料规格
	private String materialStander = "";// 材料标准
	private String materialPark = "";// 材料牌号
	private String wlNumber = "";// 件、份7
	private String wlWeight = "";
	private String indexExt = "";
	private String MPS = "";// 自制外协8
	private String pingming = "";
	private String jingzhong = "";
	private String maozhong = "";
	private String gongyi = "";
	private String gongyingshang = "";
	private String k3code = "";

	// 设置或者获取属性的方法

	public String getK3code() {
		return k3code;
	}

	public void setK3code(String k3code) {
		this.k3code = k3code;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndexStr() {
		return indexStr;
	}

	public void setIndexStr(int indexStr) {
		this.indexStr = indexStr;
	}

	public String getWlNo() {
		return wlNo;
	}

	public void setWlNo(String wlNo) {
		this.wlNo = wlNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getDrawingNo() {
		return drawingNo;
	}

	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialSize() {
		return materialSize;
	}

	public void setMaterialSize(String materialSize) {
		this.materialSize = materialSize;
	}

	public String getMaterialStander() {
		return materialStander;
	}

	public void setMaterialStander(String materialStander) {
		this.materialStander = materialStander;
	}

	public String getMaterialPark() {
		return materialPark;
	}

	public void setMaterialPark(String materialPark) {
		this.materialPark = materialPark;
	}

	public String getWlNumber() {
		return wlNumber;
	}

	public void setWlNumber(String wlNumber) {
		this.wlNumber = wlNumber;
	}

	public String getWlWeight() {
		return wlWeight;
	}

	public void setWlWeight(String wlWeight) {
		this.wlWeight = wlWeight;
	}

	@Override
	public String toString() {
		return "JFomBomExportBean [index=" + index + ", indexStr=" + indexStr
				+ ", wlNo=" + wlNo + ", customerNo=" + customerNo
				+ ", drawingNo=" + drawingNo + ", picturePath=" + picturePath
				+ ", wlName=" + wlName + ", materialName=" + materialName
				+ ", materialSize=" + materialSize + ", materialStander="
				+ materialStander + ", materialPark=" + materialPark
				+ ", wlNumber=" + wlNumber + ", wlWeight=" + wlWeight + "]";
	}

}
