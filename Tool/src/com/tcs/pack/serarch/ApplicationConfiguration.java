/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.serarch;

import java.util.*;

public class ApplicationConfiguration extends Observable{
	private HashMap<String, Configuration> configMap = new HashMap<String,Configuration>();
	private Observer configObserver;
	public Configuration getConfiguration(String name) {
		return configMap.get(name);
	}
	
	public void setConfigurationObserver(Observer observer){
		this.configObserver = observer;
	}
	
	public void addConfiguration(String name,Configuration config){
		Configuration retObj = this.configMap.put(name, config);
		if(retObj!=null){
			configObserver.update(this, retObj);
		}
	}
}
