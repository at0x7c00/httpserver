package me.huqiao.httpserver.processor.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import me.huqiao.httpserver.Request;
import me.huqiao.httpserver.Response;
import me.huqiao.httpserver.config.Config;
import me.huqiao.httpserver.processor.Processor;

public class ServletProcessor implements Processor{

	@Override
	public long doProcess(Request request, Response response) throws Exception {
		ServletRequest servletRequest = request.coverToServletRequest();
		ServletResponse servletResponse = response.coverToServletResponse();
		
		String servletClassName = Config.getInstance().getServletClassName(request.getUri());
		Servlet servlet = ServletBeanFactory.getInstance().getByName(servletClassName);
		long t = System.currentTimeMillis();
		if(servlet == null){
			throw new RuntimeException("instance of servlet not found:" + servletClassName);
		}else{
			servlet.service(servletRequest, servletResponse);
		}
		t = System.currentTimeMillis() - t;
		return t;
	}

}
