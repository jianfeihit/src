package com.obss.radar.searcher.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.po.SearchResult;

@Component(value="百度Search")
public class BaiduSearchService implements ISearchService {

	@Override
	public List<SearchResult> search(String keyWords) throws Exception {
		keyWords = keyWords.replaceAll(" ", "+");
		List<SearchResult> result = new ArrayList<SearchResult>();
		Document document = Jsoup
				.connect("http://www.baidu.com/s?wd="+keyWords)
				.userAgent(UA).get();
		Elements hitsEle = document.select("#content_left table[tpl=se_st_default]");
		for (Element hitEle : hitsEle) {
			String title = hitEle.select(".t").text();
			String content = hitEle.select(".c-abstract").text();
			String datemark = hitEle.select(".f13 span").text();
			String snapshort = hitEle.select(".f13 .m").attr("href");
			String link = hitEle.select(".t a").attr("href");
			SearchResult sr = new SearchResult(title,link,content,datemark,snapshort);
			result.add(sr);
		}
		return result;
	}

}
