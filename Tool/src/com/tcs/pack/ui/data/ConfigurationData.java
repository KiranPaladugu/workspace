/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.ui.data;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationData {

	private Map<String, Object> properties = new HashMap<String, Object>();
	private Map<String,ConfigurationData> configurationData = new HashMap<String,ConfigurationData>();

	/**
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public String getStringProperty(String key) {
		Object val = properties.get(key);
		if (val != null && val instanceof String) {
			return (String) val;
		}
		return null;
	}
	
	public ConfigurationData getConfigurationData(String key){
		return configurationData.get(key);
	}
	
	public Object getProperty(String key){
		return this.properties.get(key);
	}
	
	public void putProperty(String property,Object data){
		this.properties.put(property, data);
	}
	
	public void putConfigurationData(String key,ConfigurationData data){
		this.configurationData.put(key, data);
	}
}
