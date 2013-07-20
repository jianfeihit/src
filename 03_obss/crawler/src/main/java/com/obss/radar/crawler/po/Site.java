package com.obss.radar.crawler.po;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class Site {
	/**
	 * 主动模式
	 */
	public static final String MODE_INITIATIVE = "0";
	/**
	 * 被动模式
	 */
	public static final String MODE_PASSIVE = "1";
	/**
	 * 两者都有
	 */
	public static final String MODE_BOTH = "2";
	/**
	 * 运行
	 */
	public static final String TASK_STATE_RUN = "0";
	/**
	 * 停止
	 */
	public static final String TASK_STATE_PAUSE = "1";
	/**
	 * 逻辑删除
	 */
	public static final String TASK_STATE_DELETED = "2";
	/**
	 * 监控频率--小时
	 */
	public static final String MONIROT_RATE_HOUR = "1";
	/**
	 * 监控频率--天 
	 */
	public static final String MONIROT_RATE_DAY = "2";
	
    private String id;
    private String siteName;
    private String feedUrl;
    private String ip;
    private String netsp;
    private String city;
    private String adminName;
    private String adminTel;
    private String adminEmail;
    private String runMode;
    private String state;
    private String mainHost;
    private String keywords;
    private List<String> keywordList;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNetsp() {
		return netsp;
	}

	public void setNetsp(String netsp) {
		this.netsp = netsp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminTel() {
		return adminTel;
	}

	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getRunMode() {
		return runMode;
	}

	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getKeywords() {
		return keywords;
	}

	public List<String> getKeywordList() {
		return keywordList;
	}

	public String getMainHost() {
		return mainHost;
	}

	public void setMainHost(String mainHost) {
		this.mainHost = mainHost;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
		if(StringUtils.isNotEmpty(keywords)){
			this.keywordList = Arrays.asList(keywords.split("\\|"));
		}
	}
}
