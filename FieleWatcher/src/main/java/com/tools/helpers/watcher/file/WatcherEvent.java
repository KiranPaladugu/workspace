/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.helpers.watcher.file;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WatcherEvent<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CREATED = 1;
    public static final int MODIFIED = 2;
    public static final int DELETED = -1;
    public static final int NO_CHANGE = 0;
    private int eventType = NO_CHANGE;
    private T source;
    private Map<String, Object> properties = new HashMap<String, Object>();

    /**
     * 
     */
    public WatcherEvent(int eventType, T source) {
        this.eventType = eventType;
        this.source = source;
    }
    
    public synchronized int getEventType() {
        return eventType;
    }
    
    public synchronized T getSource(){
        return source;
    }
    
    public synchronized Object getProperty(String property){
        return properties.get(property);
    }
    
    public synchronized Map<String, Object> getAllProperties(){
        return properties;
    }
}
