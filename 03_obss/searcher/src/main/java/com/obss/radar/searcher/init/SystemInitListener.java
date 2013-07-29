package com.obss.radar.searcher.init;

import java.io.File;

import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import ch.qos.logback.core.joran.spi.JoranException;

import com.obss.radar.searcher.service.ProgramException;
import com.obss.radar.searcher.util.LogConfig;
import com.obss.radar.searcher.util.XmlFileConfigFactory;

public class SystemInitListener extends ContextLoaderListener{
    private static final Logger logger   = LoggerFactory.getLogger(SystemInitListener.class);

    
    public void contextInitialized(ServletContextEvent sce){
		String root = sce.getServletContext().getRealPath("/");
		if (StringUtils.isEmpty(root)) {
			root = System.getProperty(SystemCommon.JVM_ENV_KEY);
		}
		String path = root + "WEB-INF" + File.separator + "conf";
        try {
            LogConfig.config(path + File.separator + "logback.xml");
            XmlFileConfigFactory.init(path + File.separator + "config.xml");
            SystemCommon.init();
        } catch (JoranException e) {
        	e.printStackTrace();
            throw new ProgramException(e);
        }
        logger.info("system init ok.");
    }

}
