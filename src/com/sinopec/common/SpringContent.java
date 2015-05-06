package com.sinopec.common;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/*
 * SPRING容器Bean 获取类
 */
public class SpringContent {
	
	private ServletContext servletContext;
	
	public SpringContent(ServletContext servletContext){
		this.servletContext = servletContext;
	}
	//获取BEAN
	public Object getBean(String beanName){
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return wac.getBean(beanName);
	}
}
