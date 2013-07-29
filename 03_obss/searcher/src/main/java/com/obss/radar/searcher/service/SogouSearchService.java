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

@Component(value="搜狗Search")
public class SogouSearchService implements ISearchService {

	@Override
	public List<SearchResult> search(String keyWords) throws Exception {
		keyWords = keyWords.replaceAll(" ", "+");
		List<SearchResult> result = new ArrayList<SearchResult>();
		Document document = Jsoup.connect(SystemCommon.sogouSearch + keyWords)
				.userAgent(UA).get();
		Elements hitsEle = document
				.select(".results .rb");
		for (Element hitEle : hitsEle) {
			String title = hitEle.select(".pt a").text();
			String link = hitEle.select(".pt a").attr("href");
			String content = hitEle.select(".ft").text();
			String datemark = hitEle.select(".fb cite").text();
			String snapshort = hitEle.select(".fb a[id$=sogou_snapshot*]").attr("href");
			SearchResult sr = new SearchResult(title, link, content, datemark,
					snapshort);
			result.add(sr);
		}
		return result;
	}

}
