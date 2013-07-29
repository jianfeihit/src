package com.obss.radar.searcher.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.obss.radar.searcher.dao.LinkDAO;
import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.Link;
import com.obss.radar.searcher.po.Site;
import com.obss.radar.searcher.util.HTMLUtil;

@Component
public class IndexService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SiteService siteService;
	@Autowired
	private LinkDAO linkDAO;

	private int pageSize = 100;

	public void buildIndex(boolean isFull) throws Exception {
		int count = 0;
		if(isFull){
			linkDAO.updateAllToUnIndex();
		}
		List<Link> unIndexedLink = linkDAO.getUnIndexedLink(pageSize);
		while (!CollectionUtils.isEmpty(unIndexedLink)) {
			for (Link link : unIndexedLink) {
				Site site = siteService.getSiteById(link.getSiteId());
				if (site != null) {
					index(isFull, link, site);
					linkDAO.updateIndexState(link);
					count++;
				}
			}
			unIndexedLink = linkDAO.getUnIndexedLink(pageSize);
		}
		logger.info("索引建立完成,共收录文件" + count + "条");
		if(count>0){
			optimize();
			logger.info("索引优化完成");
		}
	}

	public boolean index(boolean isFull, Link link, Site site) throws Exception {
		String htmlContent = FileUtils.readFileToString(new File(link.getSnapPath()),
		        SystemCommon.DEFAULT_ENCODE);
		FSDirectory dir = FSDirectory.open(new File(SystemCommon.indexBasepath));
		IndexWriter.unlock(dir);
		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_33);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_33, analyzer);
		conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		IndexWriter index = new IndexWriter(dir, conf);
		String title = HTMLUtil.getTitle(htmlContent);
		Document doc = new Document();
		Field urlField = new Field("url", link.getLink(), Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field contentField = new Field("content", htmlContent, Field.Store.YES,
		        Field.Index.ANALYZED);
		Field feedUrlField = new Field("feedUrl", site.getFeedUrl(), Field.Store.YES,
		        Field.Index.NOT_ANALYZED);
		Field titleField = new Field("title", title, Field.Store.YES, Field.Index.ANALYZED);
		Field linkIdField = new Field("linkId", link.getId(), Field.Store.YES, Field.Index.NO);
		Field cityField = new Field("city", site.getCity(), Field.Store.YES, Field.Index.NO);
		Field siteipField = new Field("siteip", site.getIp(), Field.Store.YES, Field.Index.NO);
		Field sitenspField = new Field("sitensp", site.getNetsp(), Field.Store.YES, Field.Index.NO);
		NumericField crawleddate = new NumericField("crawleddate", Field.Store.YES, true);
		crawleddate.setLongValue(link.getLastCrawDate().getTime());
		titleField.setBoost(2f);
		urlField.setBoost(2f);
		doc.add(urlField);
		doc.add(feedUrlField);
		doc.add(contentField);
		doc.add(titleField);
		doc.add(crawleddate);
		doc.add(linkIdField);
		doc.add(cityField);
		doc.add(siteipField);
		doc.add(sitenspField);
		index.addDocument(doc);
		index.close();
		return true;
	}

	public void optimize() throws IOException {
		FSDirectory dir = FSDirectory.open(new File(SystemCommon.indexBasepath));
		IndexWriter.unlock(dir);
		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_33);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_33, analyzer);
		IndexWriter index = new IndexWriter(dir, conf);
		index.optimize();
		index.close();
	}
}
