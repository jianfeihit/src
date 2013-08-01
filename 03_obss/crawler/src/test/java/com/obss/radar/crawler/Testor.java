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
	
	public void test2(){
		String cols = "汉族、蒙古族、回族、藏族、维吾尔族、苗族、彝族、壮族、布依族、朝鲜族、 满族、侗族、瑶族、白族、土家族、哈尼族、哈萨克族、傣族、黎族、傈僳族、佤族、畲族、高山族、拉祜族、水族、东乡族、纳西族、景颇族、柯尔克孜族、土族、达斡尔族、仫佬族、羌族、布朗族、撒拉族、毛南族、仡佬族、锡伯族、阿昌族、普米族、塔吉克族、怒族、乌孜别克族、俄罗斯族、鄂温克族、德昂族、保安族、裕固族、京族、塔塔尔族、独龙族、鄂伦春族、赫哲族、门巴族、珞巴族、基诺族";
		for(String col:cols.split("、")){
			System.out.println("                                  <option value=\""+col+"\">"+col+"</option>");
		}
	}
}
