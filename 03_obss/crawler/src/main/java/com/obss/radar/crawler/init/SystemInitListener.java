package com.obss.radar.crawler.init;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.joran.spi.JoranException;

import com.obss.radar.crawler.po.ProgramException;
import com.obss.radar.crawler.util.LogConfig;

@Component()
public class SystemInitListener {
	private static final Logger logger = LoggerFactory.getLogger(SystemInitListener.class);

	private static String confPath = null;

	@PostConstruct
	public void init() {
		confPath = System.getProperty(SystemCommon.JVM_ENV_KEY);
//		try {
//			LogConfig.config(confPath + File.separator + "logback.xml");
////            XmlFileConfigFactory.init(confPath + File.separator + "config.xml");
//		} catch (JoranException e) {
//			throw new ProgramException(e);
//		}
		logger.info("system init ok.");
	}

}
