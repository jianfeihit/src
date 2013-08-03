package com.obss.radar.crawler;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.obss.radar.crawler.po.Site;
import com.obss.radar.crawler.service.URLJudger;

public class Testor extends TestCase {
	
	public void test(){
		String url = "http://www.bjld.gov.cn/csibiz/home/static/articles/catalog_77900/2011-11-04/article_ff80808131cb0f4a013203887b370398/ff80808132cceaf801336ca1a15e0097.xls";
		try {
			Document document = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println(e.getMessage().startsWith("Unhandled content type"));
		}
		/*URLJudger urlJudger = new URLJudger();
		Site site = new Site();
		site.setMainHost("bjld.gov.cn");
		Elements linkEles = document.select("a:not(a[href=#])");
		for (Element newlinkEle : linkEles) {
			String newLink = newlinkEle.attr("abs:href");
			if (StringUtils.isNotEmpty(newLink)) {
				if (!urlJudger.judge(newLink, site)) {
					continue;
				}
				System.out.println(newLink);
			}
		}*/
	}
	
	public void test2(){
//		String cols = "汉族、蒙古族、回族、藏族、维吾尔族、苗族、彝族、壮族、布依族、朝鲜族、 满族、侗族、瑶族、白族、土家族、哈尼族、哈萨克族、傣族、黎族、傈僳族、佤族、畲族、高山族、拉祜族、水族、东乡族、纳西族、景颇族、柯尔克孜族、土族、达斡尔族、仫佬族、羌族、布朗族、撒拉族、毛南族、仡佬族、锡伯族、阿昌族、普米族、塔吉克族、怒族、乌孜别克族、俄罗斯族、鄂温克族、德昂族、保安族、裕固族、京族、塔塔尔族、独龙族、鄂伦春族、赫哲族、门巴族、珞巴族、基诺族";
//		for(String col:cols.split("、")){
//			System.out.println("                                  <option value=\""+col+"\">"+col+"</option>");
//		}
	}
}
