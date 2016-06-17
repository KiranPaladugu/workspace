/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.ssh.netconf.connector.node;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ericsson.oss.mediation.transport.ssh.maverick.provider.exception.*;
import com.maverick.ssh.*;
import com.maverick.ssh.PasswordAuthentication;
import com.maverick.ssh2.Ssh2Channel;
import com.maverick.ssh2.Ssh2Session;
import com.sshtools.net.SocketWrapper;
import com.tcs.connector.SessionProvider;

public class SshSessionProvider implements SessionProvider{
    private int port;
    private String host;
    private long timeout;
//    private boolean hostKeyCheck;
    private SocketWrapper socketWrapper;
    private SshConnector sshConnector;
    private SshClient sshClient;
    private String username;
    private boolean isBufferedMode;
    private String password;
    private SshSession sshSession;
    private String subSystem;
    private InputStream stdOut;
    private OutputStream stdIn;
    private final AtomicBoolean askedSessionAbort = new AtomicBoolean(false);

    public SshSessionProvider() {
    }

    /**
     * @param port
     * @param host
     */
    public SshSessionProvider(int port, String host,String username,String password) {
        super();
        this.port = port;
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBufferedMode() {
        return isBufferedMode;
    }

    public void setBufferedMode(boolean isBufferedMode) {
        this.isBufferedMode = isBufferedMode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    public void openSession() {
        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout((int) timeout);
            this.socketWrapper = new SocketWrapper(socket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.sshConnector = SshConnector.createInstance();
            this.sshClient = this.sshConnector.connect(this.socketWrapper, this.username, isBufferedMode);
            int authentication = passwordAuthentication();
            if (authentication != -1) {
                startSessionAndSubsystem(false);
            }
        } catch (SshException e) {
            e.printStackTrace();
        } catch (InitializeSSHSessionException e) {
            e.printStackTrace();
        } catch (TimeoutExpiredException e) {
            e.printStackTrace();
        } catch (ErrorPatternFoundException e) {
            e.printStackTrace();
        }
    }

    private int passwordAuthentication() {
        int authenticationState = -1;
        try {
            final PasswordAuthentication pwd = new PasswordAuthentication();
            pwd.setPassword(this.password);
            authenticationState = this.sshClient.authenticate(pwd); // User authentication
        } catch (final SshException exc) {
            exc.printStackTrace();
            authenticationState = SshAuthentication.FAILED;
        }
        return authenticationState;
    }

    private void startSessionAndSubsystem(final boolean isLongLife)
            throws InitializeSSHSessionException, TimeoutExpiredException, ErrorPatternFoundException {
        try {
            this.sshSession = openSessionChannel(this.sshClient, isLongLife);
            ((Ssh2Session) this.sshSession).startSubsystem(this.subSystem);

            // Getting of the Session Channels OutputStream and InputStream
            this.stdIn = this.sshSession.getOutputStream();
            this.stdOut = this.sshSession.getInputStream();
        } catch (final ChannelOpenException exc) {
            throw new InitializeSSHSessionException("Opening of a SSH Session Channel failed", exc);
        } catch (final SshException exc) {
            throw new InitializeSSHSessionException("Cannot open/configure the SSH Session Channel", exc);
        } catch (SshIOException e) {
            e.printStackTrace();
        }
    }
    
    private SshSession openSessionChannel(final SshClient sshClient, final boolean isLongLife) throws SshException, ChannelOpenException {
        final SshSession sshSession = sshClient.openSessionChannel();
        if (sshSession instanceof Ssh2Channel) {
            ((Ssh2Channel) sshSession).setSendKeepAliveOnIdle(isLongLife);
        }
        return sshSession;
    }
    
    public final boolean isConnected() {
        return (this.sshClient != null && this.sshClient.isConnected() && this.sshSession != null && !sshSession.isClosed());
    }

    public final void closeSession() {
        try {
            if (this.sshSession != null) {
                // Closing of the SSH Session
                this.sshSession.close();
            }
            if (this.sshClient != null) {
                // Disconnecting of the SSH Client
                this.sshClient.disconnect();
            }
            if (this.socketWrapper != null) {
                // Closing of the Socket and Session Channel�s OutputStream and InputStream
                this.socketWrapper.close();
            }
        } catch (final IOException exc) {
        }
    }

    /**
     * 
     * @return
     * @throws SessionProviderException
     */
    public int available() throws SessionProviderException {
        try {
            return this.stdOut.available();
        } catch (final IOException e) {
            throw new SessionProviderException("Error retrieving the bytes available on inputStream: {}", e.getMessage());
        }
    }

    /**
     * 
     * @param bytes
     * @throws AbortRequestException
     * @throws SessionProviderException
     */
    public final void write(final byte... bytes) throws AbortRequestException, SessionProviderException {
        if (isAbortRequested()) {
            throw new AbortRequestException("CLI Session aborted on demand");
        }
        if (isConnected()) {
            try {
                this.stdIn.write(bytes);
                this.stdIn.flush();
            } catch (final IOException exc) {
                throw new SessionProviderException("Sending bytes \"" + Arrays.toString(bytes) + "\" failed", exc);
            }
        } else {
            throw new SessionProviderException("Cannot send bytes \"" + Arrays.toString(bytes) + "\": connection loss");
        }
    }

    /**
     * 
     * @param bytes
     * @param offset
     * @param length
     * @return
     * @throws TimeoutExpiredException
     * @throws AbortRequestException
     * @throws SessionProviderException
     */
    public final int read(final byte[] bytes, final int offset, final int length) throws TimeoutExpiredException, AbortRequestException,
            SessionProviderException {
        if (isAbortRequested()) {
            throw new AbortRequestException("CLI Session aborted on demand");
        }
        try {
            if (bytes.length == 0) {
                return this.stdOut.read();
            } else if (bytes.length == length && offset == 0) {
                return this.stdOut.read(bytes);
            } else {
                return this.stdOut.read(bytes, offset, length);
            }
        } catch (final SshIOException exc) {
            // Handling Timeout error for SSH connection
            final SshException sshException = exc.getRealException();
            if (sshException != null) {
                if ((sshException.getReason() == SshException.SOCKET_TIMEOUT) || (sshException.getReason() == SshException.PROMPT_TIMEOUT)) {
                    throw new TimeoutExpiredException("Timeout expired ", sshException, "");
                } else {
                    closeSession();
                    throw new SessionProviderException("An I/O error is occured during standard output command reading: ", sshException);
                }
            }
            throw new SessionProviderException("An I/O error is occured during standard output command reading.", exc, "");
        } catch (final SocketTimeoutException exc) {
            // Handling Timeout error for Telnet connection
            throw new TimeoutExpiredException("Timeout expired ", exc, "");
        } catch (final IOException exc) {
            // Handling Generic I/O error
            closeSession();
            throw new SessionProviderException("An I/O error is occured during standard output command reading: ", exc);
        }
    }
    
    public boolean isAbortRequested() {
        return this.askedSessionAbort.get();
    }

}
