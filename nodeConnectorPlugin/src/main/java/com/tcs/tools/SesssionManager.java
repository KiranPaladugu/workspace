/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.util.HashMap;
import java.util.Map;

public class SesssionManager {
	private final Map<String, Object> sessionMap = new HashMap<String, Object>();

	public void destroy(final SSHConnection connection) {

	}

	/**
	 * @param password
	 * @return
	 */
	private String generateSessionKey(final ConnectionData connectionData) {
		return connectionData.getUsername() + ":" + connectionData.getPassword() + '@' + connectionData.getHostname() + ":"
				+ connectionData.getPort();
	}

	public int getSessionCount() {
		return sessionMap.size();
	}

	public SSHConnection newSession(final ConnectionData connectionData) {
		final String key = generateSessionKey(connectionData);
		if (sessionMap.containsKey(key)) {
			// ??
		}
		final SSHConnection connection = new SSHConnection();
		try {
			connection.initialize(connectionData);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		connection.connect();
		sessionMap.put(key, connection);
		return connection;
	}
}
