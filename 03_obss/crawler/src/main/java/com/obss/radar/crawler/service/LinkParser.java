package com.obss.radar.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.dao.KeywordPageDAO;
import com.obss.radar.crawler.dao.LinkDAO;
import com.obss.radar.crawler.po.KeywordPage;
import com.obss.radar.crawler.po.Link;
import com.obss.radar.crawler.po.Site;
import com.obss.radar.crawler.util.HTMLUtil;
import com.obss.radar.crawler.util.MD5Util;

@Component
public class LinkParser extends Thread implements Startupable {
	private static final String UA = "Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20100101 Firefox/15.0.1";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private URLJudger urlJudger;
	@Autowired
	private SiteService siteService;
	@Autowired
	private LinkSnapStorer storer;
	@Autowired
	private Highlight highlight;
	@Autowired
	private LinkLoader loader;
	@Autowired
	private LinkSaver saver;
	@Autowired
	private LinkDAO linkDAO;
	@Autowired
	private KeywordPageDAO keywordPageDAO;
	
	@Value("${jsoup.timeout}")
	private int timeout = 5000;

	private boolean isStop = false;

	public boolean startup() {
		this.start();
		return true;
	}

	@Override
	public void run() {
		while (!isStop) {
			try {
				// download link
				Link link = loader.getLink();
				Site site = siteService.getSiteById(link.getSiteId());
				Document document = null;
				try{
					document = Jsoup.connect(link.getLink()).timeout(timeout).userAgent(UA).get();
				}catch(IOException ioe){
					if(StringUtils.isNotEmpty(ioe.getMessage()) 
							&& ioe.getMessage().startsWith("Unhandled content type")){// 有附件
						Link parentLink = linkDAO.getLinkByLinkId(link.getParentId());
						if(parentLink != null){
							KeywordPage page = new KeywordPage();
							page.setLink(parentLink.getLink());
							page.setSiteId(link.getSiteId());
							page.setSiteName(site.getSiteName());
							page.setSnapPath(parentLink.getSnapPath());
							page.setWarnType(KeywordPage.WARNTYPE_ATTACH);
							logger.info("因为含有附件而告警.link={}",link.getLink());
							keywordPageDAO.saveKeywordPage(page);
						}
						link.setState(Link.STAR_DONE);
						linkDAO.updateLink(link);
						continue;
					}else{
						throw ioe;
					}
				}
				
				String text = document.text();
				String contentMd5 = MD5Util.MD5(text);
				if (contentMd5.equals(link.getContentMD5())) {
					link.setState(Link.STAR_DONE);
					linkDAO.updateLink(link);
					logger.info("内容未发生变化，不处理.link={}",link.getLink());
					continue;
				}

				// collecte out links
				Elements linkEles = document.select("a:not(a[href=#])");
				for (Element newlinkEle : linkEles) {
					String newLink = newlinkEle.attr("abs:href");
					if (StringUtils.isNotEmpty(newLink)) {
						if (!urlJudger.judge(newLink, site)) {
		    				logger.debug("url过滤不通过.newLink={},mainHost={}",newLink,site.getMainHost());
							continue;
						}
						Link toDBLink = new Link(site.getId(),newLink);
						toDBLink.setParentId(link.getId());
						saver.saveLink(toDBLink);
					}
				}

				// save snapshort
				String snapPath = storer.store(link, document.html());
				link.setSnapPath(snapPath);

				// check keyword
				if (!Site.MODE_PASSIVE.equals(site.getRunMode())) {
					List<String> hitKeyword = new ArrayList<String>();
					for (String keyword : site.getKeywordList()) {
						if (text.indexOf(keyword) >= 0) {
							hitKeyword.add(keyword);
						}
					}
					if (hitKeyword.size() > 0) {
						String digist = highlight.lightContent(text, StringUtils.join(hitKeyword, " "));
						KeywordPage page = new KeywordPage();
						page.setKeyword(StringUtils.join(hitKeyword, "|"));
						page.setDigist(digist);
						page.setLink(link.getLink());
						page.setSiteId(link.getSiteId());
						page.setSiteName(site.getSiteName());
						page.setSnapPath(snapPath);
						page.setTitle(HTMLUtil.getTitle(document.html()));
						keywordPageDAO.saveKeywordPage(page);
					}
				}

				// update link
				link.setContentMD5(contentMd5);
				link.setState(Link.STAR_DONE);
				linkDAO.updateLink(link);
				logger.info("处理完成.link={}",link.getLink());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
