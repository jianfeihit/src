package com.obss.radar.searcher.po;

public class DetailInfo {
	private String url;
	private String title;
	private String abstraction;
	private String crawleddate;
	private String siteNsp;
	private String siteIP;
	private String city;
	private String linkId;
	
	
	public String getUrl() {
    	return url;
    }
	public void setUrl(String url) {
    	this.url = url;
    }
	public String getTitle() {
    	return title;
    }
	public void setTitle(String title) {
    	this.title = title;
    }
	public String getAbstraction() {
    	return abstraction;
    }
	public void setAbstraction(String abstraction) {
    	this.abstraction = abstraction;
    }
	public String getCrawleddate() {
    	return crawleddate;
    }
	public void setCrawleddate(String crawleddate) {
    	this.crawleddate = crawleddate;
    }
	@Override
    public String toString() {
	    return "DetailInfo [url=" + url + ", title=" + title + ", abstraction=" + abstraction
	            + ", crawleddate=" + crawleddate + "]";
    }
	public String getSiteNsp() {
		return siteNsp;
	}
	public void setSiteNsp(String siteNsp) {
		this.siteNsp = siteNsp;
	}
	public String getSiteIP() {
		return siteIP;
	}
	public void setSiteIP(String siteIP) {
		this.siteIP = siteIP;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
}
