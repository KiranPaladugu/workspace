/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

public class Response<T> extends Message<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Response() {
		setMessageType(Message.RESPONSE);
	}

	public Response(T message) {
		this();
		setMessage(message);
	}
}
