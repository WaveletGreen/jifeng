package com.connor.jifeng.plm.jfom006;

public class JFomPasteBean {
	private String productName = "";
	private String picturePath = "";
	private String datasetName = "";
	private String itemID = "";
	private String itemRevisionId = "";
	private String sendTo = "ͼֽ";
	private String releaseDateStr = "";
	private String indexStr = "";

	
	public JFomPasteBean(String indexStr,String itemID,String productName, String picturePath, String datasetName, String itemRevisionId,
			String releaseDateStr) {
		this.indexStr = indexStr;
		this.itemID = itemID;
		this.productName = productName;
		this.picturePath = picturePath;
		this.datasetName = datasetName;
		this.itemRevisionId = itemRevisionId;
		this.releaseDateStr = releaseDateStr;

	}
	
	
	
	public String getItemID() {
		return itemID;
	}



	public void setItemID(String itemID) {
		this.itemID = itemID;
	}



	public String getIndexStr() {
		return indexStr;
	}
	public void setIndexStr(String indexStr) {
		this.indexStr = indexStr;
	}
	public JFomPasteBean(){
			
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getItemRevisionId() {
		return itemRevisionId;
	}

	public void setItemRevisionId(String itemRevisionId) {
		this.itemRevisionId = itemRevisionId;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

}
