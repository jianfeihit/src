package com.obss.radar.crawler.util;

import java.io.File;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;

public class XmlFileConfigFactory {
	
	private static XMLConfiguration config = null;
	
	public static void init(String configPath){
		try {
			if(StringUtils.isEmpty(configPath)){
				File configFile = new File(XmlFileConfigFactory.class.getResource("/config.xml").getFile()); 
				configPath = configFile.getAbsolutePath();
			}
			config = new XMLConfiguration(configPath);
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (Exception e) {
			System.exit(1);
		}
	}
	
	
	private XmlFileConfigFactory(){
	}

	public static String getString(String configXPath){
		return config.getString(configXPath, null);
	}
	
	
	public static String getString(String configXPath,String defaultValue){
		return config.getString(configXPath, defaultValue);
	}
	
	
	public static int getInt(String configXPath){
		return config.getInt(configXPath);
	}
	
	
	public static float getFloat(String configXPath,float defaultValue){
		return config.getFloat(configXPath);
	}
	
	public static int getInt(String configXPath,int defaultValue){
		return config.getInt(configXPath,defaultValue);
	}
	
	public static long getLong(String configXPath,long defaultValue){
		return config.getLong(configXPath,defaultValue);
	}
	
	public static boolean getBoolean(String configXPath){
		return config.getBoolean(configXPath);
	}
	
	public static boolean getBoolean(String configXPath,boolean defaultValue){
		return config.getBoolean(configXPath, defaultValue);
	}
	
	public static XMLConfiguration getConfig() {
		return config;
	}
	
	public static void main(String[] args){
		init("");
		System.out.println(XmlFileConfigFactory.getString("module.param1"));
	}

}
