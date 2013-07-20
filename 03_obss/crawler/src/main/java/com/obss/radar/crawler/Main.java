package com.obss.radar.crawler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.obss.radar.crawler.service.Startupable;

public class Main {
    protected static Logger logger      = LoggerFactory.getLogger(Main.class);
    /**
     * @param args
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws IOException, SQLException {
    	AbstractApplicationContext  context = new ClassPathXmlApplicationContext("classpath:app-context.xml");
    	
    	Map<String, Startupable> startupBeans = context
		        .getBeansOfType(Startupable.class);
		if (startupBeans != null && startupBeans.size() > 0) {
			for (String key : startupBeans.keySet()) {
				boolean result = startupBeans.get(key).startup();
				logger.info(key + " startup. result=" + result);
			}
		}
    }

}
