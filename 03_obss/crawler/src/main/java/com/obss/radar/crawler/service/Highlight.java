package com.obss.radar.crawler.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.init.SystemCommon;

@Component
public class Highlight {
	private static final String highlight_font = "<font color='#ff0000'>";
	private static final String highlight_back = "</font>";
	
	private static final Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_33);
	private static final SimpleHTMLFormatter simpleHTMLFormatter =  new SimpleHTMLFormatter(highlight_font,highlight_back);
	private static final QueryParser parser = new QueryParser(Version.LUCENE_33,"content",analyzer);
	
	private Map<String,Query> queryMap = new HashMap<String,Query>();
	
	public String lightContent(String content,String keyword) throws Exception{
		if(StringUtils.isEmpty(content)){
			return "";
		}else{
			Query termquery = null;
			if(queryMap.containsKey(keyword)){
				termquery = queryMap.get(keyword);
			}else{
				termquery=parser.parse(keyword);
				queryMap.put(keyword, termquery);
			}
			
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter,new QueryScorer(termquery));
			highlighter.setTextFragmenter(new SimpleFragmenter(SystemCommon.abstractLength));
			String result = highlighter.getBestFragment(analyzer,"content",content);
			if(result == null){
				if(content.indexOf(highlight_back)>0 || content.length()<=SystemCommon.abstractLength){
					return content;
				}else{
					return content.substring(0, SystemCommon.abstractLength);
				}
			}else{
				return result;
			}
		}
	}
}
