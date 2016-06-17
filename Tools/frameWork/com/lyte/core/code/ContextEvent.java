package com.lyte.core.code;

public class ContextEvent implements com.lyte.core.api.ContextEvent {

	private int eventType;
	private Object source;
	private int id;
	 public ContextEvent(Object source,int eventType,int id) {
	}
	@Override
	public int getEventType() {
		return eventType;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public int getEventID() {
		return id;
	}

}
