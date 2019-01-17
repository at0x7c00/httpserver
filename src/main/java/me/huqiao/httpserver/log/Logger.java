package me.huqiao.httpserver.log;

import java.util.Date;

import me.huqiao.httpserver.Config;
import me.huqiao.httpserver.DateUtil;

public class Logger {
	final static Integer LOG_LEVEL_INFO = 0;
	final static Integer LOG_LEVEL_WARN = 1;
	final static Integer LOG_LEVEL_ERROR = 2;
	final static Integer LOG_LEVEL_DEBUG = 3;
	private Class c;
	private Logger(Class c){
		this.c = c;
	}
	
	public static Logger getLogger(Class c){
		return new Logger(c);
	}
	
	public void info(Object obj){
		if(Config.getInstance().enableLogLevel(LOG_LEVEL_INFO)){
			outputLog(LOG_LEVEL_INFO,obj);
		}
	}
	
	private void outputLog(int logLevel, Object obj) {
		Date now = new Date();
		System.out.print(DateUtil.format(now,"yyyy-MM-dd HH:mm:ss "));
		String logLevelInfo = ""; 
		switch(logLevel){
		case 0:logLevelInfo = "INFO";break;
		case 1:logLevelInfo = "WARN";break;
		case 2:logLevelInfo = "ERROR";break;
		case 3:logLevelInfo = "DEBUG";break;
		}
		System.out.print("[" + logLevelInfo + "] ");
		System.out.print(c.getCanonicalName()+":");
		System.out.println(obj);
	}

	public void debug(Object obj){
		if(Config.getInstance().enableLogLevel(LOG_LEVEL_DEBUG)){
			outputLog(LOG_LEVEL_DEBUG,obj);
		}
	}
	
	public void error(Object obj){
		if(Config.getInstance().enableLogLevel(LOG_LEVEL_ERROR)){
			outputLog(LOG_LEVEL_ERROR,obj);
		}
	}
	
	public void warn(Object obj){
		if(Config.getInstance().enableLogLevel(LOG_LEVEL_WARN)){
			outputLog(LOG_LEVEL_WARN,obj);
		}
		
	}
	
}
