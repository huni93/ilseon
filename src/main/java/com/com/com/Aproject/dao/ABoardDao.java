package com.com.com.Aproject.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.com.com.Aproject.dto.ABoard;
import com.com.com.Aproject.dto.AMember;

@Repository
public class ABoardDao {

    @Autowired
    private SqlSession session;
    
    private static final Logger logger = LoggerFactory.getLogger(ABoardDao.class);

    public AMember login(String memberId) {
        return session.selectOne("com.com.com.Aproject.dao.ABoardDao.login", memberId);
    }
    
    public void save(ABoard board) {
    	logger.info("ABoardDao - save method called with board: {}", board);
        session.insert("com.com.com.Aproject.dao.ABoardDao.save", board);
    }

    public int idCheck(String memberId) {
        return session.selectOne("com.com.com.Aproject.dao.ABoardDao.idCheck", memberId);
    }

    public List<ABoard> getBoardsByMemberId(String memberId, String memberName, String memberRank, Date proxyDate) {
    	Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("memberName", memberName);
        params.put("memberRank", memberRank);
        params.put("proxyDate", proxyDate);
        
        if ("003".equals(memberRank)) {
            // 과장인 경우 모든 게시글 조회
            return session.selectList("com.com.com.Aproject.dao.ABoardDao.getAllBoards", params);
        } else {
            // 일반 직원인 경우 해당 사용자의 게시글만 조회
            return session.selectList("com.com.com.Aproject.dao.ABoardDao.getBoardsByMemberId", params);
        }
    }



    public ABoard getBoard(int seq) {
        return session.selectOne("com.com.com.Aproject.dao.ABoardDao.getBoard", seq); 
    }

    public int getLastBoardSeq() {
        return session.selectOne("com.com.com.Aproject.dao.ABoardDao.getLastBoardSeq");
    }

    public void updateBoard(ABoard board) {
        session.update("com.com.com.Aproject.dao.ABoardDao.updateBoard", board);
    }

    public List<ABoard> getApprovalPendingBoards() {
        return session.selectList("com.com.com.Aproject.dao.ABoardDao.getApprovalPendingBoards");
    }

    public void updateBoardWithApproval(ABoard board) {
        System.out.println("DAO layer - Updating board: " + board); // 로그 추가
        session.update("com.com.com.Aproject.dao.ABoardDao.updateBoardWithApproval", board);
    }
    
    public List<ABoard> AsearchBoard(Map<String, Object> params) {
        return session.selectList("com.com.com.Aproject.dao.ABoardDao.AsearchBoard", params);
    }
    
    public List<ABoard> getBoardsByApprovalStatus(Map<String, Object> params) {
        return session.selectList("com.com.com.Aproject.dao.ABoardDao.getBoardsByApprovalStatus", params);
    }
    
   

}