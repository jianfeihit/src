package com.obss.radar.searcher.service;

import java.util.List;

import org.junit.Test;

import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.SearchResult;

public class BingSearchServiceTest {

	@Test
	public void testSearch() throws Exception {
		SystemCommon.bingSearch = "http://cn.bing.com/search?q=";
		BingSearchService service = new BingSearchService();
		List<SearchResult> result = service.search("测试");
		for(SearchResult hit : result){
			System.out.println(hit.getTitle());
		}
	}

}
