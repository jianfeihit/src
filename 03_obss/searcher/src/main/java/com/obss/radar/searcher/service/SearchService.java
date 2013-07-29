package com.obss.radar.searcher.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.dao.SiteDAO;
import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.DetailInfo;
import com.obss.radar.searcher.po.StatisticsInfo;
import com.obss.radar.searcher.util.HTMLUtil;
import com.obss.radar.searcher.util.SystemDataUtils;

@Component
public class SearchService {
	private String highlight_font = "<font color='#ff0000'>";
	private String highlight_back = "</font>";
	private int maxResult = 1000;
	private Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_33);

	@Autowired
	private SiteDAO crawleTaskDAO = null;
	private IndexSearcher searcher;

	public static String segWords(Analyzer ana, String keyWords) throws IOException {
		String result = "";
		Reader reader = new StringReader(keyWords);
		TokenStream stream = (TokenStream) ana.tokenStream("", reader);
		CharTermAttribute termAtt = (CharTermAttribute) stream
		        .addAttribute(CharTermAttribute.class);
		while (stream.incrementToken()) {
			result = result + new String(termAtt.buffer(), 0, termAtt.length()) + " ";
		}
		return result;
	}

	public BooleanQuery parseQuery(String querystr) throws Exception {
		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_33);
		querystr = segWords(analyzer, querystr);
		QueryParser parser = new QueryParser(Version.LUCENE_33, "content", analyzer);
		Query termquery = parser.parse(querystr);
		BooleanQuery query = new BooleanQuery();
		query.add(termquery, Occur.MUST);
		return query;
	}

	public BooleanQuery parseQuery(String baseQuery, String extentQuery, int type) throws Exception {
		QueryParser parser = new QueryParser(Version.LUCENE_33, "content", analyzer);
		Query base = parser.parse(baseQuery);
		Query extent = parser.parse(extentQuery);
		BooleanQuery total = new BooleanQuery();
		total.add(base, Occur.MUST);
		switch (type) {
			case 0:
				total.add(extent, Occur.MUST);
				break;
			case 1:
				total.add(extent, Occur.SHOULD);
				break;
			case 2:
				total.add(extent, Occur.MUST_NOT);
				break;
		}
		return total;
	}

	public List<DetailInfo> search(String keyWords) throws Exception {
		List<DetailInfo> result = new ArrayList<DetailInfo>();
		keyWords = segWords(analyzer, keyWords);
		searcher = new IndexSearcher(FSDirectory.open(new File(SystemCommon.indexBasepath)), true);
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_33, new String[] {
		        "title", "content" }, analyzer);
		Query query = parser.parse(keyWords);

		TopDocs results = searcher.search(query, maxResult);
		ScoreDoc[] hits = results.scoreDocs;
		for (ScoreDoc hit : hits) {
			int docid = hit.doc;
			Document doc = searcher.doc(docid);
			DetailInfo detail = new DetailInfo();
			String content = doc.get("content");
			detail.setAbstraction(content.replace("\n", "").replaceAll("\\s+", " ").trim());
			detail.setCity(doc.get("city"));
			detail.setSiteIP(doc.get("siteip"));
			detail.setSiteNsp(doc.get("sitensp"));
			detail.setLinkId(doc.get("linkId"));
			result.add(detail);
		}
		return result;
	}

	public int detailSearch(List<DetailInfo> result, String querystr, int start, int size)
	        throws Exception {
		result.clear();
		BooleanQuery query = parseQuery(querystr);
		searcher = new IndexSearcher(FSDirectory.open(new File(SystemCommon.indexBasepath)), true);
		TopDocs results = searcher.search(query, maxResult);
		ScoreDoc[] hits = results.scoreDocs;
		int count = 0;
		for (int i = 0; i < hits.length; i++) {
			if (i < start) {
				continue;
			}
			DetailInfo detailInfo = new DetailInfo();
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(highlight_font,
			        highlight_back);
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(SystemCommon.abstractLength));
			int docid = hits[i].doc;
			Document doc = searcher.doc(docid);
			String content = doc.get("content");
			content = HTMLUtil.getHTMLText(content);
			if (!"".equals(content) && content != null) {
				content = highlighter.getBestFragment(analyzer, "content", content);
			}
			detailInfo.setUrl(doc.get("url"));
			String title = doc.get("title");
			if (StringUtils.isNotEmpty(title)) {
				title = highlighter.getBestFragment(analyzer, "title", title);
			}
			if (StringUtils.isEmpty(title)) {
				title = doc.get("title");
			}
			detailInfo.setTitle(title);
			detailInfo.setCrawleddate(doc.get("crawleddate"));
			if (content == null) {
				content = "";
			}
			detailInfo.setAbstraction(content.replace("\n", "").replaceAll("\\s+", " ").trim());
			detailInfo.setCity(doc.get("city"));
			detailInfo.setSiteIP(doc.get("siteip"));
			detailInfo.setSiteNsp(doc.get("sitensp"));
			detailInfo.setLinkId(doc.get("linkId"));
			result.add(detailInfo);
			count++;
			if (count == size) {
				break;
			}
		}
		return hits.length;
	}

	public ArrayList<StatisticsInfo> dateSearch(String querystr) throws Exception {
		ArrayList<StatisticsInfo> result = new ArrayList<StatisticsInfo>();
		BooleanQuery query = parseQuery(querystr);
		String date = SystemDataUtils.getDate("yyyyMMdd");
		for (int i = 0; i < 30; i++) {
			StatisticsInfo res = new StatisticsInfo();
			BooleanQuery query_new = new BooleanQuery();
			query_new.add(query, Occur.MUST);
			NumericRangeQuery<Long> query_date = NumericRangeQuery.newLongRange("crawleddate",
			        Long.valueOf(SystemDataUtils.getdate(date, i, "yyyyMMdd")),
			        Long.valueOf(SystemDataUtils.getdate(date, i - 1, "yyyyMMdd")), true, false);
			query_new.add(query_date, Occur.MUST);
			searcher = new IndexSearcher(FSDirectory.open(new File(SystemCommon.indexBasepath)),
			        true);
			TopDocs results = searcher.search(query_new, maxResult);
			ScoreDoc[] hits = results.scoreDocs;
			res.key = SystemDataUtils.getdate(date, i, "yyyyMMdd");
			res.value = hits.length;
			result.add(res);
		}
		return result;
	}

	public ArrayList<StatisticsInfo> siteSearch(String querystr) throws Exception {
		ArrayList<StatisticsInfo> result = new ArrayList<StatisticsInfo>();
		BooleanQuery query = parseQuery(querystr);
		StatisticsInfo res = new StatisticsInfo();
		BooleanQuery query_new = new BooleanQuery();
		query_new.add(query, Occur.MUST);
		searcher = new IndexSearcher(FSDirectory.open(new File(SystemCommon.indexBasepath)), true);
		TopDocs results = searcher.search(query_new, maxResult);
		ScoreDoc[] hits = results.scoreDocs;
		res.value = hits.length;
		result.add(res);
		return result;
	}

	public int advanceSearch(List<DetailInfo> result, String basequery, String extentquery,
	        int type, String site, int start, int size) throws Exception {
		result.clear();
		BooleanQuery query = parseQuery(basequery, extentquery, type);
		if (site != null && !("all".equalsIgnoreCase(site))) {
			TermQuery query_task = new TermQuery(new Term("feedUrl", site));
			query.add(query_task, Occur.MUST);
		}
		searcher = new IndexSearcher(FSDirectory.open(new File(SystemCommon.indexBasepath)), true);
		TopDocs results = searcher.search(query, maxResult);
		ScoreDoc[] hits = results.scoreDocs;
		int count = 0;
		for (int i = 0; i < hits.length; i++) {
			if (i < start) {
				continue;
			}
			DetailInfo detailInfo = new DetailInfo();
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(highlight_font,
			        highlight_back);
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(SystemCommon.abstractLength));
			int docid = hits[i].doc;
			Document doc = searcher.doc(docid);
			String content = doc.get("content");
			content = HTMLUtil.getHTMLText(content);
			if (!"".equals(content) && content != null) {
				content = highlighter.getBestFragment(analyzer, "content", content);
			}
			detailInfo.setUrl(doc.get("url"));
			String title = doc.get("title");
			if (StringUtils.isNotEmpty(title)) {
				title = highlighter.getBestFragment(analyzer, "title", title);
			}
			if (StringUtils.isEmpty(title)) {
				title = doc.get("title");
			}
			detailInfo.setTitle(title);
			detailInfo.setCrawleddate(doc.get("crawleddate"));
			if (content == null) {
				content = "";
			}
			detailInfo.setAbstraction(content.replace("\n", "").replaceAll("\\s+", " ").trim());
			result.add(detailInfo);
			count++;
			if (count == size) break;
		}
		return hits.length;
	}
}
