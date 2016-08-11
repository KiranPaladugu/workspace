/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.application.conf.ConfigurationManager;
import com.tcs.tools.ConnectionData;
import com.tcs.tools.UI.utils.LayoutUtils;
import com.tcs.tools.UI.utils.UIConstants;
import com.tcs.tools.resources.ResourceLocator;

public class ConnectionView extends ControlPanel implements Subscriber {

	private static final long serialVersionUID = 1L;
	private final JComboBox<String> hostname, port, subsystem, username;
	private final Vector<String> hosts = new Vector<>();
	private final Vector<String> usernames = new Vector<>();
	private final Vector<String> ports = new Vector<>();
	private final Vector<String> subsystems = new Vector<>();
	private final JButton connect, disconnect;
	private boolean updateConf = false;
	private boolean updateHosts, updatePorts, updateSubsystems, updateUsernames;

	private void init() {
		hosts.add("10.170.115.66");
		ports.add("830");
		usernames.add("test");
		subsystems.addAll(Arrays.asList(new String[] { "netconf-ecim", "netconf-yang" }));
		try {
			final ConfigurationManager confMan = Application.getConfigurationManager();
			updateList(hosts, ConfigurationManager.linesAsList(confMan.getConfigurationAsString("ConnectorUI_hosts.conf")));
			updateList(ports, ConfigurationManager.linesAsList(confMan.getConfigurationAsString("ConnectorUI_ports.conf")));
			updateList(usernames, ConfigurationManager.linesAsList(confMan.getConfigurationAsString("ConnectorUI_usernames.conf")));
			updateList(subsystems, ConfigurationManager.linesAsList(confMan.getConfigurationAsString("ConnectorUI_subsystems.conf")));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void updateList(final List<String> toUpdate, final List<String> from) {
		if (from == null || from.isEmpty()) {
			return;
		}
		for (final String str : from) {
			if (toUpdate.contains(str)) {
				continue;
			}
			toUpdate.add(str);
		}
	}

	public ConnectionView() {
		init();
		this.setExpandPolicy(HORIZONTAL_FULL);
		hostname = new JComboBox<>(new DefaultComboBoxModel<String>(hosts));
		hostname.setEditable(true);
		port = new JComboBox<>(new DefaultComboBoxModel<String>(ports));
		port.setEditable(true);
		subsystem = new JComboBox<>(new DefaultComboBoxModel<String>(subsystems));
		subsystem.setEditable(true);
		username = new JComboBox<>(new DefaultComboBoxModel<String>(usernames));
		username.setEditable(true);

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
			updateInputs();
			if (validateInput()) {
				final ConnectionData data = prepareConnection();
				Application.getSubscriptionManager().notifySubscriber(UIConstants.DO_CONNECTION, this, data);
				writeInputAsconf();
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

	private void updateInputs() {
		if (updateComboBox(hostname, hosts)) updateHosts = true;
		if (updateComboBox(port, ports)) updatePorts = true;
		if (updateComboBox(subsystem, subsystems)) updateSubsystems = true;
		if (updateComboBox(username, usernames)) updateUsernames = true;
	}

	private void writeInputAsconf() {
		if (updateConf) {
			final ConfigurationManager confMan = Application.getConfigurationManager();
			try {
				if (updateHosts) confMan.writeStringConfiguration("ConnectorUI_hosts.conf", getListAsData(hosts));
				if (updatePorts) confMan.writeStringConfiguration("ConnectorUI_ports.conf", getListAsData(ports));
				if (updateUsernames) confMan.writeStringConfiguration("ConnectorUI_usernames.conf", getListAsData(usernames));
				if (updateSubsystems) confMan.writeStringConfiguration("ConnectorUI_subsystems.conf", getListAsData(subsystems));
			} catch (final IOException e) {
				e.printStackTrace();
			}
			resetUpdateConfs();
		}
	}

	private void resetUpdateConfs() {
		updateConf = updateHosts = updatePorts = updateUsernames = updateSubsystems = false;

	}

	private String getListAsData(final List<String> list) {
		if (list.isEmpty()) {
			return null;
		}
		final StringBuffer buffer = new StringBuffer();
		for (final String str : list) {
			buffer.append(str + "\n");
		}
		return buffer.toString();
	}

	private boolean updateComboBox(final JComboBox<String> cbx, final Vector<String> vector) {
		final String searchStr = (String) cbx.getModel().getSelectedItem();
		if (!vector.contains(searchStr.trim())) {
			((DefaultComboBoxModel<String>) cbx.getModel()).addElement(searchStr.trim());
			cbx.setSelectedIndex(vector.size() - 1);
			updateConf = true;
			return true;
		}
		return false;
	}

	private ConnectionData prepareConnection() {
		final ConnectionData data = new ConnectionData();
		data.setHostname(((String) hostname.getSelectedItem()).trim());
		data.setPort(Integer.parseInt(((String) port.getSelectedItem()).trim()));
		data.setSubsystemName(((String) subsystem.getSelectedItem()).trim());
		data.setUsername(((String) username.getSelectedItem()).trim());
		data.setPassword(data.getUsername());
		return data;
	}

	private boolean validateInput() {
		boolean flag = true;
		if (hostname.getSelectedIndex() == -1 || port.getSelectedIndex() == -1 || username.getSelectedIndex() == -1
				|| subsystem.getSelectedIndex() == -1) {
			flag = false;
		}
		try {
			Integer.parseInt((String) port.getSelectedItem());
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
