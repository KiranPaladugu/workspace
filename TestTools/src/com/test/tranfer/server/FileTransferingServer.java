package com.test.tranfer.server;


public class FileTransferingServer {
	
	public FileTransferingServer() {
		init();
	}

	private void init() {		
		SocketServer server = new SocketServer(9070);
		server.startServer();
	}

	public static void main(String args[]){
		 new FileTransferingServer();
		
	}
}
