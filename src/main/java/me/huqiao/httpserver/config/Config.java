package me.huqiao.httpserver.config;

import java.util.HashMap;
import java.util.Map;

import me.huqiao.httpserver.Request;

public class Config {

	final static Config cfg = new Config();
	private Config(){
		servletMapping.put("/servlet/test", "me.huqiao.httpserver.test.servlet.TestServlet");
	}
	public static Config getInstance(){
		return cfg;
	}
	private String baseDir = "D:\\httpserver\\webroot";
	private String defaultIndex = "index.html";
	
	private Map<String,String> servletMapping = new HashMap<String,String>();
	
	/**
	 * 0:info
	 * 1:warn
	 * 2:error
	 * 3:debug
	 */
	private Integer logLevel = 3;
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	public String getDefaultIndex() {
		return defaultIndex;
	}
	public void setDefaultIndex(String defaultIndex) {
		this.defaultIndex = defaultIndex;
	}
	public Integer getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}
	
	public boolean enableLogLevel(int level){
		return logLevel >= level;
	}
	
	public boolean matchServlet(Request request) {
		if(request.getUri()==null){
			return false;
		}
		return getServletClassName(request.getUri())!=null;
	}
	public String getServletClassName(String uri) {
		return servletMapping.get(uri);
	}
	
	public Map<String,String> getServletMapping(){
		return servletMapping;
	}
	
}
