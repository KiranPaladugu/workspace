/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tool.ssh;

import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.*;

public class SSHConnection {

    private int port;
    private String host;
    private String username;
    private String password;
    private Map<String, String> options = new HashMap<String, String>();

    /**
     * @param port
     * @param host
     * @param username
     * @param password
     */
    public SSHConnection(int port, String host, String username, String password) {
        super();
        this.port = port;
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        JSch sch = new JSch();
        try {
            Session session = sch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            ChannelSubsystem channel = (ChannelSubsystem) session.openChannel("subsystem");
            channel.setSubsystem("netconf-ecim");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.err);
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
    
    

}
