package com.obss.radar.searcher.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.service.IndexService;

@Component
public class BuildIndexTask {
	private static final Logger logger = LoggerFactory
			.getLogger(BuildIndexTask.class);
	@Autowired
	private IndexService indexService;

	public void doTask() {
		try {
			indexService.buildIndex(false);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
}
