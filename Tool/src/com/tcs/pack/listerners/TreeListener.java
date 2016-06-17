/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.listerners;

import java.awt.event.*;

import javax.swing.*;

import com.tcs.pack.search.ui.SearchTreeNode;
import com.tcs.pack.windows.TextViewWindow;

public class TreeListener extends MouseAdapter {
	private JTree _Tree;
	private boolean singleClick = true;
	private int doubleClickDelay = 400;
	private Timer timer;

	public TreeListener(JTree tree) {
		this._Tree = tree;
		ActionListener actionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				timer.stop();
				if (singleClick) {
					singleClickHandler(e);
				} else {
					doubleClickHandler(e);
				}

			}
		};
		timer = new javax.swing.Timer(doubleClickDelay, actionListener);
		timer.setRepeats(false);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			singleClick = true;
			timer.start();
		} else {
			singleClick = false;
		}
	}

	private void singleClickHandler(ActionEvent e) {
		// System.out.println("-- single click --");
	}

	private void doubleClickHandler(ActionEvent e) {
		if (_Tree.getSelectionPath().getLastPathComponent() instanceof SearchTreeNode) {
			final SearchTreeNode node = (SearchTreeNode) _Tree.getSelectionPath().getLastPathComponent();
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					new TextViewWindow(node);
				}
			});
		}

	}

}