package me.huqiao.httpserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import me.huqiao.httpserver.log.Logger;

public class App {
	static Logger log = Logger.getLogger(App.class);
    public static void main( String[] args ) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080,1);
        log.info("Listening on port 8080");
        while(true){
        	Socket socket = serverSocket.accept();
        	InputStream is = socket.getInputStream();
        	Request request = new Request(is);
        	OutputStream os = socket.getOutputStream();
        	Response response = new Response(os);
        	response.setRequest(request);
        	long t = response.sendStaticResource();
        	log.info(request.getPath() + " " + response.getStatusCode() + " RT/" +t+ " Len/" + response.getSentLen());
        	
        	os.flush();
        	os.close();
        }
    }
    
    
}
