package me.huqiao.httpserver.classloader;

import java.io.File;
import java.io.IOException;

import me.huqiao.httpserver.FileUtil;
import me.huqiao.httpserver.config.Config;
import me.huqiao.httpserver.log.Logger;

public class ClassLoaderImpl extends ClassLoader{
	static Logger log = Logger.getLogger(ClassLoaderImpl.class);
	private String classPath;

	private static ClassLoaderImpl instance = new ClassLoaderImpl(
			Config.getInstance().getBaseDir()+File.separator + "WEB-INF" + File.separator + "classes");
	
	private ClassLoaderImpl(String classPath){
		this.classPath = classPath;
	}
	public static ClassLoader getInstance(){
		return instance;
	} 
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		String fileName = classPath +File.separator+ classNameToFileName(name);
		File file = new File(fileName);
		byte[] buffer;
		Class clazz = null;
		try {
			buffer = FileUtil.read(file);
			int len = buffer.length;
			clazz = defineClass(name,buffer,0,len);
		} catch (IOException e) {
			log.warn("read class file failed:" + e.getMessage());
		}
		if(clazz!=null){
			return clazz;
		}
		return super.findClass(name);
	}
	
	private String classNameToFileName(String name) {
		name = name.replaceAll("\\.", File.separator+File.separator);
		name += ".class";
		return name;
	}
}
