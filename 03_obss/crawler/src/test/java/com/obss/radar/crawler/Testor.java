package com.obss.radar.crawler;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.obss.radar.crawler.service.Highlight;

public class Testor extends TestCase {
	
	public void test() throws Exception{
		String url = "http://www.jl.gov.cn/zt/jltsczhjs/xwdt/201011/t20101117_897637.html";
		Document document = Jsoup.connect(url).get();
		String text = document.text();
		String digist = text;
		Highlight highlight = new Highlight();
		List<String> keywords = Arrays.asList("吉林","政府");
		for (String keyword : keywords) {
			if (digist.indexOf(keyword) > 0) {
				digist = highlight.lightContent(digist, keyword);
			}
		}
		System.out.println(digist);
	}
}
