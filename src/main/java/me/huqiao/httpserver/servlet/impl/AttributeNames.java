package me.huqiao.httpserver.servlet.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class AttributeNames implements Enumeration<String>{

	private List<String> list = new ArrayList<String>();
	private int current = 0;
	
	public AttributeNames(Map<String,Object> attrs){
		for(Map.Entry<String, Object> entry : attrs.entrySet()){
			list.add(entry.getKey());
		}
	}
	
	@Override
	public boolean hasMoreElements() {
		return current < list.size();
	}

	@Override
	public String nextElement() {
		return list.get(current++);
	}

}
