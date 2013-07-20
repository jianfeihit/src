package com.obss.radar.crawler.service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.dao.LinkDAO;
import com.obss.radar.crawler.po.Link;

@Component
public class LinkLoader extends Thread implements Startupable{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private LinkedBlockingQueue<Link> queue = null;
	
	@Autowired
	private LinkDAO linkDAO;
	
	@Value("${link.queue.size}")
	private int queueSize = 1000;
	
	private boolean isStop;
	
	public LinkLoader(){
		this.setName("LinkLoader");
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
				List<Link> linkList = linkDAO.getUndoLink(null, 100);
				for(Link link : linkList){
					queue.put(link);
					link.setState(Link.STAR_DOING);
					linkDAO.updateLink(link);
				}
			} catch (Exception e) {
				logger.error("CobWebConsumer has error ", e);
			}
		}
	}
	
	public Link getLink() throws InterruptedException {
		return queue.take();
	}
}
