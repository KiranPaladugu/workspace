/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pattern.observer;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

public class SearchPathUpdateHandler implements Observer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */

	private JLabel status;

	/**
	 * 
	 */
	public SearchPathUpdateHandler(final JLabel status) {
		this.status = status;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg == null)
			return;
		synchronized (status) {
			status.setText("Searching :"+arg.toString());
		}
	}
	
	

}
