package com.tcs.tools.UI.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class CommonMouseAdaptor extends MouseAdapter {

	private final JPopupMenu popupMenu;

	public CommonMouseAdaptor(final JPopupMenu popup) {
		this.popupMenu = popup;
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		showPopup(e);

	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			popupMenu.setVisible(false);
		}

	}

	private void showPopup(final MouseEvent e) {
		if (e.isPopupTrigger() && popupMenu != null) {
			popupMenu.setVisible(true);
			final JComponent comp = ((JComponent) e.getSource());
			popupMenu.show(comp, e.getX(), e.getY());
		}
	}
}
