/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.GridLayout;

import javax.swing.JSeparator;

import com.tcs.tools.UI.utils.LayoutUtils;

public class SessionView extends ControlPanel {

	private static final long serialVersionUID = 1L;
	private final HistoryView history;
	private final ConnectionView connection;
	private final RequestControlView reqControl;
	private final MessageDisplayView display;
	private ConnectionManger connectionManager;
	@SuppressWarnings("unused")
	private final NewCoustomMessageView newCoustomMessageView;

	public SessionView() {
		setExpandPolicy(BOTH);
		this.setLayout(new GridLayout());
		setConnectionManager(new ConnectionManger());
		reqControl = new RequestControlView();
		display = new MessageDisplayView();
		history = new HistoryView();
		connection = new ConnectionView();
		newCoustomMessageView = new NewCoustomMessageView();

		// LayoutUtils.arrangeComponantsInRow(connection,reqControl,display).setExpandPolicy(BOTH);
		// LayoutUtils.arrangeComponantsInColoumn(history,LayoutUtils.arrangeComponantsInRow(connection,reqControl,display).setExpandPolicy(BOTH));
		this.add(LayoutUtils.arrangeComponantsInColoumn(history,
				LayoutUtils.arrangeComponantsInRow(connection, reqControl, new JSeparator(), display).setExpandPolicy(BOTH)));
		// LayoutUtils.arrangeCom
		// this.add)
	}

	public ConnectionManger getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(final ConnectionManger connectionManager) {
		this.connectionManager = connectionManager;
	}
}
