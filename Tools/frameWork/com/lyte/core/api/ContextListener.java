package com.lyte.core.api;

public interface ContextListener extends Listener {
	public void contextChanged(ContextEvent event);
	public void contextAdded(ContextEvent event);
	public void contextRemoved(ContextEvent event);
	
}
