package me.huqiao.httpserver;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import me.huqiao.httpserver.log.Logger;
import me.huqiao.httpserver.servlet.impl.ServletRequestImpl;
import me.huqiao.httpserver.servlet.impl.ServletResponseImpl;

public class Request {

	static Logger log = Logger.getLogger(Request.class);
	private String method;
	private String protocol;
	private String path;
	private Map<String,String> headers = new HashMap<String,String>();
	private boolean inited = false;
	
	public Request(InputStream is){
		try{
			BufferedInputStream bis = new BufferedInputStream(is);
			int len = bis.available();
			log.debug("is.available():" + len);
			byte[] buffer = new byte[len];
			bis.read(buffer);
			String request = new String(buffer);
			request = request.replaceAll("\r", "");
			int lc = 1;
			log.debug("Request:"+request);
			if(request.trim().equals("")){
				return;
			}
			for(String line : request.split("\n")){
				if(line.trim().equals("")){
					continue;
				}
				if(lc == 1){
					pareFirstLine(line);
				}else{
					if(!line.contains(":")){
						continue;
					}
					String value = line.substring(0,line.indexOf(":"));
					String header = line.substring(line.indexOf(":")+1);
					headers.put(header,value);
				}
				lc++;
			}
			inited = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void pareFirstLine(String line) {
		String[] first = line.split(" ");
		log.debug("HTTP first line:"+line);
		method = first[0];
		protocol = first[2];
		
		path = first[1];
		if(path.contains("?")){
			path = path.substring(0,path.indexOf("?"));
		}
		log.debug("Request path:" + path);
	}

	@Override
	public String toString() {
		return "Request [method=" + method + ", protocol=" + protocol + ", path="
				+ path  + ",headers:" + headers+ "]";
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Deprecated
	public String getPath() {
		return path;
	}
	
	public String getUri() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isInited() {
		return inited;
	}

	public void setInited(boolean inited) {
		this.inited = inited;
	}
	
	public String getHeader(String header){
		return getHeaders().get(header);
	}
	
	public boolean isKeepAlive(){
		return "keep-alive".equals(getHeader("Connection"));
	}

	public ServletRequest coverToServletRequest() {
		ServletRequest req = new ServletRequestImpl(this);
		return req;
	}

}
