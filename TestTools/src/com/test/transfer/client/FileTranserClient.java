package com.test.transfer.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.test.models.TranferableObject;

public class FileTranserClient {
	private int socket =9070;
	public FileTranserClient(int socket) {
		this.socket = socket;
	}
	
	public void startClient(){
		init();
	}
	
	public int getSocketNumber(){
	    return socket;
	}
	private void init() {
		try {
			Socket client = new Socket("localhost", 9070);
			ObjectInputStream input = new ObjectInputStream(client.getInputStream());
			File file = new File("C:\\Users\\ekirpal\\Downloads\\test\\archetype-catalog.xml");
			int num =1;
			while(file.exists()){
				file = new File("C:\\Users\\ekirpal\\Downloads\\test\\archetype-catalog("+num+").xml");
				num++;
			}
			BufferedOutputStream buffer = new BufferedOutputStream(new FileOutputStream(file, true));
			Object obj = null;
			long totalBytes=0;
			long completed=0;
			while(true){
				if((obj=input.readObject())!=null && obj instanceof TranferableObject){
					TranferableObject tobj = (TranferableObject)obj;
					totalBytes=tobj.getLength();
					completed =completed+tobj.getSize();
					buffer.write(tobj.getData());
					if(tobj.getCount()==2293){
						System.out.println(tobj);
					}
					System.out.println("#"+tobj.getCount()+" Recieved Data : "+tobj.getSize());
				}
				buffer.flush();
				if(completed>=totalBytes){
					break;
				}
			}
			buffer.close();
			input.close();
			client.close();
			System.out.println("Transfer Done to file :"+file.getName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			System.out.println("Exiting clinet");
		}
	}
	
	public static void main(String args[]){
		FileTranserClient client = new FileTranserClient(9070);
		client.startClient();
	}
}
