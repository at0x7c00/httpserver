package me.huqiao.httpserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import me.huqiao.httpserver.classloader.ClassLoaderImpl;
import me.huqiao.httpserver.config.Config;
import me.huqiao.httpserver.log.Logger;
import me.huqiao.httpserver.processor.Processor;
import me.huqiao.httpserver.processor.servlet.ServletBeanFactory;
import me.huqiao.httpserver.processor.servlet.ServletProcessor;
import me.huqiao.httpserver.processor.staticresource.StaticResourceProcessor;

public class App {
	static Logger log = Logger.getLogger(App.class);
	boolean shutdown = false;
    public static void main( String[] args ) throws Exception{
    	App httpServer = new App();
    	httpServer.initServlet();
    	httpServer.await(8080);
    }
    
	public void await(int port)throws Exception{
    	ServerSocket serverSocket = new ServerSocket(port,1);
        log.info("Listening on port 8080");
        
        while(!shutdown){
        	Socket socket = serverSocket.accept();
        	InputStream is = socket.getInputStream();
        	Request request = new Request(is);
        	OutputStream os = socket.getOutputStream();
        	Response response = new Response(os);
        	response.setRequest(request);
        	
        	Processor processor = getProcessor(request);
        	long t = processor.doProcess(request, response);
        	
        	log.info(request.getPath() + " " + response.getStatusCode() + " RT/" +t+ " Len/" + response.getSentLen());
        	os.flush();
        	os.close();
        }
        serverSocket.close();
    }
    
    private Processor getProcessor(Request request) {
    	if(Config.getInstance().matchServlet(request)){
    		log.debug("servlet request:" + request.getUri());
    		return new ServletProcessor();
    	}else{
    		log.debug("static resource request:" + request.getUri());
    		return new StaticResourceProcessor();
    	}
	}

	public void shutdown(){
    	shutdown = true;
    }
    
    private void initServlet() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException {
		Map<String,String> servletMapping = Config.getInstance().getServletMapping();
		for(Map.Entry<String,String> entry : servletMapping.entrySet()){
			String className = entry.getValue();
			
			ServletBeanFactory beanFactory = ServletBeanFactory.getInstance();
			Servlet servlet = beanFactory.getByName(className);
			if(servlet==null){
				Class clazz = ClassLoaderImpl.getInstance().loadClass(className);
				Object obj = clazz.newInstance();
				if(!(obj instanceof Servlet)){
					throw new IllegalArgumentException("Class is not Servlet:"+className);
				}
				servlet = (Servlet)obj;
				servlet.init(null);
				beanFactory.put(className, servlet);
				log.info("init Servlet:" + className);
			}
		}
	}
    
}
