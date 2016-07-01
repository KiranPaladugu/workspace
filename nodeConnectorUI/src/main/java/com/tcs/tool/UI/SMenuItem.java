/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import javax.swing.JMenuItem;

import com.tcs.application.*;

public class SMenuItem extends JMenuItem implements  Subscriber {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SubscriptionEventDelegator delegator;

    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {
        if (delegator != null) {
            delegator.onEvent(event,this);
        }else{
            if(event.getSource().equals(this)){
                return;
            }
        }        
    }

    public SubscriptionEventDelegator getDelegator() {
        return delegator;
    }

    public void setDelegator(SubscriptionEventDelegator delegator) {
        this.delegator = delegator;
    }

}
