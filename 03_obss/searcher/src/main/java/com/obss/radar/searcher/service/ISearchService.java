package com.obss.radar.searcher.service;

import java.util.List;

import com.obss.radar.searcher.po.SearchResult;

public interface ISearchService {
	static final String UA = "Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20100101 Firefox/15.0.1";
	
	public List<SearchResult> search(String keyWords) throws Exception;
}
