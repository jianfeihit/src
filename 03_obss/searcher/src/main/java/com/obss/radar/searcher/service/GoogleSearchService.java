package com.obss.radar.searcher.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.po.SearchResult;

@Component(value="谷歌Search")
public class GoogleSearchService implements ISearchService {

	@Override
	public List<SearchResult> search(String keyWords) throws Exception {
		keyWords = keyWords.replaceAll(" ", "+");
		List<SearchResult> result = new ArrayList<SearchResult>();
		Document document = Jsoup
				.connect("https://www.google.com.hk/search?q="+keyWords)
				.userAgent(UA).get();
		Elements hitsEle = document.select("#rso .g");
		for (Element hitEle : hitsEle) {
			String title = hitEle.select(".r a").text();
			String link = hitEle.select(".r a").attr("href");
			String content = hitEle.select(".st").text();
			SearchResult sr = new SearchResult(title,link,content,null,null);
			result.add(sr);
		}
		return result;
	}

}
