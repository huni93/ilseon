package com.com.com.Aproject.dto;

import java.util.Date;

public class AHistory {
	private int hiNum;
    private Date signDate;
    private String approval;
    private String signStatus;
    private int hiNo;
    private String memberName;
	public int getHiNum() {
		return hiNum;
	}
	public void setHiNum(int hiNum) {
		this.hiNum = hiNum;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public int getHiNo() {
		return hiNo;
	}
	public void setHiNo(int hiNo) {
		this.hiNo = hiNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
	public String toString() {
		return "AHistory [hiNum=" + hiNum + ", signDate=" + signDate + ", approval=" + approval + ", signStatus="
				+ signStatus + ", hiNo=" + hiNo + ", memberName=" + memberName + "]";
	}
	
	
    
}
