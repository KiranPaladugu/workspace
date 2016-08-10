/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import javax.swing.Action;
import javax.swing.JMenu;

import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.application.SubscriptionEventDelegator;

public class SMenu extends JMenu implements Subscriber {

	private static final long serialVersionUID = 1L;
	private SubscriptionEventDelegator delegator;

	public SMenu() {
		super();
	}

	public SMenu(final String name) {
		super(name);
	}

	public SMenu(final Action action) {
		super(action);
	}

	public SMenu(final String s, final boolean b) {
		super(s, b);
	}

	@Override
	public void onSubscriptionEvent(final SubscriptionEvent event) {
		if (delegator != null) {
			delegator.onEvent(event, this);
		}
	}

	public SubscriptionEventDelegator getDelegator() {
		return delegator;
	}

	public void setDelegator(final SubscriptionEventDelegator delegator) {
		this.delegator = delegator;
	}

}
