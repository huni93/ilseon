package com.com.com.Aproject.dto;

import java.util.Date;

public class ABoard {

	    private int seq;
	    private String memberId;
	    private String boardTitle;
	    private String boardContent;
	    private Date regDate;
	    private Date apprDate;
	    private String approver;
	    private String apprstat;
	    private String memberName;
	    private Date proxyDate;
	    private String proxyName;
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public String getBoardTitle() {
			return boardTitle;
		}
		public void setBoardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
		}
		public String getBoardContent() {
			return boardContent;
		}
		public void setBoardContent(String boardContent) {
			this.boardContent = boardContent;
		}
		public Date getRegDate() {
			return regDate;
		}
		public void setRegDate(Date regDate) {
			this.regDate = regDate;
		}
		public Date getApprDate() {
			return apprDate;
		}
		public void setApprDate(Date apprDate) {
			this.apprDate = apprDate;
		}
		public String getApprover() {
			return approver;
		}
		public void setApprover(String approver) {
			this.approver = approver;
		}
		public String getApprstat() {
			return apprstat;
		}
		public void setApprstat(String apprstat) {
			this.apprstat = apprstat;
		}
		public String getMemberName() {
			return memberName;
		}
		public void setMemberName(String memberName) {
			this.memberName = memberName;
		}
		public Date getProxyDate() {
			return proxyDate;
		}
		public void setProxyDate(Date proxyDate) {
			this.proxyDate = proxyDate;
		}
		public String getProxyName() {
			return proxyName;
		}
		public void setProxyName(String proxyName) {
			this.proxyName = proxyName;
		}
		@Override
		public String toString() {
			return "ABoard [seq=" + seq + ", memberId=" + memberId + ", boardTitle=" + boardTitle + ", boardContent="
					+ boardContent + ", regDate=" + regDate + ", apprDate=" + apprDate + ", approver=" + approver
					+ ", apprstat=" + apprstat + ", memberName=" + memberName + ", proxyDate=" + proxyDate
					+ ", proxyName=" + proxyName + "]";
		}
		
	  
		
	
}
