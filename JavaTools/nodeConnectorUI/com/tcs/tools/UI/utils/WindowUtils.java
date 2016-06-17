/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools.UI.utils;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class WindowUtils {

	public synchronized static void addEscapeListener(final JFrame frame) {
		ActionListener escListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		};

		 frame.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}
	public synchronized static void addHideOnEscapeListener(final JFrame frame) {
		ActionListener escListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		};

		frame.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	public synchronized static void setCenterLocation(Window window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
	}
	
}
