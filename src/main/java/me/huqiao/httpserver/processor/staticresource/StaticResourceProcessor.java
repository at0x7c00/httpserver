package me.huqiao.httpserver.processor.staticresource;

import me.huqiao.httpserver.Request;
import me.huqiao.httpserver.Response;
import me.huqiao.httpserver.processor.Processor;

public class StaticResourceProcessor implements Processor{

	public long doProcess(Request request,Response response) throws Exception{
		return response.sendStaticResource();
	}

}
