/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.tools.plugin.xmltreeviewer.tech.SimpleSaxXmlParser;

public class FrontDashBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private final XMLTreeView treeView;
	private final JSplitPane splitView;
	private final Controller controller;
	private final NodeView nodeView;

	public FrontDashBoard() {
		this.setLayout(new GridLayout(1, 1));
		treeView = new XMLTreeView();
		ApplicationContext.putObject(Application.NAVIGATOR, treeView);
		controller = new Controller();
		ApplicationContext.putObject(Application.CONTROLLER, controller);
		nodeView = new NodeView();
		ApplicationContext.putObject(Application.NODEVIEW, nodeView);
		splitView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LayoutUtils.arrangeComponantsInColoumn(treeView).setExpandable(true), nodeView);
		final JPanel panelToAdd = LayoutUtils.arrangeComponantsInRow(controller.setExpandable(true).setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
				LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.BOTH, splitView).setExpandable(true).setExpandPolicy(ControlPanel.BOTH));
		splitView.setDividerSize(2);
		splitView.setDividerLocation(175);
		this.add(panelToAdd);

	}

	public void disableController() {
		this.controller.setVisible(false);
	}

	public void load(final String xml) {
		final SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
		parser.parse(xml);
		treeView.visualizeTree(parser.getNodeModel());
		nodeView.visualize(treeView.getRootXmlElement());
	}

	public void displayXml(final String xml) {
		load(xml);

	}
}
