/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSubsystem;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.tcs.application.BlockingQueue;
import com.tcs.tools.ConnectionData.ConnectionType;

public class SSHConnection {
	class ConnectionWatcher extends Thread {
		private boolean sentConnected = false;
		private boolean sentDisconnect = false;
		private final AtomicBoolean flag = new AtomicBoolean(true);

		/**
		 *
		 */
		public ConnectionWatcher() {
			start();
		}

		private void notifyListeners(final int eventType) {
			for (final ConnectionListener listener : listeners) {
				listener.onConnectionEvent(new ConnectionEvent(channel, eventType));
			}
		}

		@Override
		public void run() {
			while (flag.get()) {
				if (channel != null) {
					if (channel.isConnected()) {
						if (!sentConnected) {
							sentConnected = true;
							sentDisconnect = false;
							notifyListeners(ConnectionEvent.CONNECTED);
						}
					} else {
						if (!sentDisconnect) {
							sentConnected = false;
							sentDisconnect = true;
							flag.set(false);
							notifyListeners(ConnectionEvent.DISCONNECTED);
						}
					}
					// System.out.println("Session Heart beat Tum tump Alive:" + channel.isConnected());
				}
				try {
					Thread.sleep(5000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static final String subsystem = "subsystem";
	public static final int SUBSYSTEM = 1;
	/**
	 *
	 */
	private final List<ConnectionListener> listeners = new Vector<ConnectionListener>();
	private int port;
	private String hostname;
	private String username;
	private String password;
	private String subsystemName;
	private final ConnectionType channelType = ConnectionType.subsystem;
	private String endOfSatement = "";
	private Channel channel;
	private boolean initalized = false;
	private RequestHandler requestHandler;
	private ResponseHandler responseHandler;
	private Session session;
	protected int sentMessages;
	protected int recievedMessage;
	private int retryCount = 1;
	private final BlockingQueue<Message<String>> requestMessageQueue = new BlockingQueue<Message<String>>(1, "Request Queue");

	private final BlockingQueue<Message<String>> responseMessageQueue = new BlockingQueue<Message<String>>(1, "Response Queue");

	public synchronized boolean addConnectionListener(final ConnectionListener listener) {
		if (!listeners.contains(listener)) {
			return listeners.add(listener);
		}
		return false;
	}

	public synchronized void connect() {
		if (!initalized) {
			try {
				initialize();
			} catch (final Exception e) {
				e.printStackTrace();
				return;
			}
		}
		try {
			channel.connect();
			if (channel.isConnected()) {
				responseHandler.startHandler();
				requestHandler.startHandler();
			}
			new ConnectionWatcher();

		} catch (final JSchException e) {
			e.printStackTrace();
		}
	}

	public void disConnect() {
		channel.disconnect();
		session.disconnect();
	}

	public String getEndOfSatement() {
		return endOfSatement;
	}

	public String getHostname() {
		return hostname;
	}

	public synchronized InputStream getInputStream() throws IOException {
		return this.channel.getInputStream();
	}

	public synchronized OutputStream getOutputStream() throws IOException {
		return this.channel.getOutputStream();
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public int getRecievedMessage() {
		return recievedMessage;
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public int getSentMessages() {
		return sentMessages;
	}

	public String getSubsystemName() {
		return subsystemName;
	}

	public String getUsername() {
		return username;
	}

	public void initialize() throws Exception {
		validate();
		final JSch sch = new JSch();
		try {
			session = sch.getSession(username, hostname, port);
			if (password != null) {
				session.setPassword(password);
			}
			final UserInfo ui = new MyUserInfo() {
				@Override
				public boolean promptYesNo(final String message) {
					return true;
				}
				// If password is not given before the invocation of Session#connect(),
				// implement also following methods,
				// * UserInfo#getPassword(),
				// * UserInfo#promptPassword(String message) and
				// * UIKeyboardInteractive#promptKeyboardInteractive()

				@Override
				public void showMessage(final String message) {
				}
			};

			session.setUserInfo(ui);
			for (int tryCount = 0; tryCount < retryCount; tryCount++) {
				try {
					session.connect();
					break;
				} catch (final Exception ex) {
					if (tryCount >= (retryCount - 1)) {
						throw ex;
					}
				}
			}

			this.channel = prepareChannel();
			if (channel == null) {
				return;
			}
			responseHandler = new ResponseHandler(this, responseMessageQueue);
			requestHandler = new RequestHandler(this, requestMessageQueue);
			initalized = true;
		} catch (final Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private Channel prepareChannel() throws JSchException {
		switch (channelType) {
		case subsystem:
			return prepareSubsystemChannel();
		case shell:
			return prepareShellChannel();
		default:
			break;
		}
		return null;
	}

	private Channel prepareShellChannel() throws JSchException {
		final ChannelExec channel = (ChannelExec) session.openChannel("exec");
		return channel;
	}

	private ChannelSubsystem prepareSubsystemChannel() throws JSchException {
		final ChannelSubsystem channel = (ChannelSubsystem) session.openChannel(channelType.toString());
		channel.setSubsystem(getSubsystemName());
		return channel;
	}

	public void initialize(final ConnectionData data) throws Exception {
		this.hostname = data.getHostname();
		this.port = data.getPort();
		this.subsystemName = data.getSubsystemName();
		this.username = data.getUsername();
		this.password = data.getPassword();
		initialize();
	}

	public boolean isConnected() {
		return channel.isConnected();
	}

	public synchronized String read() {
		// System.out.println("Read from queue:"+message);
		try {
			return responseMessageQueue.remove().getMessage();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized boolean removeListener(final ConnectionListener listener) {
		return listeners.remove(listener);
	}

	public synchronized void removeListeners() {
		listeners.clear();
	}

	public void setEndOfSatement(final String endOfSatement) {
		this.endOfSatement = endOfSatement;
	}

	public void setHostname(final String hostname) {
		this.hostname = hostname;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	protected void setRequestHandler(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	protected void setResponseHandler(final ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

	public void setRetryCount(int retryCount) {
		if (retryCount <= 0) {
			retryCount = 1;
		}
		this.retryCount = retryCount;
	}

	private void setSubsystemName(final String subsystemName) {
		this.subsystemName = subsystemName;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	private void validate() {
		if (hostname == null || hostname.length() <= 0) {
			throw new IllegalArgumentException("hostname");
		}

		if (port <= 0) {
			throw new IllegalArgumentException("Invalid port number");
		}

		if (username == null || username.length() <= 0) {
			throw new IllegalArgumentException("username");
		}

		if (password == null || password.length() <= 0) {
			throw new IllegalArgumentException("password");
		}
	}

	public synchronized void write(final Message<String> mesage) {
		try {
			requestMessageQueue.insert(mesage);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void write(final String message) {
		try {
			// inputPipe.write(message.getBytes());
			requestMessageQueue.insert(new Request<String>(message));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
