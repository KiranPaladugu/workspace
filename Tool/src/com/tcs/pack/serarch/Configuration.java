/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.serarch;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

	private List<PropertyChangeListener> listeneres = new ArrayList<PropertyChangeListener>(); 
	
	protected String key;
	protected Object value;
	
	/**
	 * 
	 */
	public Configuration(String key , Object value) {
		this.key = key;
		this.value=value;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		Object oldValue = this.value;
		this.value = value;
		notifyListeners(oldValue,value);
	}
	
	/**
	 * @param oldValue
	 * @param value2
	 */
	private synchronized void notifyListeners(Object oldValue, Object newValue) {
		synchronized (this) {
			for(PropertyChangeListener listener:this.listeneres){
				listener.propertyChange(new PropertyChangeEvent(this, key, oldValue, newValue));
			}
		}
		
	}

	public void addPropertyChangeListeners(PropertyChangeListener listener){
		synchronized (listeneres) {
			if(!listeneres.contains(listener))
				listeneres.add(listener);
		}
	}
	
}
