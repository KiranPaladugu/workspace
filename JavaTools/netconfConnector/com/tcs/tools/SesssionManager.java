/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.util.HashMap;
import java.util.Map;

public class SesssionManager {
    private Map<String, Object> sessionMap = new HashMap<String, Object>();

    public SSHConnection newSession(String host, int port, String username, String password) {
        String key = generateSessionKey(host, port, username, password);
        if(sessionMap.containsKey(key)){
            //??
        }
        SSHConnection connection = new SSHConnection();
        try {
            connection.initialize(host, port, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.connect();
        sessionMap.put(key, connection);
        return connection;
    }
    
    public int getSessionCount(){
        return sessionMap.size();
    }
    
    public void destroy(SSHConnection connection){
        
    }

    /**
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    private String generateSessionKey(String host, int port, String username, Object password) {
        return username+":"+password+'@'+host+":"+port;
    }
}
