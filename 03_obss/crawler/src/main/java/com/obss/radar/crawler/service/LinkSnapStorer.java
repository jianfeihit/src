package com.obss.radar.crawler.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.po.Link;

@Component
public class LinkSnapStorer {
	private static final String ENCODING = "UTF-8";
	@Value("${link.snap.baseDic}")
	private String baseDic;
	
	public String store(Link link,String content) throws IOException{
		String filePath = baseDic+File.separator+link.getSiteId()+File.separator+link.getLinkMD5();
		FileUtils.writeStringToFile(new File(filePath), content, ENCODING);
		return filePath;
	}
}
