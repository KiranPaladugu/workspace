/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import javax.swing.JOptionPane;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.tools.UI.utils.UIConstants;

public class DBListener implements Subscriber {

	public DBListener() {
		Application.getSubscriptionManager().subscribe(this, UIConstants.DB_LOAD_FAIL, UIConstants.DB_LOAD_SUCCESS, UIConstants.DB_CREATE_SUCCESS);
	}

	@Override
	public void onSubscriptionEvent(final SubscriptionEvent event) {
		switch (event.getEvent()) {
		case UIConstants.DB_CREATE_SUCCESS:
			System.out.println("db Create Success..");
			break;
		case UIConstants.DB_LOAD_SUCCESS:
			System.out.println("DB loading sucess");
			break;
		case UIConstants.DB_LOAD_FAIL:
			System.out.println("DB LOAD FAIL");
			JOptionPane.showMessageDialog(null, "Data load failed\n Templets and save message feature will not work for this run", "Warning",
					JOptionPane.WARNING_MESSAGE);
		default:
			break;
		}
	}

}
