/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.util.Stack;

import javax.swing.JOptionPane;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.tools.ConnectionData;
import com.tcs.tools.ConnectionEvent;
import com.tcs.tools.ConnectionListener;
import com.tcs.tools.Message;
import com.tcs.tools.Response;
import com.tcs.tools.SSHConnection;
import com.tcs.tools.UI.utils.UIConstants;

public class ConnectionManger implements Subscriber, ConnectionListener {
	private SSHConnection connection;
	private final String endOfCommand = "]]>]]>";
	private boolean notifyOnConnecitonLost = false;
	private boolean isConnected;

	public ConnectionManger() {
		Application.getApplicationContext().put("endOfCommand", endOfCommand);
		Application.getSubscriptionManager().subscribe(this, UIConstants.DO_CONNECTION, UIConstants.DO_DISCONNECTION, UIConstants.SEND_REQUEST,
				Application.EXIT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void onSubscriptionEvent(final SubscriptionEvent event) {
		switch (event.getEvent()) {
		case UIConstants.SEND_REQUEST:
			final Object obj = event.getData();
			if (connection == null || !connection.isConnected()) {
				JOptionPane.showMessageDialog(null, "Connection is not active!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (obj != null && obj instanceof Message<?>) {
				final Message<String> message = (Message<String>) obj;
				if (message.getMessage().lastIndexOf(connection.getEndOfSatement()) == -1) {
					System.out.println("Appending end of statement");
					message.setMessage(message.getMessage() + '\n' + connection.getEndOfSatement());
				}
				System.out.println("Sending request:");
				connection.write(message.getMessage() + '\n');
				System.out.println("sent request:");
				message.setMessageType(Message.REQUEST);
				Application.getSubscriptionManager().notifySubscriber(UIConstants.REQUEST, connection, message);
				/*
				 * if (message.isReplyExpected()) { System.out.println("Waiting for reply"); Message<String> respnse = new
				 * Response<String>(connection.read()); respnse.setEndOfMessageDelim(endOfCommand); System.out.println(
				 * "got reply.."); Application.getSubscriptionManager().notifySubscriber(UIConstants.RESPONSE, connection,
				 * respnse); }
				 */
			}
			break;
		case UIConstants.DO_CONNECTION:
			final Object data = event.getData();
			if (data instanceof ConnectionData) {
				final ConnectionData conData = (ConnectionData) data;
				connection = new SSHConnection();
				connection.addConnectionListener(this);
				connection.setEndOfSatement(endOfCommand);
				try {
					notifyOnConnecitonLost = true;
					connection.initialize(conData);
					Application.getSubscriptionManager().notifySubscriber(UIConstants.CONNECTING, connection);
					connection.connect();
					if (connection.isConnected()) {
						isConnected = true;
						Application.getSubscriptionManager().notifySubscriber(UIConstants.CONNECTION_SUCCESS, connection);
					} else {
						Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
					}
					new ResponseListener(connection);
					/*
					 * Message<String> message = new Response<String>(connection.read());
					 * Application.getSubscriptionManager().notifySubscriber(UIConstants.RESPONSE, connection, message);
					 */
				} catch (final Exception e) {
					// e.printStackTrace();
					connection = null;
					Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case Application.EXIT:
			if (!isConnected) {
				break;
			}

		case UIConstants.DO_DISCONNECTION:
			if (connection != null && connection.isConnected()) {
				this.notifyOnConnecitonLost = false;
				Application.getSubscriptionManager().notifySubscriber(UIConstants.PRE_DISCONNECT, connection);

			}
			break;
		case UIConstants.FORCE_DISCONNECT:
			System.out.println("Is connectin alive:" + connection.isConnected());
			if (connection != null && connection.isConnected()) {
				this.notifyOnConnecitonLost = false;
				connection.disConnect();
				isConnected = false;
				connection = null;
				// Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
			}
			break;
		case UIConstants.PRE_DISCONNECT:
		case UIConstants.PRE_DISCONNECT_FAIL:
		case UIConstants.PRE_DISCONNECT_SUCESS:
		}

	}

	@Override
	public void onConnectionEvent(final ConnectionEvent event) {
		if (event.getEventType() == ConnectionEvent.CONNECTED) {

		} else if (event.getEventType() == ConnectionEvent.DISCONNECTED) {
			Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
			if (notifyOnConnecitonLost) JOptionPane.showMessageDialog(null, "Connection lost!", "Connection Lost", JOptionPane.WARNING_MESSAGE);
		}
	}

	class ResponseListener implements Runnable, Subscriber {

		private final Stack<String> name = new Stack<String>();
		private final SSHConnection connection;

		public ResponseListener(final SSHConnection connection) {
			this.connection = connection;
			final Thread t = new Thread(this);
			t.start();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while (this.connection.isConnected()) {
				final Message<String> message = new Response<String>(connection.read());
				if (name.size() > 0) {
					message.setName(name.pop());
				}
				Application.getSubscriptionManager().notifySubscriber(UIConstants.RESPONSE, connection, message);
			}
		}

		public synchronized void addRequest(final String name) {
			this.name.push(name);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
		 */
		@Override
		public void onSubscriptionEvent(final SubscriptionEvent event) {
		}

	}

}
