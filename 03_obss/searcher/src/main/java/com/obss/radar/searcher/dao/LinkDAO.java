package com.obss.radar.searcher.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.obss.radar.searcher.po.Link;

@Component(value = "linkDAO")
public class LinkDAO extends SqlMapClientDaoSupport {

	@Autowired
	public LinkDAO(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<Link> getUnIndexedLink(int pageSize) {
		return getSqlMapClientTemplate().queryForList("link.getUnIndexedLink", pageSize);
	}
	
	public void updateAllToUnIndex(){
		getSqlMapClientTemplate().update("link.updateAllToUnIndex");
	}
	
	public void updateIndexState(Link link){
		getSqlMapClientTemplate().update("link.updateIndexState",link);
	}

}
