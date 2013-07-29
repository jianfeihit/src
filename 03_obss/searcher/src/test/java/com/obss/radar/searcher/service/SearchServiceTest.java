package com.obss.radar.searcher.service;

import org.junit.Before;

import com.obss.radar.searcher.init.SystemCommon;

import junit.framework.TestCase;

public class SearchServiceTest extends TestCase {
	@Before
	public void setUp(){
		SystemCommon.indexBasepath = "D:/index";
	}
	
	public void testSearch() throws Exception {
//		SearchService searchService =  new SearchService();
//		searchService.search("吉林");
	}

}
