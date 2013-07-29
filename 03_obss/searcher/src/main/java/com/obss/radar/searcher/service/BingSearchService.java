package com.obss.radar.searcher.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.SearchResult;

@Component(value="必应Search")
public class BingSearchService implements ISearchService {

	@Override
	public List<SearchResult> search(String keyWords) throws Exception {
		keyWords = keyWords.replaceAll(" ", "+");
		List<SearchResult> result = new ArrayList<SearchResult>();
		Document document = Jsoup.connect(SystemCommon.bingSearch + keyWords)
				.userAgent(UA).get();
		Elements hitsEle = document
				.select("#wg0 .sa_wr");
		for (Element hitEle : hitsEle) {
			String title = hitEle.select(".sb_tlst a").text();
			String link = hitEle.select(".sb_tlst a").attr("href");
			String content = hitEle.select("p").text();
			String datemark = hitEle.select(".sb_meta").text();
			SearchResult sr = new SearchResult(title, link, content, datemark,
					null);
			result.add(sr);
		}
		return result;
	}

}
