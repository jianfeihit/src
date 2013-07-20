package com.obss.radar.crawler.service;

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
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.dao.KeywordPageDAO;
import com.obss.radar.crawler.dao.LinkDAO;
import com.obss.radar.crawler.po.KeywordPage;
import com.obss.radar.crawler.po.Link;
import com.obss.radar.crawler.po.Site;
import com.obss.radar.crawler.util.HTMLUtil;
import com.obss.radar.crawler.util.HTTPUtils;
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

	private boolean isStop = false;

	public boolean startup() {
		this.start();
		return true;
	}

	@Override
	public void run() {
		while (!isStop) {
			try {
				long time1 = System.currentTimeMillis();
				// download link
				Link link = loader.getLink();
				Site site = siteService.getSiteById(link.getSiteId());
				Document document = Jsoup.connect(link.getLink()).userAgent(UA).get();
				String text = document.text();
				String contentMd5 = MD5Util.MD5(text);
				if (contentMd5.equals(link.getContentMD5())) {
					continue;
				}

				long time2 = System.currentTimeMillis();
				// collecte out links
				Elements linkEles = document.select("a:not(a[href=#])");
				for (Element newlinkEle : linkEles) {
					String newLink = newlinkEle.attr("href");
					newLink = HTTPUtils.buildHref(newLink, link.getLink());
					if (StringUtils.isNotEmpty(newLink)) {
						if (!urlJudger.judge(newLink, site)) {
		    				logger.debug("url过滤不通过.newLink={},mainHost={}",newLink,site.getMainHost());
							continue;
						}
						saver.saveLink(link);
//						
					}
				}

				long time3 = System.currentTimeMillis();
				// save snapshort
				String snapPath = storer.store(link, document.html());
				link.setSnapPath(snapPath);

				long time4 = System.currentTimeMillis();
				// check keyword
				if (!Site.MODE_PASSIVE.equals(site.getRunMode())) {
					List<String> hitKeyword = new ArrayList<String>();
					String digist = text;
					for (String keyword : site.getKeywordList()) {
						if (text.indexOf(keyword) > 0) {
							hitKeyword.add(keyword);
							digist = highlight.lightContent(digist, keyword);
						}
					}
					if (hitKeyword.size() > 0) {
						KeywordPage page = new KeywordPage();
						page.setKeyword(StringUtils.join(hitKeyword, "|"));
						page.setDigist(digist);
						page.setLink(link.getLink());
						page.setSiteId(link.getSiteId());
						page.setSnapPath(snapPath);
						page.setTitle(HTMLUtil.getTitle(document.html()));
						keywordPageDAO.saveKeywordPage(page);
					}
				}

				long time5 = System.currentTimeMillis();
				// update link
				link.setContentMD5(contentMd5);
				link.setState(Link.STAR_DONE);
				linkDAO.updateLink(link);
				long time6 = System.currentTimeMillis();
				logger.debug("link={},loadTime={}",link.getLink(),(time6 - time5));
				logger.debug("link={},collecte out links time={}",link.getLink(),(time5 - time4));
				logger.debug("link={},save snapshort time={}",link.getLink(), (time4 - time3));
				logger.debug("link={},check keyword time={}",link.getLink(), (time3 - time2));
				logger.debug("link={},update link time={}",link.getLink(), (time2 - time1));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
