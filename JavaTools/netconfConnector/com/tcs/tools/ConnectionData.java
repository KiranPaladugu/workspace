/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.util.HashMap;
import java.util.Map;

public class ConnectionData {
    private String hostname, subsystem, username, password;
    private int port;
    private Map<String, String> attributes = new HashMap<String, String>();

    /**
     * Adds new attribute.
     * 
     * @param name
     *            attribute name
     * @param value
     *            attribute value
     * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate
     *         that the map previously associated null with key, if the implementation supports null values.)
     */
    public String addAttribute(String name, String value) {
        return attributes.put(name, value);
    }

    /**
     * 
     * @param key
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    public String removeAttribute(String key) {
        return attributes.remove(key);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + port;
        result = prime * result + ((subsystem == null) ? 0 : subsystem.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConnectionData other = (ConnectionData) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        if (hostname == null) {
            if (other.hostname != null)
                return false;
        } else if (!hostname.equals(other.hostname))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (port != other.port)
            return false;
        if (subsystem == null) {
            if (other.subsystem != null)
                return false;
        } else if (!subsystem.equals(other.subsystem))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    public boolean validate() {
        if (hostname == null || hostname.length() == 0 || port < 1 || port > 64000 || username == null || username.length() < 1)
            return false;
        else
            return true;
    }

}
