package com.test.tranfer.server;

import java.io.*;
import java.net.Socket;

import com.test.models.TranferableObject;

public class ClientHandler implements Runnable {

	private File file= new File("C:\\Users\\ekirpal\\Downloads\\archetype-catalog.xml");
	private Socket clientConnection;
	@Override
	public void run() {
		System.out.println("Started Handling Client");
		if(clientConnection !=null){
			try {
				ObjectOutputStream outStream = new ObjectOutputStream(clientConnection.getOutputStream());
				BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(file));
				int size = 512;
				byte bytes[] = new byte[size];
				int count =1;
				int lenght=0;
				TranferableObject tObj ;
				while((lenght=buffer.read(bytes))!=-1){
					tObj= new TranferableObject();
					tObj.setName(file.getName());
					tObj.setLength(file.length());
					tObj.setData(bytes);
					tObj.setCount(count++);
					if(lenght<512){
						System.out.println("Warning : length is less..for count:"+(count-1));
					}
					outStream.writeObject(tObj);
					outStream.flush();
					System.out.println("Sent :"+bytes.length+" @ "+(count-1));
				}
				buffer.close();
				outStream.close();
				System.out.println("Transfer done ...");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

	}

	public void handleClient(Socket clientConnection) {
		this.clientConnection = clientConnection;
		Thread thread = new Thread(this);
		thread.start();
	}

}
