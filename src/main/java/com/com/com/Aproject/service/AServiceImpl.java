package com.com.com.Aproject.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.com.com.Aproject.dao.ABoardDao;
import com.com.com.Aproject.dao.AHistoryDao;
import com.com.com.Aproject.dao.AProxyDao;
import com.com.com.Aproject.dto.ABoard;
import com.com.com.Aproject.dto.AHistory;
import com.com.com.Aproject.dto.AMember;
import com.com.com.Aproject.dto.AProxy;

@Service
@Transactional
public class AServiceImpl implements AService {

    @Autowired
    private ABoardDao aBoardDao;
    @Autowired
    private AHistoryDao aHistoryDao;
    @Autowired
    private AProxyDao aProxyDao;

    @Override
    public AMember login(AMember dto) {
        // DAO를 이용하여 사용자 정보 조회
        AMember member = aBoardDao.login(dto.getMemberId());
        
        // 조회된 회원이 존재하고 비밀번호가 일치할 경우 회원 정보 반환
        if (member != null && member.getMemberPw().equals(dto.getMemberPw())) {
            return member;
        } else {
            // 비밀번호가 일치하지 않는 경우
            return null;
        }
    }
    
    @Override
    public void saveBoard(ABoard board) {
        // 게시글 저장 메서드 호출
        aBoardDao.save(board);
    }

    @Override
    public int idCheck(String memberId) {
        return aBoardDao.idCheck(memberId);
    }
    
    @Override//목록
    public List<ABoard> getBoardsByMemberId(String memberId, String memberName, String memberRank, Date proxyDate) {
        List<ABoard> boards = aBoardDao.getBoardsByMemberId(memberId, memberName, memberRank, proxyDate);
       
        return boards;
    }
    
    @Override  //상세보기
    public ABoard getBoard(int seq) {
        return aBoardDao.getBoard(seq);
    }
    @Override
    public int getLastBoardSeq() {
        return aBoardDao.getLastBoardSeq();
    }
    
    @Override
    public void updateBoard(ABoard board) {
        aBoardDao.updateBoard(board);
    }
    
    @Override// 부장 리스트
    public List<ABoard> getApprovalPendingBoards() {     
        List<ABoard> boards = aBoardDao.getApprovalPendingBoards();       
        return boards;
    }

    @Override
    public void updateBoardWithApproval(ABoard board) {
        System.out.println("Service layer - Updating board: " + board); // 로그 추가
        aBoardDao.updateBoardWithApproval(board);
    }

    @Override
    public void insertHistory(AHistory history) {
    	aHistoryDao.insertHistory(history);
    }

    @Override
    public List<AHistory> getHistoryByHiNo(int hiNo) {
        return aHistoryDao.getHistoryByHiNo(hiNo);
    }
    @Override
    public List<ABoard> AsearchBoard(Map<String, Object> params) {
        return aBoardDao.AsearchBoard(params);
    }
    
    @Override
    public List<ABoard> getBoardsByApprovalStatus(Map<String, Object> params) {
        return aBoardDao.getBoardsByApprovalStatus(params);
    }
    
    @Override
    public List<AProxy> selectProxiesByRank(String memberRank) {
        return aProxyDao.selectProxy(memberRank);
    }
    
    @Override
    public void updateProxy(Map<String, Object> params) {
        aProxyDao.updateProxyAuthorization(params);
    }
    
    @Override
    public AProxy getProxyInfoByMemberId(String memberId) {
        return aProxyDao.getProxyInfoByMemberId(memberId);
    }
    
    @Override
    public void revertProxy() {
        aProxyDao.revertProxy();
    }
    
}
