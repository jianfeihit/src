package com.obss.radar.searcher.po;

public class SearchResult {
	private String title;
	private String content;
	private String link;
	private String snapshort;
	private String datemark;
	
	public SearchResult(String title,String link,String content,String datemark,String snapshort){
		this.title = title;
		this.link = link;
		this.content = content;
		this.snapshort = snapshort;
		this.datemark = datemark;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSnapshort() {
		return snapshort;
	}
	public void setSnapshort(String snapshort) {
		this.snapshort = snapshort;
	}
	public String getDatemark() {
		return datemark;
	}
	public void setDatemark(String datemark) {
		this.datemark = datemark;
	}
}
