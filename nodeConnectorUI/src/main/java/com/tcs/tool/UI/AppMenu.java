/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

import com.tcs.application.Application;
import com.tcs.tools.UI.utils.UIConstants;

public class AppMenu extends JMenuBar{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SMenu file,options,history,help;
    private SMenuItem exit,clear,credits;
    
    /**
     * 
     */
    public AppMenu() {
        
        file = new SMenu("File");
        exit = new SMenuItem();
        history = new SMenu("History");
        clear = new SMenuItem();
        clear.setText("Clear History");
        exit.setText("Exit");
        credits = new SMenuItem();
        credits.setText("Credits");
        new CreditsDialog();
        Application.getSubscriptionManager().subscribe("exit", exit);
        exit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber("exit", exit);
            }
        });
        clear.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(UIConstants.CLEAR_HISTORY, e.getSource());
            }
        });
        credits.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(CreditsDialog.CREDITS_SHOW);
            }
        });
        file.add(exit);
        history.add(clear);
        options = new SMenu("Options");
        help = new SMenu("Help");
        help.add(credits);
        this.add(file);
        this.add(history);
        this.add(options);
        this.add(help);
    }
}
