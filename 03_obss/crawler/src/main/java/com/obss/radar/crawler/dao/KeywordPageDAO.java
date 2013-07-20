package com.obss.radar.crawler.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.obss.radar.crawler.po.KeywordPage;

@Component
public class KeywordPageDAO extends SqlMapClientDaoSupport{
    @Autowired
	public KeywordPageDAO(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
    
    public void saveKeywordPage(KeywordPage page){
    	getSqlMapClientTemplate().insert("keywordPage.saveKeywordPage", page);
    }
    
}
