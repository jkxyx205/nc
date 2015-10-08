package com.rick.base.context;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.util.WebUtils;

public class SpringInit extends ContextLoaderListener {
    
	//private static final transient Logger logger = LoggerFactory.getLogger(SpringInit.class);
	

	public SpringInit() {
        super();
    }
    
    public void contextInitialized(ServletContextEvent event) {
    	try {
    		Constants.realContextPath = WebUtils.getRealPath(event.getServletContext(), File.separator);
    		Constants.contextPath = event.getServletContext().getContextPath();
    		
    		WebUtils.getTempDir(event.getServletContext());
    		
    		super.contextInitialized(event);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
     

    public void contextDestroyed(ServletContextEvent event) {
    	
    }
    
}