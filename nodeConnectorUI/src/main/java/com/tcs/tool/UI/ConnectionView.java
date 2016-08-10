/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.tools.ConnectionData;
import com.tcs.tools.UI.utils.LayoutUtils;
import com.tcs.tools.UI.utils.UIConstants;
import com.tcs.tools.resources.ResourceLocator;

public class ConnectionView extends ControlPanel implements Subscriber {

	private static final long serialVersionUID = 1L;
	private final JTextField hostname, port, subsystem, username;
	private final JButton connect, disconnect;

	public ConnectionView() {
		this.setExpandPolicy(HORIZONTAL_FULL);
		hostname = new JTextField("10.170.115.66");
		port = new JTextField("830");
		subsystem = new JTextField("netconf-ecim");
		username = new JTextField("test");
		connect = new JButton("Connect");
		connect.setIcon(ResourceLocator.getImageIcon("Connect.png"));
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.getSubscriptionManager().notifySubscriber(UIConstants.PREPARE_CONNECT);
			}
		});
		disconnect = new JButton("Disconnect");
		disconnect.setIcon(ResourceLocator.getImageIcon("Disconnect.png"));
		disconnect.setEnabled(false);
		disconnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.getSubscriptionManager().notifySubscriber(UIConstants.DO_DISCONNECTION);

			}
		});
		this.setLayout(new GridLayout());
		Application.getSubscriptionManager().subscribe(this, UIConstants.CONNECTING, UIConstants.CONNECTION_SUCCESS, UIConstants.DICONNECTED,
				UIConstants.PREPARE_CONNECT);
		this.add(LayoutUtils.arrangeComponantsInColoumn(
				LayoutUtils
						.arrangeComponantsInRow(
								LayoutUtils
										.arrangeComponantsInColoumn(true,
												ControlPanel.HORIZONTAL_FULL,
												LayoutUtils.arrangeComponantsInColoumn("hostname", hostname)
														.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
												LayoutUtils.arrangeComponantsInColoumn("port", port).setExpandPolicy(ControlPanel.TWENTY_PERCENT))
										.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
								LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.HORIZONTAL_FULL,
										LayoutUtils.arrangeComponantsInColoumn("sub-system", subsystem).setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
										LayoutUtils.arrangeComponantsInColoumn("username", username).setExpandPolicy(ControlPanel.HORIZONTAL_FULL))
										.setExpandPolicy(ControlPanel.HORIZONTAL_FULL)),
				LayoutUtils.arrangeComponantsInRow(connect, disconnect).setExpandPolicy(ControlPanel.NONE)));

	}

	@Override
	public void onSubscriptionEvent(final SubscriptionEvent event) {
		switch (event.getEvent()) {
		case UIConstants.PREPARE_CONNECT:
			if (validateInput()) {
				final ConnectionData data = prepareConnection();
				Application.getSubscriptionManager().notifySubscriber(UIConstants.DO_CONNECTION, this, data);
			} else {
				JOptionPane.showMessageDialog(this, "Invalid Input", "Error in validation", JOptionPane.ERROR_MESSAGE);
				break;
			}
		case UIConstants.CONNECTING:
			connect.setEnabled(false);
			disconnect.setEnabled(false);
			setInputPanel(false);
			break;
		case UIConstants.CONNECTION_SUCCESS:
			connect.setEnabled(false);
			disconnect.setEnabled(true);
			setInputPanel(false);
			break;
		case UIConstants.DICONNECTED:
			connect.setEnabled(true);
			disconnect.setEnabled(false);
			setInputPanel(true);
			break;

		}

	}

	private ConnectionData prepareConnection() {
		final ConnectionData data = new ConnectionData();
		data.setHostname(hostname.getText().trim());
		data.setPort(Integer.parseInt(port.getText().trim()));
		data.setSubsystem(subsystem.getText().trim());
		data.setUsername(username.getText().trim());
		data.setPassword(data.getUsername());
		return data;
	}

	private boolean validateInput() {
		boolean flag = true;
		if (hostname.getText().trim().length() < 1 || port.getText().trim().length() < 1 || username.getText().trim().length() < 1
				|| subsystem.getText().trim().length() < 1) {
			flag = false;
		}
		try {
			Integer.parseInt(port.getText());
		} catch (final Exception e) {
			flag = false;
		}

		return flag;
	}

	private void setInputPanel(final boolean enable) {
		hostname.setEnabled(enable);
		port.setEnabled(enable);
		subsystem.setEnabled(enable);
		username.setEnabled(enable);
	}

}
