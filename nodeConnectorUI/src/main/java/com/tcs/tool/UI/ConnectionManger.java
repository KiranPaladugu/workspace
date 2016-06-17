/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.util.Stack;

import javax.swing.JOptionPane;

import com.tcs.application.*;
import com.tcs.tools.*;
import com.tcs.tools.UI.utils.UIConstants;

public class ConnectionManger implements Subscriber, ConnectionListener {
    private SSHConnection connection;
    private String endOfCommand = "]]>]]>";
    private boolean notifyOnConnecitonLost = false;
    private boolean isConnected;

    public ConnectionManger() {
        Application.getApplicationContext().put("endOfCommand", endOfCommand);
        Application.getSubscriptionManager().subscribe(this, UIConstants.DO_CONNECTION, UIConstants.DO_DISCONNECTION,
                UIConstants.SEND_REQUEST,Application.EXIT);
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        switch (event.getEvent()) {
        case UIConstants.SEND_REQUEST:
            Object obj = event.getData();
            if (connection == null || !connection.isConnected()) {
                JOptionPane.showMessageDialog(null, "Connection is not active!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (obj != null && obj instanceof Message<?>) {
                Message<String> message = (Message<String>) obj;
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
            Object data = event.getData();
            if (data instanceof ConnectionData) {
                ConnectionData conData = (ConnectionData) data;
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
                } catch (Exception e) {
                    //                    e.printStackTrace();
                    connection = null;
                    Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
        case Application.EXIT:
            if(!isConnected){
                break;
            }
            
        case UIConstants.DO_DISCONNECTION:
            System.out.println("Is connectin alive:" + connection.isConnected());
            if (connection != null && connection.isConnected()) {
                this.notifyOnConnecitonLost = false;
                connection.disConnect();
                isConnected = false;
                connection = null;
                Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
            }

        }

    }

    @Override
    public void onConnectionEvent(ConnectionEvent event) {
        if (event.getEventType() == ConnectionEvent.CONNECTED) {

        } else if (event.getEventType() == ConnectionEvent.DISCONNECTED) {
            Application.getSubscriptionManager().notifySubscriber(UIConstants.DICONNECTED, connection);
            if (notifyOnConnecitonLost)
                JOptionPane.showMessageDialog(null, "Connection lost!", "Connection Lost", JOptionPane.WARNING_MESSAGE);
        }
    }

    class ResponseListener implements Runnable, Subscriber {

        private Stack<String> name = new Stack<String>();
        private SSHConnection connection;

        /**
         * 
         */
        public ResponseListener(SSHConnection connection) {
            this.connection = connection;
            Thread t = new Thread(this);
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
                Message<String> message = new Response<String>(connection.read());
                if (name.size() > 0) {
                    message.setName(name.pop());
                }
                Application.getSubscriptionManager().notifySubscriber(UIConstants.RESPONSE, connection, message);
            }
        }

        public synchronized void addRequest(String name) {
            this.name.push(name);
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
         */
        @Override
        public void onSubscriptionEvent(SubscriptionEvent event) {

        }

    }

}
