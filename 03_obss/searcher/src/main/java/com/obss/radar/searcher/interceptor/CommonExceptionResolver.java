package com.obss.radar.searcher.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class CommonExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse resonpnse,
	        Object object, Exception ex) {
		ex.printStackTrace();
		ModelAndView model = new ModelAndView();
		model.addObject("result", "1");
		String errorCode = null;
		String errorMessage = null;

		errorCode = "E400899";
		errorMessage = "系统内部错误";
		logger.error(ex.getMessage(), ex);
		model.addObject("errorCode", errorCode);
		model.addObject("errorMessage", "[" + MDC.get("SeqId") + "]" + errorMessage);

		model.setView(new MappingJacksonJsonView());
		resonpnse.setStatus(500);
		return model;
	}
}
