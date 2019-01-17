package me.huqiao.httpserver;

public class Config {

	final static Config cfg = new Config();
	private Config(){
	}
	public static Config getInstance(){
		return cfg;
	}
	private String baseDir = "D:\\httpserver\\webroot";
	private String defaultIndex = "index.html";
	
	/**
	 * 0:info
	 * 1:warn
	 * 2:error
	 * 3:debug
	 */
	private Integer logLevel = 2;
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
	
}
