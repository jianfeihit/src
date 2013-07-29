package com.obss.radar.searcher.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.init.SystemCommon;
import com.obss.radar.searcher.po.SearchResult;

@Component(value="360Search")
public class QihuSearchService implements ISearchService {

	@Override
	public List<SearchResult> search(String keyWords) throws Exception {
		keyWords = keyWords.replaceAll(" ", "+");
		List<SearchResult> result = new ArrayList<SearchResult>();
		Document document = Jsoup.connect(SystemCommon.qihuSearch + keyWords)
				.userAgent(UA).get();
		Elements hitsEle = document
				.select("#m-result .res-list");
		for (Element hitEle : hitsEle) {
			String title = hitEle.select(".res-title  a").text();
			if(StringUtils.isEmpty(title)){
				continue;
			}
			String link = hitEle.select(".res-title  a").attr("href");
			String content = hitEle.select(".res-desc ").text();
			String datemark = hitEle.select(".res-linkinfo cite").text();
			String snapshort = hitEle.select(".res-linkinfo a").attr("href");
			SearchResult sr = new SearchResult(title, link, content, datemark,
					snapshort);
			result.add(sr);
		}
		return result;
	}

}
