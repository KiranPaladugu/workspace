package com.lyte.core.code;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import com.lyte.core.api.Configuration;
import com.lyte.core.api.Context;
import com.lyte.core.api.ContextListener;
import com.lyte.core.api.Plugin;
import com.lyte.core.api.PluginEventListener;

public abstract class AbstractPlugin implements Plugin {

    private Context context;
    private Configuration config;
    private String name;
    private int id;
    private List<PluginEventListener> pluginListeners = new Vector<PluginEventListener>(1);
    private List<ContextListener> contextlisteners = new Vector<ContextListener>(1);
    private boolean started;

    public AbstractPlugin() {
    }

    public void startPlugin() {
        if (!started)
            sendPluginEvent(PluginEvent.PLUGIN_START);
        else {
            throw new IllegalStateException("Plugin already started");
        }
        setPluginStarted();
    }

    @Override
    public Context getPluginContext() {
        return context;
    }

    @Override
    public Configuration getPluginConfiguration() {
        return config;
    }

    @Override
    public List<Plugin> getPlugins() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPluginID() {
        return id;
    }

    @Override
    public void addContextListener(ContextListener listener) {
        this.contextlisteners.add(listener);

    }

    @Override
    public void addPluginListener(PluginEventListener listener) {
        this.pluginListeners.add(listener);
    }

    public void sendPluginEvent(int eventType) {
        if (!pluginListeners.isEmpty()) {
            for (PluginEventListener lister : this.pluginListeners) {
                new ForkedEventSender(lister, new PluginEvent(0, eventType, this));
            }
        }
    }

    @Override
    public abstract void notify(int eventTypte, int toWhome);

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == PluginConstants.PLUGIN_STATUS) {
            if (evt.getNewValue() == PluginConstants.PLUGIN_STARTED) {
                setPluginStarted();
            }
        }

    }

    @Override
    public void update(Observable observable, Object arg) {

    }

    @Override
    public boolean isStarted() {
        return started;
    }

    protected void setPluginStarted() {
        if (!started) {
            started = true;
            sendPluginEvent(PluginEvent.PLUGIN_STARTED);
        }
    }

    @Override
    public void stopPlugin() {
        
    }

}
