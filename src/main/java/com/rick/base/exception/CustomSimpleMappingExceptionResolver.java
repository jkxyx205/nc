package com.rick.base.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.rick.base.context.Constants;

public class CustomSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	
	private static final transient Logger logger = LoggerFactory.getLogger(CustomSimpleMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage());
		ex.printStackTrace();
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {// JSP格式返回
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
				try {
					PrintWriter writer = response.getWriter();
					response.setStatus(500);
					String contentType = "text/plain;charset="+Constants.ENCODING+"";
					response.setContentType(contentType);  
					//writer.write(ex.getMessage());
					//writer.write("系统错误，请联系管理员");
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;

			}
		} else {
			return null;
		}
	}
}
