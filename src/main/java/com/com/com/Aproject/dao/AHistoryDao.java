package com.com.com.Aproject.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.com.com.Aproject.dto.AHistory;


@Repository
public class AHistoryDao {

    @Autowired
    private SqlSession session;


    public void insertHistory(AHistory history) {
        session.insert("com.com.com.Aproject.dao.AHistoryDao.insertHistory", history);
    }
    
    public List<AHistory> getHistoryByHiNo(int hiNo) {
        return session.selectList("com.com.com.Aproject.dao.AHistoryDao.getHistoryByHiNo", hiNo);
    }
    

}