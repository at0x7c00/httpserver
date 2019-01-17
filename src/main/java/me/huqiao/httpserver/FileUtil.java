package me.huqiao.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	public static void copy(InputStream in,OutputStream out)throws IOException{
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = in.read(buffer))>0){
			out.write(buffer, 0, len);
		}
	}
}
