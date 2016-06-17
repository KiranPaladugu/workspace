package com.tcs.pack.search.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class CommonMouseAdaptor extends MouseAdapter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	/**
	 * 
	 */
	private JPopupMenu popupMenu;

	public CommonMouseAdaptor(JPopupMenu popup) {
		this.popupMenu = popup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("Mouse clicked : from CommonMouseAdaptor.mousePressed()");
//		showPopup(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("Mouse clicked : from CommonMouseAdaptor.mouseCliecked()");
		showPopup(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("Mouse clicked : from CommonMouseAdaptor.mouseReleased(), ButtonPressed"+e.getButton());
//		showPopup(e);
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
