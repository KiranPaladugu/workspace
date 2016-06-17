/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import javax.swing.*;

import com.tcs.application.*;

public class SMenu extends JMenu implements Subscriber {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SubscriptionEventDelegator delegator;
    
    public SMenu() {
        super();
    }
    
    public SMenu(String name){
        super(name);
    }
    
    public SMenu(Action action){
        super(action);
    }
    
    public SMenu(String s, boolean b){
        super(s,b);
    }
    
    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {
        if(delegator!=null){
            delegator.onEvent(event,this);
        }
    }

    public SubscriptionEventDelegator getDelegator() {
        return delegator;
    }

    public void setDelegator(SubscriptionEventDelegator delegator) {
        this.delegator = delegator;
    }

}
