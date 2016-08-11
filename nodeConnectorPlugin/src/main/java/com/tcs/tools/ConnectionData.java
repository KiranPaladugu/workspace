/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.util.HashMap;
import java.util.Map;

public class ConnectionData {
	public static enum ConnectionType {
		subsystem, shell
	}

	private String hostname, subsystemName, username, password;
	private final ConnectionType connectionType = ConnectionType.subsystem;
	private int port;
	private Map<String, String> attributes = new HashMap<String, String>();

	public ConnectionData() {
	}

	public ConnectionData(final String hostname, final int port, final ConnectionType channelType, final String subsystem, final String username,
			final String password) {
		super();
		this.hostname = hostname;
		this.subsystemName = subsystem;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	public ConnectionData(final String hostname, final int port, final ConnectionType channelType, final String subsystem, final String username,
			final String password, final Map<String, String> attributes) {
		this(hostname, port, channelType, subsystem, username, password);
		this.attributes = attributes;
	}

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
	public String addAttribute(final String name, final String value) {
		return attributes.put(name, value);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final ConnectionData other = (ConnectionData) obj;
		if (attributes == null) {
			if (other.attributes != null) return false;
		} else if (!attributes.equals(other.attributes)) return false;
		if (hostname == null) {
			if (other.hostname != null) return false;
		} else if (!hostname.equals(other.hostname)) return false;
		if (password == null) {
			if (other.password != null) return false;
		} else if (!password.equals(other.password)) return false;
		if (port != other.port) return false;
		if (subsystemName == null) {
			if (other.subsystemName != null) return false;
		} else if (!subsystemName.equals(other.subsystemName)) return false;
		if (username == null) {
			if (other.username != null) return false;
		} else if (!username.equals(other.username)) return false;
		return true;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public String getHostname() {
		return hostname;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getSubsystemName() {
		return subsystemName;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + port;
		result = prime * result + ((subsystemName == null) ? 0 : subsystemName.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	 *
	 * @param key
	 * @return the previous value associated with key, or null if there was no mapping for key.
	 */
	public String removeAttribute(final String key) {
		return attributes.remove(key);
	}

	public void setAttributes(final Map<String, String> attributes) {
		this.attributes = attributes;
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

	public void setSubsystemName(final String subsystem) {
		this.subsystemName = subsystem;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public boolean validate() {
		if (hostname == null || hostname.length() == 0 || port < 1 || port > 64000 || username == null || username.length() < 1
				|| connectionType == null)
			return false;
		else return true;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

}
