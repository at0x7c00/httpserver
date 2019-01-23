package me.huqiao.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletResponse;

import me.huqiao.httpserver.config.Config;
import me.huqiao.httpserver.log.Logger;
import me.huqiao.httpserver.servlet.impl.ServletResponseImpl;

public class Response {
	static Logger log = Logger.getLogger(Response.class);
	private Request request;
	private OutputStream os;
	private Integer statusCode;
	private long sentLen;
	
	public Response(OutputStream os){
		this.os = os;
	}
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	public long sendStaticResource() throws Exception{
		if(!request.isInited()){
			return 0;
		}
		long t = System.currentTimeMillis();
		try{
			String resource = this.getRequest().getPath();
			if(resource.equals("/")){
				resource = "/" + Config.getInstance().getDefaultIndex();
			}
			File file = new File(Config.getInstance().getBaseDir()+resource);
			if(!file.exists()){
				write(404,"<b>404</b> Page not found:" + resource + "<hr/>nutshell v1.0");
			}else{
				writeFile(file);
			}
		}catch(Exception e){
			statusCode(500);
		}
		t = System.currentTimeMillis() - t;
		return t;
	}

	private void write(int code,String content) throws Exception{
		statusCode(code);
		date(new Date());
		writeHeader("Content-Type","text/html");
		writeHeader("Content-Length",content.length() + "");
		writeHeader("Last-Modified",DateUtil.format(new Date()));
		writeHeader("Server","Apache/1.3.3.7 (Unix) (Red-Hat/Linux)");
		writeHeader("Cache-control","no-cache");
		writeHeader("Connection","close");
		write("");
	    write(content);
		sentLen = content.length();
	}

	private void writeFile(File file) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		statusCode(200);
		date(new Date());
		writeHeader("Content-Type",Files.probeContentType(file.toPath()));
		writeHeader("Content-Length",fis.available()+"");
		writeHeader("Last-Modified",DateUtil.format(new Date()));
		writeHeader("Server","Apache/1.3.3.7 (Unix) (Red-Hat/Linux)");
		writeHeader("Cache-control","no-cache");
		writeHeader("Connection","close");
		write("");
		sentLen = fis.available();
		FileUtil.copy(fis, os);
	}

	private void write(String content)throws Exception{
		write(os,content + "\r\n");
	}

	private void writeHeader(String header, String value)throws Exception {
		write(os,header+": "+value+"\r\n");
	}

	private void contentType(String contentType) throws Exception  {
		write(os,"Content-Type: "+contentType+"\r\n");
	}

	private void statusCode(int code) throws Exception {
		write(os,"HTTP/1.0 "+code+" "+codeDescriptionMap.get(code)+"\r\n");
		statusCode = code;
	}
	private void date(Date date) throws Exception {
		write(os,"Date: " + DateUtil.format(date, "EEE,MMM dd yyyy HH:mm:ss z",Locale.ENGLISH)+"\r\n");
	}
	
	private void write(OutputStream os,String str)throws Exception{
		os.write(str.getBytes("UTF-8"));
		os.flush();
	}
	
	static Map<Integer,String> codeDescriptionMap = new HashMap<Integer,String>();
	static {
		codeDescriptionMap.put(200, "OK");
		codeDescriptionMap.put(404, "Not found");
		codeDescriptionMap.put(500, "Server error");
	}
	public Integer getStatusCode() {
		return statusCode;
	}

	public long getSentLen() {
		return sentLen;
	}
	
	public ServletResponse coverToServletResponse() {
		ServletResponse resp = new ServletResponseImpl(this);
		return resp;
	}
	
	public OutputStream getOutputStream(){
		return os;
	}
	
}
