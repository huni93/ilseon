package com.com.com.dto;

import java.util.Date;

public class FileUp {

	private int fileSeq;
    private String realName;
    private String saveName;
    private Date regDate;
    private String savePath;
    private int listSeq;
	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public int getListSeq() {
		return listSeq;
	}
	public void setListSeq(int listSeq) {
		this.listSeq = listSeq;
	}
	@Override
	public String toString() {
		return "FileUp [fileSeq=" + fileSeq + ", realName=" + realName + ", saveName=" + saveName + ", regDate="
				+ regDate + ", savePath=" + savePath + ", listSeq=" + listSeq + "]";
	}
	
	
}
