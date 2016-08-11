/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import com.tcs.application.Application;
import com.tcs.application.Plugin;
import com.tcs.application.pluign.PluginManager;

public class NetconfNodeConnectorPlugin implements Plugin {

	private String name;
	private String plugId;
	private String identifier;
	private String version;
	private String activatorMethod;
	private String deactivatorMetod;
	private String className;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getActivatorMethod()
	 */
	@Override
	public String getActivatorMethod() {
		return activatorMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getClassName()
	 */
	@Override
	public String getClassName() {
		return className;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getDeActivatorMethod()
	 */
	@Override
	public String getDeActivatorMethod() {
		return deactivatorMetod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getPlugId()
	 */
	@Override
	public String getPlugId() {
		return plugId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#getVersion()
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#setIdentifier(java.lang.String)
	 */
	@Override
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.application.Plugin#setPlugId(java.lang.String)
	 */
	@Override
	public void setPlugId(String id) {
		this.plugId = id;
	}

	public void shutDownConnectorPlugin() {

	}

	public void startConnectorPlugin() {
		Application.getSubscriptionManager().notifySubscriber(PluginManager.PLUGIN_STARTED, this);
	}

}
