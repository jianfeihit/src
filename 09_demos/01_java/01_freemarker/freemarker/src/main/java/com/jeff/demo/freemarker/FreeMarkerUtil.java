/**
 *  Copyright (c)  2011-2020 Panguso, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of Panguso, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with Panguso.
 */
package com.jeff.demo.freemarker;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.core.TemplateElement;
import freemarker.template.Template;

/**
 * 
 * @author yangjianfei
 * @date 2013-4-7
 */
public class FreeMarkerUtil {
	/**
	 * 定义正则匹配规则。合法的插值名(包含内建函数的用法)是以${开头，}结尾
	 * 中间一个或多个0-9、a-z、A-Z、-')(@_?!等字符组成
	 */
	private static Pattern pattern = Pattern.compile("(\\$\\{[\\w|.]+\\})");
	
	public static List<String> getParameters(Template template){
		List<String> paraList = new ArrayList<String>();
		TemplateElement templateElement = template.getRootTreeNode();
		for (Enumeration children = templateElement.children(); children.hasMoreElements();) {
		    Object object = children.nextElement();
		    Matcher matcher = pattern.matcher(object.toString());//object为模板上待匹配的一个模块。
		    while (matcher.find()) {
		        String variable = matcher.group(0); //获取正则表达式中的分组。
		        //group(0)表示匹配上的表达式本身。group(1)表示分组1，group(2)表示分组2，以此类推...
		        paraList.add(variable);
		    }
		}
		return paraList;
	}
	
	public static void main(String...args){
		String test = "1122";
		String test1 = "111${ss}2222";
		String test2 = "1111${ss.ss}2222";
		Matcher matcher = pattern.matcher(test2);
		if(matcher.find()){
			int count = matcher.groupCount();
			for(int t=0;t<count;t++){
				System.out.println("t="+t+"|#"+matcher.group(t));
			}
			
		}
		
//		System.out.println(pattern.matcher(test2).groupCount());
	}
}
