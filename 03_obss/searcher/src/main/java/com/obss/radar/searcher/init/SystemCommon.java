package com.obss.radar.searcher.init;

import com.obss.radar.searcher.util.XmlFileConfigFactory;


public class SystemCommon {
	public static final String JVM_ENV_KEY = "searcher";
	
	public static final String DEFAULT_ENCODE = "utf-8";

	public final static String TIME_FORMAT_YYYYMMDD = "yyyyMMdd";
	
	public static String indexBasepath;
	
	public static int abstractLength = 200;
	
	public static String baiduSearch = "";
	
	public static String bingSearch = "";
	
	public static String googleSearch = "";
	
	public static String sogouSearch = "";
	
	public static String qihuSearch = "";
	
	
	public static void init(){
		abstractLength = XmlFileConfigFactory.getInt("abstractLength",200);
		indexBasepath = XmlFileConfigFactory.getString("indexBasepath");
		baiduSearch = XmlFileConfigFactory.getString("baiduSearch");
		bingSearch = XmlFileConfigFactory.getString("bingSearch");
		googleSearch = XmlFileConfigFactory.getString("googleSearch");
		sogouSearch = XmlFileConfigFactory.getString("sogouSearch");
		qihuSearch = XmlFileConfigFactory.getString("qihuSearch");
	}
}
