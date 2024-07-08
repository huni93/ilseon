package com.com.com.Aproject.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.com.com.Aproject.dto.ABoard;
import com.com.com.Aproject.dto.AHistory;
import com.com.com.Aproject.dto.AMember;
import com.com.com.Aproject.dto.AProxy;

public interface AService {
    AMember login(AMember dto);
    void saveBoard(ABoard board);
    int idCheck(String memberId);
	List<ABoard> getBoardsByMemberId (String memberId, String memberName, String memeberRank, Date proxyDate);
	ABoard getBoard(int seq);
	 
	int getLastBoardSeq();// 마지막 글 번호 조회 메서드 추가
	
	void updateBoard(ABoard board); //상태변경
	
	List<ABoard> getApprovalPendingBoards(); // 부장리스트

	void updateBoardWithApproval(ABoard board); // 수정
	
	 void insertHistory(AHistory history); // 히스토리 저장 메서드 추가
	 
	 List<AHistory> getHistoryByHiNo(int hiNo); //히스토리 리스트
	 
	 List<ABoard> AsearchBoard(Map<String, Object> params); // 검색
	 
	 List<ABoard> getBoardsByApprovalStatus(Map<String, Object> params); //ajax

	 List<AProxy> selectProxiesByRank(String memberRank);//대리결재자선택
	 
	 void updateProxy(Map<String, Object> params); //승인
	 
	 AProxy getProxyInfoByMemberId(String memberId);// 프록스 승인
	 
	 void revertProxy(); //프록스 date비교 

    }

