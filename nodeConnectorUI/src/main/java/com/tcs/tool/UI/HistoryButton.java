/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.tcs.application.Application;
import com.tcs.tools.Message;
import com.tcs.tools.UI.utils.UIConstants;
import com.tcs.tools.resources.ResourceLocator;

public class HistoryButton extends JButton {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Message<?> message;

    /**
     * 
     */
    public HistoryButton(String name, final Message<?> message) {
        super(name);
        this.setMessage(message);
        if (message != null) {
            if (message.isRequest()) {
                this.setIcon(ResourceLocator.getImageIcon("req.png"));
            } else if (message.isResponse()) {
                this.setIcon(ResourceLocator.getImageIcon("res.png"));
            }
        }
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(UIConstants.MESSAGE_DISPLAY, message);
            }
        });
    }

    public Message<?> getMessage() {
        return message;
    }

    public void setMessage(Message<?> message) {
        this.message = message;
    }

}
