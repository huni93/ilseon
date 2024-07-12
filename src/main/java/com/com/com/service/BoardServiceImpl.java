package com.com.com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.com.com.dao.BoardDao;
import com.com.com.dto.Board;
import com.com.com.dto.FileUp;



@Service
public class BoardServiceImpl implements BoardService {

   private static final Logger Log = LoggerFactory.getLogger(BoardServiceImpl.class);

   @Autowired
   private BoardDao boardDao;

   @Override
   public List<Board> list(Board board) {
      return boardDao.list(board);
   }


   @Override
   public void insertBoard(Board board) {
       boardDao.insertBoard(board);
   }

   @Override
   public void deleteBoard(long seq) {
       boardDao.deleteBoard(seq);
   }
   
   @Override
   public Board getBoard(long seq) {
       return boardDao.getBoard(seq);
   }
   
   @Override
   public void updateBoard(Board board) {
       boardDao.updateBoard(board);
   }
   @Override
   public void increaseViewCount(long seq) {
       boardDao.increaseViewCount(seq);
   }
   
   @Override
   public List<Board> searchBoard(String searchType, String searchKeyword, String startDate, String endDate) {
       return boardDao.searchBoard(searchType, searchKeyword, startDate, endDate);
   }
   
   @Override
   public void saveFile(FileUp file) {
       boardDao.insertFile(file);
   }
   
   @Override
   public List<FileUp> getFilesByBoardSeq(Long seq) {
       return boardDao.getFilesByBoardSeq(seq);
   }
   
   @Override
   public FileUp getFileBySeq(Long fileSeq) {
       return boardDao.getFileBySeq(fileSeq);
   }
   
  
   
  
   
}