package me.huqiao.httpserver.processor;

import me.huqiao.httpserver.Request;
import me.huqiao.httpserver.Response;

public interface Processor {
	long doProcess(Request request,Response response)throws Exception;
}
