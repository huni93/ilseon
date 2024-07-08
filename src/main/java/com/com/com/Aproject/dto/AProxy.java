package com.com.com.Aproject.dto;

import java.util.Date;

public class AProxy {
	private String proxyId;
    private String proxyName;
    private String apperId;
    private Date proxyDate;
    private String proxyRank;
    private String apperName;
    private String memberRank;
	public String getProxyId() {
		return proxyId;
	}
	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getApperId() {
		return apperId;
	}
	public void setApperId(String apperId) {
		this.apperId = apperId;
	}
	public Date getProxyDate() {
		return proxyDate;
	}
	public void setProxyDate(Date proxyDate) {
		this.proxyDate = proxyDate;
	}
	public String getProxyRank() {
		return proxyRank;
	}
	public void setProxyRank(String proxyRank) {
		this.proxyRank = proxyRank;
	}
	public String getApperName() {
		return apperName;
	}
	public void setApperName(String apperName) {
		this.apperName = apperName;
	}
	public String getMemberRank() {
		return memberRank;
	}
	public void setMemberRank(String memberRank) {
		this.memberRank = memberRank;
	}
	@Override
	public String toString() {
		return "AProxy [proxyId=" + proxyId + ", proxyName=" + proxyName + ", apperId=" + apperId + ", proxyDate="
				+ proxyDate + ", proxyRank=" + proxyRank + ", apperName=" + apperName + ", memberRank=" + memberRank
				+ "]";
	}
	
	
	
	
	
    
}
