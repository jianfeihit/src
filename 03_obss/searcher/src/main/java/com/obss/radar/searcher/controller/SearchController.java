package com.obss.radar.searcher.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.obss.radar.searcher.po.DetailInfo;
import com.obss.radar.searcher.po.SearchResult;
import com.obss.radar.searcher.po.StatisticsInfo;
import com.obss.radar.searcher.service.ISearchService;
import com.obss.radar.searcher.service.SearchService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/search/*")
public class SearchController implements ApplicationContextAware{
	
	private static final String SUCCESS = "1";
	
	private static final String FAILURE = "0";
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService = null;
	
	private ApplicationContext context;
	
	@RequestMapping(value = "/detailSearch", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView detailSearch(@RequestParam(value = "query", required = false, defaultValue = "0") String query,
			@RequestParam(value = "pageStart", required = false, defaultValue = "1") int pageStart,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
		Map<String, Object> respModel = new HashMap<String, Object>();
        try {  
        	query = new String(query.getBytes("ISO-8859-1"),"utf-8");
    		logger.info("detailSearch:query="+query+"|#pageStart="+pageStart+"|#pageSize="+pageSize);
	        List<DetailInfo> result = new ArrayList<DetailInfo>();
	        int total = searchService.detailSearch(result, query, (pageStart-1)*pageSize, pageSize);
	        respModel.put("flag", SUCCESS);
	        respModel.put("total", total);
	        respModel.put("result", result);
        } catch (Exception e) {
	        e.printStackTrace();
	        respModel.put("flag", FAILURE);
	        respModel.put("errorMsg", e.getMessage());
        }
		return new ModelAndView(new MappingJacksonJsonView(), respModel);
	}
	
	@RequestMapping(value = "/dateSearch", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView dateSearch(@RequestParam(value = "query", required = false, defaultValue = "0") String query) {
		Map<String, Object> respModel = new HashMap<String, Object>();
        try {
        	query = new String(query.getBytes("ISO-8859-1"),"utf-8");
        	logger.info("dateSearch:query="+query);
        	ArrayList<StatisticsInfo> result = searchService.dateSearch(query);
	        respModel.put("flag", SUCCESS);
	        respModel.put("result", result);
        } catch (Exception e) {
	        e.printStackTrace();
	        respModel.put("flag", FAILURE);
	        respModel.put("errorMsg", e.getMessage());
        }
		return new ModelAndView(new MappingJacksonJsonView(), respModel);
	}
	
	@RequestMapping(value = "/siteSearch", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView siteSearch(@RequestParam(value = "query", required = false, defaultValue = "0") String query) {
		Map<String, Object> respModel = new HashMap<String, Object>();
        try {
        	query = new String(query.getBytes("ISO-8859-1"),"utf-8");
    		logger.info("siteSearch:query="+query);
        	ArrayList<StatisticsInfo> result = searchService.siteSearch(query);
	        respModel.put("flag", SUCCESS);
	        respModel.put("result", result);
        } catch (Exception e) {
	        e.printStackTrace();
	        respModel.put("flag", FAILURE);
	        respModel.put("errorMsg", e.getMessage());
        }
		return new ModelAndView(new MappingJacksonJsonView(), respModel);
	}
	
	@RequestMapping(value = "/advanceSearch", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView advanceSearch(@RequestParam(value = "basequery", required = false, defaultValue = "0") String baseQuery,
			@RequestParam(value = "extentquery", required = false, defaultValue = "0") String extentquery,
			@RequestParam(value = "type", required = false, defaultValue = "0") int type,
			@RequestParam(value = "site", required = false, defaultValue = "0") String site,
			@RequestParam(value = "pageStart", required = false, defaultValue = "1") int pageStart,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
		Map<String, Object> respModel = new HashMap<String, Object>();
        try {
        	baseQuery = new String(baseQuery.getBytes("ISO-8859-1"),"utf-8");
    		extentquery = new String(extentquery.getBytes("ISO-8859-1"),"utf-8");
    		site = new String(site.getBytes("ISO-8859-1"),"utf-8");
    		logger.info("advanceSearch:baseQuery="+baseQuery+"|#extentquery="+extentquery+"|#type="+type
    				+"|#site="+site+"|#pageStart="+pageStart+"|#pageSize="+pageSize);
    		
        	List<DetailInfo> result = new ArrayList<DetailInfo>();
        	int total = searchService.advanceSearch(result, baseQuery, extentquery, type, site, (pageStart-1)*pageSize, pageSize);
        	respModel.put("flag", SUCCESS);
	        respModel.put("total", total);
	        respModel.put("result", result);
        } catch (Exception e) {
	        e.printStackTrace();
	        respModel.put("flag", FAILURE);
	        respModel.put("errorMsg", e.getMessage());
        }
		return new ModelAndView(new MappingJacksonJsonView(), respModel);
	}
	
	/**
	 * 公网搜索
	 * @param q 搜索词
	 * @param sis 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchInternet", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchInternet(String q,String sis) throws UnsupportedEncodingException{
		q = new String(q.getBytes("ISO-8859-1"),"utf-8");
		sis = new String(sis.getBytes("ISO-8859-1"),"utf-8");
		String[] siArray = sis.split("\\|");
		Map<String, Object> respModel = new HashMap<String, Object>();
		for(String si : siArray){
			ISearchService service = (ISearchService) context.getBean(si+"Search");
			List<SearchResult> result;
			try {
				result = service.search(q);
				respModel.put(si, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return new ModelAndView(new MappingJacksonJsonView(), respModel);
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	
}
