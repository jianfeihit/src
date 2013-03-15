package com.jeff.demo.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Hello world!
 *
 */
public class FreeMarkerDemo {
	private static Configuration config = new Configuration();
	// ftl文件存放路径
	private static String ftlDic = "D:/workspace/personal/src/trunk/09_demo/01_freemarker/freemarker/src/main/conf";
	
	private static void init() throws IOException{
		config.setDirectoryForTemplateLoading(new File(ftlDic));
		config.setObjectWrapper(new DefaultObjectWrapper());
		config.setSharedVariable("upperFC", new UpperFirstCharacter());
	}
    public static void main( String[] args ) throws IOException, TemplateException{
    	// 1、初始化
    	init();
    	
    	// 2、将map类型的数据和模板拼装
    	Map<String, Object> valueMap = new HashMap<String, Object>();
    	valueMap.put("stringKey", "This is a String key.");
    	valueMap.put("dateKey", new Date());
    	valueMap.put("word", "test upcase the first alphabeta");
    	valueMap.put("testList", Arrays.asList(new String[]{"1","2","3"}));
    	Template temp = config.getTemplate("demo.ftl");
    	ByteArrayOutputStream result = new ByteArrayOutputStream();
    	Writer writer = new OutputStreamWriter(result);
    	temp.process(valueMap, writer);
    	writer.close();
    	System.out.println(result.toString());
    }
}
