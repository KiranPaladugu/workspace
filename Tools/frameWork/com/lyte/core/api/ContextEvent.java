package com.lyte.core.api;

public interface ContextEvent extends Event {
	public static final byte CONTEXT_ADDED=1;
	public static final byte CONTEXT_CHANGED=2;
	public static final byte CONTEXT_REMOVED=3; 
}
