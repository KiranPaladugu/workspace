/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.tcs.application.BlockingQueue;

public class RequestHandler implements Runnable {

	private SSHConnection connection;
	private BlockingQueue<Message<String>> messageQueue;
	private Thread t;

	/**
	 * @param messageQueue
	 * 
	 */
	public RequestHandler(SSHConnection connection, BlockingQueue<Message<String>> messageQueue) {
		this.connection = connection;
		this.messageQueue = messageQueue;
		t = new Thread(this);
		t.setName(connection.getUsername() + "@" + connection.getHostname() + " - " + this.getClass().getSimpleName());
	}

	public String getMessageToWirte() {
		Message<String> message = messageQueue.peek();
		if (message != null && message.isRequest()) {
			try {
				return messageQueue.remove().getMessage();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void run() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			while (connection.isConnected()) {
				String message = getMessageToWirte();
				if (message != null && message.length() > 0) {
					System.out.println("Wriging message::: to server");
					System.out.println(message);
					writer.write(message);
					writer.flush();
				} else {
					Thread.sleep(200);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startHandler() {
		t.start();
	}
}
