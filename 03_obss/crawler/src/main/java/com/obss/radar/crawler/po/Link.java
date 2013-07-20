package com.obss.radar.crawler.po;

import java.util.Date;

import com.obss.radar.crawler.util.MD5Util;

public class Link{
	/**
	 * 待处理
	 */
	public static final String STAR_UNDO = "0";
	/**
	 * 正在处理
	 */
	public static final String STAR_DOING = "1";
	/**
	 * 处理完毕
	 */
	public static final String STAR_DONE = "2";
	
	public Link(){
		
	}
	
	public Link(String siteId,String link){
		this.siteId = siteId;
		this.link = link;
		this.linkMD5 = MD5Util.MD5(link);
		this.state = STAR_UNDO;
	}
	
	private String id;
	private String siteId;
	private String linkMD5;
	private String link;
	private String snapPath;
	private String contentMD5;
	private String state;
	private Date lastCrawDate;
	
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
	public String getLinkMD5() {
		return linkMD5;
	}
	public void setLinkMD5(String linkMD5) {
		this.linkMD5 = linkMD5;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSnapPath() {
		return snapPath;
	}
	public void setSnapPath(String snapPath) {
		this.snapPath = snapPath;
	}
	public String getContentMD5() {
		return contentMD5;
	}
	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getLastCrawDate() {
		return lastCrawDate;
	}
	public void setLastCrawDate(Date lastCrawDate) {
		this.lastCrawDate = lastCrawDate;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((linkMD5 == null) ? 0 : linkMD5.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    Link other = (Link) obj;
	    if (linkMD5 == null) {
		    if (other.linkMD5 != null) return false;
	    } else if (!linkMD5.equals(other.linkMD5)) return false;
	    return true;
    }
	
}
