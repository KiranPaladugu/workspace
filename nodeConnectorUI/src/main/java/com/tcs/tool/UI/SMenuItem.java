/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import javax.swing.JMenuItem;

import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.application.SubscriptionEventDelegator;

public class SMenuItem extends JMenuItem implements Subscriber {

	private static final long serialVersionUID = 1L;
	private SubscriptionEventDelegator delegator;

	@Override
	public void onSubscriptionEvent(final SubscriptionEvent event) {
		if (delegator != null) {
			delegator.onEvent(event, this);
		} else {
			if (event.getSource().equals(this)) {
				return;
			}
		}
	}

	public SubscriptionEventDelegator getDelegator() {
		return delegator;
	}

	public void setDelegator(final SubscriptionEventDelegator delegator) {
		this.delegator = delegator;
	}

}
