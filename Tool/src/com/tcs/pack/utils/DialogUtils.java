/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.utils;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.tcs.pack.ui.dialogs.AboutDialog;

public class DialogUtils {
	private static AboutDialog about;

	public synchronized static void addEscapeListener(final JDialog dialog) {
		ActionListener escListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		};

		dialog.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}
	public synchronized static void addHideOnEscapeListener(final JDialog dialog) {
		ActionListener escListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		};

		dialog.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	public synchronized static void setCenterLocation(Window window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
	}
	
	public synchronized static void showAboutDialog(Window parent){
		if(about == null){
			about = new AboutDialog(parent);
		}
		about.setVisible(true);
	}
}
