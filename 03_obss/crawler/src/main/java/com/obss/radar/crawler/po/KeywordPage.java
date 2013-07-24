package com.obss.radar.crawler.po;

import java.util.Date;

public class KeywordPage {
	/**
	 * 已处理
	 */
	public static final String HANDLED = "1";
	/**
	 * 已报告
	 */
	public static final String REPORTED = "1";
	
	private String id;
	private String siteId;
	private String siteName;
	private String keyword;
	private String digist;
	private String snapPath;
	private String link;
	private String title;
	private Date checkDate;
	private String isHandle;
	private String isReport;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDigist() {
		return digist;
	}
	public void setDigist(String digist) {
		this.digist = digist;
	}
	public String getSnapPath() {
		return snapPath;
	}
	public void setSnapPath(String snapPath) {
		this.snapPath = snapPath;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getIsHandle() {
		return isHandle;
	}
	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}
	public String getIsReport() {
		return isReport;
	}
	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
