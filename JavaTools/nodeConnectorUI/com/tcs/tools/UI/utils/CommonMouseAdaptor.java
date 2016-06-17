package com.tcs.tools.UI.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class CommonMouseAdaptor extends MouseAdapter {

	private JPopupMenu popupMenu;

	public CommonMouseAdaptor(JPopupMenu popup) {
		this.popupMenu = popup;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	
	@Override
	public void mouseReleased(MouseEvent e) {
		showPopup(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			popupMenu.setVisible(false);
		}
			
	}

	/**
	 * @param e
	 */
	private void showPopup(MouseEvent e) {
		if (e.isPopupTrigger() && popupMenu != null) {
			popupMenu.setVisible(true);
			JComponent comp = ((JComponent) e.getSource());
			popupMenu.show(comp, e.getX(), e.getY());
		}
	}
}
