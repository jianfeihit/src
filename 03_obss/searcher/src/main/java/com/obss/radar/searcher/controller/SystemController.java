package com.obss.radar.searcher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.obss.radar.searcher.service.IndexService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/system/*")
public class SystemController {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	private static final String INDEX_FULL = "1";
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value = "/buildIndex", method = RequestMethod.GET)
	public String buildIndex(String fullFlag) throws Exception {
		if(INDEX_FULL.equals(fullFlag)){
			logger.info("开始创建全量索引");
			indexService.buildIndex(true);
			logger.info("创建全量索引结束");
		}else{
			logger.info("开始创建增量索引");
			indexService.buildIndex(false);
			logger.info("创建增量索引结束");
		}
		return "home";
	}
	
}
