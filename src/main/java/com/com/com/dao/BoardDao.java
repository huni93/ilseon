package com.com.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.com.com.dto.Board;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession session;
	
	private static final Logger Log = LoggerFactory.getLogger(BoardDao.class);
	
	public List<Board> list(Board board) {
		
		Log.info("******BoardDao Start*******");
			
		return session.selectList("com.com.com.dao.BoardDao.boardList",board);
	}
	// 게시글 등록 메서드 예시
	public void insertBoard(Board board) {
	    Log.info("******BoardDao insertBoard 메서드 호출*******");
	    Log.info("삽입할 board 데이터: " + board.toString());
	    session.insert("com.com.com.dao.BoardDao.insertBoard", board);
	    Log.info("Insert 완료");
	}
	
	 public void deleteBoard(long seq) {
	        session.delete("com.com.com.dao.BoardDao.deleteBoard", seq);
	    }
	 
	 public Board getBoard(long seq) {
	        return session.selectOne("com.com.com.dao.BoardDao.getBoard", seq);
	    }
	 
	 public void updateBoard(Board board) {
	        session.update("com.com.com.dao.BoardDao.updateBoard", board);
	    }
	 
	 public void increaseViewCount(long seq) {
	        session.update("com.com.com.dao.BoardDao.increaseViewCount", seq);
	    }
	 
	 public List<Board> searchBoard(String searchType, String searchKeyword, String startDate, String endDate) {
	        Map<String, Object> params = new HashMap<String, Object>();  // 제네릭 타입을 명시적으로 선언
	        params.put("searchType", searchType);
	        params.put("searchKeyword", searchKeyword);
	        params.put("startDate", startDate);
	        params.put("endDate", endDate);
	        return session.selectList("com.com.com.dao.BoardDao.searchBoard", params);
	    }
	
}