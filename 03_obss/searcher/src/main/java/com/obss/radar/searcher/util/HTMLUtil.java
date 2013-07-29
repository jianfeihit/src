package com.obss.radar.searcher.util;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTMLUtil {
	
	public static String getTitle(String page) {
		if(StringUtils.isNotEmpty(page)){
			Document doc = Jsoup.parse(page);
			Elements eles = doc.select("title");
			if(eles == null){
				return "";
			}else{
				return doc.select("title").text();
			}
		}else{
			return "";
		}
	}
	
	public static String suppleTag(String content){
		if(StringUtils.isNotEmpty(content)){
			return Jsoup.parse(content).body().html();
		}else{
			return "";
		}
	}
	
	public static String getHTMLText(String html){
		if(StringUtils.isNotEmpty(html)){
			return Jsoup.parse(html).text();
		}else{
			return "";
		}
	}
}
