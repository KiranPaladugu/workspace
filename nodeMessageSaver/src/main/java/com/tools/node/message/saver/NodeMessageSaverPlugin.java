/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.node.message.saver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.tcs.application.Application;
import com.tcs.application.Plugin;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.application.pluign.PluginManager;

public class NodeMessageSaverPlugin implements Plugin, Subscriber, ActionListener {

	private String name;
	private String plugId;
	private String identifier;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Plugin#setPlugId(java.lang.String)
	 */
	@Override
	public void setPlugId(final String id) {
		this.plugId = id;
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
		return this.name;
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

	public void startPlugin() {
		Application.getSubscriptionManager().subscribe(this, this.getClass().getName() + ".OK", this.getClass().getName() + ".RESPONSE");
		final JMenuItem item = new JMenuItem("SaveToFile");
		item.addActionListener(this);
		Application.getSubscriptionManager().notifySubscriber("messageDisplayPopupAddMenu", this, item);
		Application.getSubscriptionManager().notifySubscriber(PluginManager.PLUGIN_STARTED, this);
	}

	public void stopPlugin() {

	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		Application.getSubscriptionManager().notifySubscriber(this.getClass().getName() + ".REQUEST", this, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
	 */
	@Override
	public void onSubscriptionEvent(final SubscriptionEvent arg0) throws Exception {
		final String event = arg0.getEvent();
		final Object data = arg0.getData();
		if (event.equals(this.getClass().getName() + ".OK")) {
			System.out.println("Request SUCESS...");
		} else if (event.equals(this.getClass().getName() + ".RESPONSE")) {
			System.out.println("Got the response..");
			saveToFileDialog((String) data);
		}
	}

	/**
	 * @param data
	 */
	private void saveToFileDialog(final String data) {
		final JFileChooser chooser = new JFileChooser();
		final int selection = chooser.showSaveDialog(null);
		File file = null;
		String cause = "";
		boolean saved = false;
		if (selection == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			try {
				final FileWriter writer = new FileWriter(file);
				writer.write(data);
				writer.flush();
				writer.close();
				saved = true;
				cause = "success";
			} catch (final IOException e) {
				e.printStackTrace();
				cause = e.getMessage();
			}
		}
		if (saved) {
			JOptionPane.showMessageDialog(chooser, "File saved@" + file.getAbsolutePath(), "Save Success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(chooser, "Save Failed for " + file.getAbsolutePath() + "\n Cause:" + cause, "Save Failed!!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public String getActivatorMethod() {
		return null;
	}

	@Override
	public String getDeActivatorMethod() {
		return null;
	}

}
