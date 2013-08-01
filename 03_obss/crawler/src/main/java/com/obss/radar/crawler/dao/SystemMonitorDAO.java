package com.obss.radar.crawler.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.obss.radar.crawler.po.SystemMonitor;

@Component
public class SystemMonitorDAO extends SqlMapClientDaoSupport {

	@Autowired
	public SystemMonitorDAO(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	public void saveorupdateMonitor(SystemMonitor monitor) throws SQLException {
		SystemMonitor mon = this.getSystemMonitor(monitor.getType());
		if (mon == null) {
			getSqlMapClientTemplate().insert("systemMonitor.saveMonitor",monitor);
		}else{
			getSqlMapClientTemplate().update("systemMonitor.updateMonitor",monitor);
		}
	}
	
	public SystemMonitor getSystemMonitor(String type){
		return (SystemMonitor) getSqlMapClientTemplate().queryForObject("systemMonitor.getMonitor", type);
	}
}
