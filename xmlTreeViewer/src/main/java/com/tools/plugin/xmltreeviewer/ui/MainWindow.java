/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private final FrontDashBoard board = new FrontDashBoard();
	private final MainWindow window = this;

	public MainWindow() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.getContentPane().add(board);
		WindowUtils.setCenterLocation(this);

	}

	@Override
	public void run() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void displayXml(final String xml) {
		board.disableController();
		if (!this.isVisible()) {
			this.setVisible(true);
		}
		board.displayXml(xml);
	}
}
