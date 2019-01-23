package me.huqiao.httpserver.processor.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

public class ServletBeanFactory {

	Map<String,Servlet> servlets = new HashMap<String,Servlet>();
	
	private static ServletBeanFactory instance = new ServletBeanFactory();
	private ServletBeanFactory(){
		
	}
	
	public static ServletBeanFactory getInstance(){
		return instance;
	}
	
	public Servlet getByName(String className){
		return servlets.get(className);
	}
	
	public void put(String name,Servlet servlet){
		servlets.put(name,servlet);
	}
	
}
