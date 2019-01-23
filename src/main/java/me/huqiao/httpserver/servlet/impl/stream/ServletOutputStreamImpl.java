package me.huqiao.httpserver.servlet.impl.stream;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ServletOutputStreamImpl extends ServletOutputStream{

	private OutputStream os;
	public ServletOutputStreamImpl(OutputStream os){
		this.os = os;
	}
	
	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		
	}

	@Override
	public void write(int b) throws IOException {
		os.write(b);
	}

}
