package com.lyte.core.api;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Observer;

public interface Plugin extends PropertyChangeListener,Observer {
	
	public static final int PLUGIN_LISTENER=1;
	public static final int PLUGIN_CONTEXT=2;
	public static final int PLUGIN_CONTAINER=3;

	public Context getPluginContext();
	public Configuration getPluginConfiguration();
	public List<Plugin> getPlugins();
	public String getName();
	public int getPluginID();
	public void addContextListener(ContextListener listener);
	public void addPluginListener(PluginEventListener listener);
	public boolean isStarted();
	public void sendPluginEvent(int eventType);
	public void startPlugin();
	public void stopPlugin();
	public void notify(int eventTypte, int toWhome);
}
