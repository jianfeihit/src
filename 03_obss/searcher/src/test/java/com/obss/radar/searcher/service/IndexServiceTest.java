package com.obss.radar.searcher.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;

import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.Link;
import com.obss.radar.searcher.po.Site;

public class IndexServiceTest extends TestCase {
	
	@Before
	public void setUp(){
		SystemCommon.indexBasepath = "D:/index2";
	}
	
	public void testBuildIndex() throws Exception {
//		IndexService index = new IndexService();
//		Link link = new Link();
//		Site site = new Site();
//		link.setSnapPath("D:/snapshort/3/80a59a78423c051e0e7ea0116a9f098c");
//		link.setId("1");
//		link.setLink("http://www.jl.gov.cn");
//		link.setLastCrawDate(new Date());
//		site.setCity("北京");
//		site.setNetsp("联通");
//		site.setIp("10.10.10.10");
//		site.setFeedUrl("http://www.jl.gov.cn");
//		index.index(true, link, site);
	}
	

}
