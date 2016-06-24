/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import com.tcs.application.*;
import com.tcs.tools.Message;
import com.tcs.tools.UI.utils.LayoutUtils;
import com.tcs.tools.UI.utils.UIConstants;

public class HistoryView extends ControlPanel implements Subscriber {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ControlPanel panel;
    private LayoutUtils util = LayoutUtils.getUtils("HistoryView");
    private JScrollPane scrollPane;

    /**
     * 
     */
    public HistoryView() {
        this.setLayout(new GridLayout());
        this.setBorder(BorderFactory.createTitledBorder("History"));
        panel = new ControlPanel(true, ControlPanel.VERTICAL_FULL);
        panel.setLayout(new GridBagLayout());
        this.setExpandPolicy(ControlPanel.HALF);
        scrollPane = new JScrollPane(panel);
        this.add(scrollPane);
        /*
         * JButton req = new JButton("Req"); req.addActionListener(new ActionListener() {
         * 
         * @Override public void actionPerformed(ActionEvent e) { addToHistory("Request", new Request<String>(
         * "This is some request to server..."));
         * 
         * } }); JButton res = new JButton("Res"); res.addActionListener(new ActionListener() {
         * 
         * @Override public void actionPerformed(ActionEvent e) { addToHistory("Response", new Response<String>(
         * "This is some response from server"));
         * 
         * } }); panel.add(req, util.getNextRowConstaints()); panel.add(res, util.getNextRowConstaints());
         */
        Application.getSubscriptionManager().subscribe(this, UIConstants.RESPONSE, UIConstants.REQUEST,UIConstants.CLEAR_HISTORY);
    }

    public void addHistory(Component comp) {
        panel.add(comp, util.getNextRowConstaints());
        panel.revalidate();
        scrollPane.revalidate();
        this.revalidate();
        this.repaint();
    }

    public void addToHistory(String name, Message<?> message) {
        if (message != null) {
            Component cmp = new HistoryButton(name, message);
            addHistory(cmp);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        Object data = event.getData();
        switch (event.getEvent()) {
        case UIConstants.RESPONSE:
        case UIConstants.REQUEST:
            if (data != null && data instanceof Message<?>) {
                Message<String> message = (Message<String>) data;
                addToHistory(message.getName(), message);
            }
            break;
        case UIConstants.CLEAR_HISTORY:
            resetView();
            break;
        default:
            break;
        }
    }

    /**
     * 
     */
    private void resetView() {
        this.panel.removeAll();
        this.panel.revalidate();
        this.panel.repaint();
        this.util = LayoutUtils.getUtils("Empty!");
        
    }

}
