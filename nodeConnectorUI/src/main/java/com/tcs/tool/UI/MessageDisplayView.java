/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

import com.tcs.application.*;
import com.tcs.tools.Message;
import com.tcs.tools.UI.utils.CommonMouseAdaptor;
import com.tcs.tools.UI.utils.UIConstants;

public class MessageDisplayView extends ControlPanel implements Subscriber {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextArea dispaly;

    /**
     * 
     */
    public MessageDisplayView() {
        this.setExpandPolicy(BOTH);
        this.setBorder(BorderFactory.createTitledBorder("Message Dispaly area"));
        dispaly = new JTextArea();
        dispaly.setEditable(false);
        this.setLayout(new GridLayout());
        this.add(new JScrollPane(dispaly));
        dispaly.setFont(new Font("Courier New", Font.PLAIN, 14));
        dispaly.addMouseListener(new CommonMouseAdaptor(new MessageDispalyPopUp()));
        Application.getSubscriptionManager().subscribe(this, UIConstants.MESSAGE_DISPLAY,
                UIConstants.GET_DISPLAYING_MESSAGE_REQUEST);
    }

    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        Object obj = event.getSource();
        switch (event.getEvent()) {
        case UIConstants.MESSAGE_DISPLAY:
            if (obj instanceof Message<?>) {
                @SuppressWarnings("unchecked")
                Message<String> message = (Message<String>) obj;
                dispaly.setText(message.getMessage());
            }
            break;
        case UIConstants.GET_DISPLAYING_MESSAGE_REQUEST:
            String dataToSend = dispaly.getText();
            if (dataToSend.trim().length() > 0) {
               
                String endStmt = (String) Application.getApplicationContext().get("endOfCommand");
                if (endStmt != null) {
                    int index = -1;
                    if((index=dataToSend.lastIndexOf(endStmt))!=-1){
                        dataToSend = dataToSend.substring(0, index);
                    }
                }
                Application.getSubscriptionManager().notifySubscriber(event.getSource().getClass().getName() + ".RESPONSE", this,
                        dataToSend);
            }
        default:
            break;
        }

    }

}
