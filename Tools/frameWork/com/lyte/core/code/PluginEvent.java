package com.lyte.core.code;

import com.lyte.core.api.Event;

public class PluginEvent implements Event{
	public static final int PLUGIN_START=1;
	public static final int PLUGIN_STOP=0;
	public static final int PLUGIN_STARTED=2;
	public static final int PLUGIN_STOPPED=3;
	public static final int PLUGIN_EXECUTING=4;
	private int id;
	private int type;
	private Object source;
	
	
	public PluginEvent() {
		super();
	}
	public PluginEvent(int id, int type, Object source) {
		super();
		this.id = id;
		this.type = type;
		this.source = source;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setSource(Object source) {
		this.source = source;
	}
	@Override
	public int getEventType() {
		return type;
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
