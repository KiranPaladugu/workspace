/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.io.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jcraft.jsch.*;
import com.tcs.application.BlockingQueue;

public class SSHConnection {
    /**
     * 
     */
    private List<ConnectionListener> listeners = new Vector<ConnectionListener>();
    public static final String subsystem = "subsystem";
    public static final int SUBSYSTEM = 1;
    private int port;
    private String hostname;
    private String username;
    private String password;
    private String subsystemName;
    private String endOfSatement = "";
    private Channel channel;
    private boolean initalized = false;
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;
    private Session session;
    protected int sentMessages;
    protected int recievedMessage;
    private int retryCount = 1;
    private BlockingQueue<Message<String>> requestMessageQueue = new BlockingQueue<Message<String>>(1, "Request Queue");
    private BlockingQueue<Message<String>> responseMessageQueue = new BlockingQueue<Message<String>>(1, "Response Queue");

    public int getSentMessages() {
        return sentMessages;
    }

    public int getRecievedMessage() {
        return recievedMessage;
    }

    public void initialize(String hostname, int port, String username) throws Exception {
        this.username = username;
        this.hostname = hostname;
        this.port = port;
        initialize();
    }

    public void initialize(ConnectionData data) throws Exception {
        this.hostname = data.getHostname();
        this.port = data.getPort();
        this.subsystemName = data.getSubsystem();
        this.username = data.getUsername();
        this.password = data.getPassword();
        initialize();
    }

    public void initialize(String hostname, int port, String username, String password) throws Exception {
        this.password = password;
        initialize(hostname, port, username);

    }

    public void initialize() throws Exception {
        validate();
        JSch sch = new JSch();
        try {
            session = sch.getSession(username, hostname, port);
            if (password != null) {
                session.setPassword(password);
            }
            UserInfo ui = new MyUserInfo() {
                public void showMessage(String message) {
                }

                public boolean promptYesNo(String message) {
                    return true;
                }
                // If password is not given before the invocation of Session#connect(),
                // implement also following methods,
                //   * UserInfo#getPassword(),
                //   * UserInfo#promptPassword(String message) and
                //   * UIKeyboardInteractive#promptKeyboardInteractive()
            };

            session.setUserInfo(ui);
            for (int tryCount = 0; tryCount < retryCount; tryCount++) {
                try {
                    session.connect();
                    break;
                } catch (Exception ex) {
                    if (tryCount >= (retryCount - 1)) {
                        throw ex;
                    }
                }
            }
            ChannelSubsystem channel = (ChannelSubsystem) session.openChannel("subsystem");
            channel.setSubsystem(getSubsystemName());
            //            channel.setPty(true);
            this.channel = channel;
            //            channel.setErrStream(System.err);
            //                        channel.setInputStream(System.in);
            //                        channel.setOutputStream(System.out);
            //            channel.setInputStream(new PipedInputStream(inputPipe));
            //            channel.setOutputStream(new PipedOutputStream(outputPipe));
            //            channel.setInputStream(null);
            responseHandler = new ResponseHandler(this, responseMessageQueue);
            requestHandler = new RequestHandler(this, requestMessageQueue);
            initalized = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 
     */
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

    public void write(String message) {
        try {
            //            inputPipe.write(message.getBytes());
            requestMessageQueue.insert(new Request<String>(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized InputStream getInputStream() throws IOException {
        return this.channel.getInputStream();
    }

    public synchronized OutputStream getOutputStream() throws IOException {
        return this.channel.getOutputStream();
    }

    public synchronized void connect() {
        if (!initalized) {
            try {
                initialize();
            } catch (Exception e) {
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

        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void disConnect() {
        channel.disconnect();
        session.disconnect();
    }

    public synchronized void write(Message<String> mesage) {
        try {
            requestMessageQueue.insert(mesage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized String read() {
        //        System.out.println("Read from queue:"+message);
        try {
            return responseMessageQueue.remove().getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected() {
        return channel.isConnected();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public String getEndOfSatement() {
        return endOfSatement;
    }

    public void setEndOfSatement(String endOfSatement) {
        this.endOfSatement = endOfSatement;
    }

    public ResponseHandler getResponseHandler() {
        return responseHandler;
    }

    protected void setResponseHandler(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    protected void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        if (retryCount <= 0) {
            retryCount = 1;
        }
        this.retryCount = retryCount;
    }

    public synchronized boolean addConnectionListener(ConnectionListener listener) {
        if (!listeners.contains(listener)) {
            return listeners.add(listener);
        }
        return false;
    }

    public synchronized void removeListeners() {
        listeners.clear();
    }

    public synchronized boolean removeListener(ConnectionListener listener) {
        return listeners.remove(listener);
    }

    class ConnectionWatcher extends Thread {
        private boolean sentConnected = false;
        private boolean sentDisconnect = false;
        private AtomicBoolean flag = new AtomicBoolean(true);

        /**
         * 
         */
        public ConnectionWatcher() {
            start();
        }

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
                    //                    System.out.println("Session Heart beat Tum tump Alive:" + channel.isConnected());
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void notifyListeners(int eventType) {
            for (ConnectionListener listener : listeners) {
                listener.onConnectionEvent(new ConnectionEvent(channel, eventType));
            }
        }
    }

}
