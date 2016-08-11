/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

public class ConnectionEvent {

	public static int CONNECTED = 1;
	public static int DISCONNECTED = -1;
	private Object source;
	private int eventType;

	/**
	 * @param source
	 * @param eventType
	 */
	public ConnectionEvent(Object source, int eventType) {
		super();
		this.source = source;
		this.eventType = eventType;
	}

	public int getEventType() {
		return eventType;
	}

	public Object getSource() {
		return source;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public void setSource(Object source) {
		this.source = source;
	}

}
