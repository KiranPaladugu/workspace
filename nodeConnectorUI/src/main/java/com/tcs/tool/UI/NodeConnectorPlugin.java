/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import com.tcs.application.Application;
import com.tcs.application.Plugin;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.application.pluign.PluginManager;

public class NodeConnectorPlugin implements Plugin, Subscriber {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
	 */
	private boolean started;
	private String plugId;
	private String name;
	private String identifier;

	@Override
	public void onSubscriptionEvent(final SubscriptionEvent event) {
	}

	public void startUIPlugin() {
		if (!started) {
			final MainWindow window = new MainWindow();
			window.requestFocus();
			window.setVisible(true);
			/*
			 * StartUi starter = new StartUi(window); try { SwingUtilities.invokeAndWait(starter); } catch
			 * (InvocationTargetException e) { e.printStackTrace(); } catch (InterruptedException e) { e.printStackTrace(); }
			 */
			started = true;
			Application.getSubscriptionManager().notifySubscriber(PluginManager.PLUGIN_STARTED, this);
		}
	}

	public void stopPlugin() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#setPlugId(java.lang.String)
	 */
	@Override
	public void setPlugId(final String id) {
		this.plugId = id;
	}

	@Override
	public String getPlugId() {
		return this.plugId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#setIdentifier(java.lang.String)
	 */
	@Override
	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
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
	 * @see com.tcs.application.Plugin#getVersion()
	 */
	@Override
	public String getVersion() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#getClassName()
	 */
	@Override
	public String getClassName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#getActivatorMethod()
	 */
	@Override
	public String getActivatorMethod() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#getDeActivatorMethod()
	 */
	@Override
	public String getDeActivatorMethod() {
		return null;
	}

}
