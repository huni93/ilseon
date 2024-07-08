package com.com.com.Aproject.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.com.com.Aproject.dto.AProxy;

@Repository
public class AProxyDao {

    @Autowired
    private SqlSession session;
    
    private static final Logger logger = LoggerFactory.getLogger(AProxyDao.class);

    public List<AProxy> selectProxy(String memberRank) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberRank", memberRank);
        return session.selectList("com.com.com.Aproject.dao.AProxyDao.selectProxy", params);
    }
    
    public void updateProxyAuthorization(Map<String, Object> params) {
        session.update("com.com.com.Aproject.dao.AProxyDao.updateProxy", params);
    }
    
    
    public AProxy getProxyInfoByMemberId(String memberId) {
        return session.selectOne("com.com.com.Aproject.dao.AProxyDao.getProxyInfoByMemberId", memberId);
    }
    
    public void revertProxy() {
        session.update("com.com.com.Aproject.dao.AProxyDao.revertProxy");
    }

}