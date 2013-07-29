package com.obss.radar.searcher.service;

import java.util.List;

import org.junit.Test;

import com.obss.radar.searcher.po.SearchResult;

public class BaiduSearchServiceTest {

	@Test
	public void testSearch() throws Exception {
		BaiduSearchService service = new BaiduSearchService();
		List<SearchResult> result = service.search("测试");
		for(SearchResult hit : result){
			System.out.println(hit.getTitle());
		}
	}

}
