package com.obss.radar.crawler.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Http工具类
 * 
 * @author jianfeihit
 * 
 */
public class HTTPUtils {
	/**
	 * GET方式请求--以字符串的形式返回
	 * 
	 * @param url 请求的url
	 * @return
	 * @throws IOException IO异常
	 */
	public static String getResponseAsStringByGetMethod(String url) throws IOException{
		HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        String html = "";
        int statusCode = httpClient.executeMethod(getMethod);
        // 若不是200，则返回空串
        if (statusCode != HttpStatus.SC_OK) {
            return html;
        }
        // 读取内容
        byte[] responseBody = getMethod.getResponseBody();
        //charset = getMethod.getResponseHeader("Content-Type").getElements()[0].getParameterByName("charset").getValue();
        // 处理内容
        html = new String(responseBody,"utf-8");
        getMethod.releaseConnection();
        return html;
	}
	
	/**
     * 构建url，当url是相对url时使用
     * @param orientHref 原始url
     * @param fullUrl 完整url
     * @return
     * @throws URISyntaxException URI异常
     * @author yangjianfei
     * @date 2012-9-23
     */
	public static String buildHref(String orientHref,String fullUrl){
		try {
			URL url2 = new URL(new URL(fullUrl), orientHref);
			return url2.toString();
		} catch (MalformedURLException e) {
			return "";
		}
    }
	
	public static String getPath(String url){
		int index = url.indexOf("/", 8);
		if(index < 0){
			return "";
		}
		String path = url.substring(index);
		if(url.endsWith("/")){
			path = path+"index.html";
		}
		return path;
	}
	
	public static String getMainHost(String strUrl) throws MalformedURLException{
		URL url = new URL(strUrl);
		String host = url.getHost();
		int index = host.indexOf(".");
		return host.substring(index+1);
	}
	
	
}
