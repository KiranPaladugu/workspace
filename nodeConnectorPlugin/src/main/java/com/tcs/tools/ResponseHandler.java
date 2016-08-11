/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import com.tcs.application.BlockingQueue;

public class ResponseHandler implements Runnable {

	private SSHConnection connection;
	private BlockingQueue<Message<String>> messageQueue;
	private Thread t;

	/**
	 * @param messageQueue
	 * 
	 */
	public ResponseHandler(SSHConnection connection, BlockingQueue<Message<String>> messageQueue) {
		this.connection = connection;
		this.messageQueue = messageQueue;
		t = new Thread(this);
		t.setName(connection.getUsername() + "@" + connection.getHostname() + " - " + this.getClass().getSimpleName());
	}

	public void pushReadMessage(String message) {
		try {
			System.out.println("pushing response to queue:" + message);
			messageQueue.insert(new Response<String>(message));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		InputStream inputStream = null;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		StringBuffer buffer = new StringBuffer();
		byte[] bytes = new byte[32 * 1024];
		CharBuffer charBuffer = CharBuffer.allocate(1);
		while (connection.isConnected()) {

			int read = -1;
			try {
				while (inputStream.available() > 0 && (read = inputStream.read(bytes)) != -1) {
					charBuffer = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, 0, read));
					buffer.append(charBuffer.array());

				}
				if (buffer.length() > 0) {
					// System.out.println("Check.."); // System.out.println(" MESSGE: " +buffer.toString());
					if (connection.getEndOfSatement() == null) {
						pushReadMessage(buffer.toString());
						buffer = new StringBuffer();
					} else {
						int index = -1;
						if ((index = buffer.toString().lastIndexOf(connection.getEndOfSatement())) != -1) {
							String remainingmsg = buffer.substring(index + connection.getEndOfSatement().length(), buffer.length());
							pushReadMessage(buffer.substring(0, index + connection.getEndOfSatement().length()));
							connection.recievedMessage++;
							buffer = new StringBuffer();
							if (!remainingmsg.trim().equals("")) {
								buffer.append(remainingmsg);
								System.out.println("Appending reamining:" + remainingmsg);
							}
						}
					}
					continue;
				}

				Thread.sleep(200);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startHandler() {
		t.start();
	}
}
