/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ResourceLocator {
	public static URL getResource(String name){
		return ResourceLocator.class.getResource(name);
	}
	
	public static InputStream getResourceAsStream(String name){
		return ResourceLocator.class.getResourceAsStream(name);
	}
	
	public static Properties getPropertiesFromResource(String propertyFileName){
		Properties properties = null;
		InputStream stream = getResourceAsStream(propertyFileName);
		if(stream!=null){
			properties = new Properties();
			try {
				properties.load(stream);
			} catch (IOException e) {
				e.printStackTrace();
				if(properties.isEmpty()){
					properties = null;
				}
			}
		}
		return properties;
	}
}
