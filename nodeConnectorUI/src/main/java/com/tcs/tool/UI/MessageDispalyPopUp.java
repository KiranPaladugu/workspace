/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import com.tcs.application.*;
import com.tcs.tools.UI.utils.UIConstants;

public class MessageDispalyPopUp extends JPopupMenu implements Subscriber {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Map<Object, String> sourceMap = new HashMap<>();

    /**
     * 
     */
    public MessageDispalyPopUp() {
        Application.getSubscriptionManager().subscribe(this, UIConstants.MESSAGE_DISPLAY_POPUP_ADD_MENU,
                UIConstants.MESSAGE_DISPLAY_POPUP_ADD_SEPERATOR);
    }

    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {
        Object data = event.getData();
        switch (event.getEvent()) {
        case UIConstants.MESSAGE_DISPLAY_POPUP_ADD_MENU:
            if (data != null) {
                if (data instanceof JMenuItem) {
                    this.add((JMenuItem) data);
                    sourceMap.put(data, event.getCaller().getClassName());
                    Application.getSubscriptionManager().notifySubscriber(event.getCaller().getClassName()+".OK", this, null);
                    Application.getSubscriptionManager().subscribe(this, event.getCaller().getClassName()+".REQUEST");
                }
            }
            break;
        case UIConstants.MESSAGE_DISPLAY_POPUP_ADD_SEPERATOR:
            this.addSeparator();
            break;
        default:
            if(event.getEvent().endsWith(".REQUEST")){
                Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_DISPLAYING_MESSAGE_REQUEST, event.getSource());
            }
            break;
        }
    }

}
