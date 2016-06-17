package com.lyte.core.api;

public interface Event {

	public int getEventType();
	public Object getSource();
	public int getEventID();
	
}
