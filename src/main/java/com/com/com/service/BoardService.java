package com.com.com.service;

import java.util.List;

import com.com.com.dto.Board;
import com.com.com.dto.FileUp;

public interface BoardService {
	
  List<Board> list(Board board);

  void insertBoard(Board board);
  
  void deleteBoard(long seq);
  
  Board getBoard(long seq);// 상세보기 
  
  void updateBoard(Board board); //수정
  
  void increaseViewCount(long seq);//조회수
  
  List<Board> searchBoard(String searchType, String searchKeyword, String startDate, String endDate); // 검색
  
  void saveFile(FileUp file);
}

