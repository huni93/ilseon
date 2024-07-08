package com.com.com.Aproject.dto;

public class AMember {
    private String memberId;
    private String memberName;
    private String memberPw;
    private String memberRank;
    private String firstRank;
    private String apperId;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberRank() {
		return memberRank;
	}
	public void setMemberRank(String memberRank) {
		this.memberRank = memberRank;
	}
	public String getFirstRank() {
		return firstRank;
	}
	public void setFirstRank(String firstRank) {
		this.firstRank = firstRank;
	}
	public String getApperId() {
		return apperId;
	}
	public void setApperId(String apperId) {
		this.apperId = apperId;
	}
	@Override
	public String toString() {
		return "AMember [memberId=" + memberId + ", memberName=" + memberName + ", memberPw=" + memberPw
				+ ", memberRank=" + memberRank + ", firstRank=" + firstRank + ", apperId=" + apperId + "]";
	}

    
}
