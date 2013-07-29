package com.obss.radar.searcher.service;

import java.util.List;

import org.junit.Test;

import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.SearchResult;

public class QihuSearchServiceTest {

	@Test
	public void testSearch() throws Exception {
		SystemCommon.qihuSearch = "http://www.so.com/s?q=";
		QihuSearchService service = new QihuSearchService();
		List<SearchResult> result = service.search("测试");
		for(SearchResult hit : result){
			System.out.println(hit.getTitle());
		}
	}

}
