package com.obss.radar.crawler.service;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.dao.LinkDAO;
import com.obss.radar.crawler.po.Link;

@Component
public class LinkSaver extends Thread implements Startupable{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private volatile LinkedBlockingQueue<Link> queue = null;
	
	@Autowired
	private LinkDAO linkDAO;
	
	@Value("${link.queue.size}")
	private int queueSize = 1000;
	
	private boolean isStop;
	
	public LinkSaver(){
		this.setName("LinkSaver");
		queue = new LinkedBlockingQueue<Link>(queueSize);
	}
	
	public boolean startup() {
		this.start();
		return true;
    }

	@Override
	public void run() {
		while (!isStop) {
			try {
				Link link = queue.take();
				linkDAO.saveLink(link);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("CobWebConsumer has error ", e);
			}
		}
	}
	
	public void saveLink(Link link) throws InterruptedException {
		if(!queue.contains(link)){
			queue.put(link);
		}
	}
}
