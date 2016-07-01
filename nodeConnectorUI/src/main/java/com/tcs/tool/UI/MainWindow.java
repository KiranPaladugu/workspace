/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.tcs.application.Application;
import com.tcs.tools.UI.utils.UIConstants;
import com.tcs.tools.UI.utils.WindowUtils;

public class MainWindow extends JFrame implements WindowListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    public MainWindow() {
        this.setTitle("Node Netconf connector...");
        DatabaseManager saveMessageManager =new DatabaseManager();        
        Application.getApplicationContext().put("SaveManager", saveMessageManager);
        DBListener dbListener = new DBListener();
        Application.getApplicationContext().put("dbListener", dbListener);
        Application.getSubscriptionManager().notifySubscriber(UIConstants.LOAD_DATA_BASE);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.addWindowListener(this);
        this.getContentPane().setLayout(new GridLayout());
        this.getContentPane().add(new SessionView());
        this.setJMenuBar(new AppMenu());
        this.setSize(600, 500);
        WindowUtils.setCenterLocation(this);
    }


    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Application.getSubscriptionManager().notifySubscriber("exit", this);
    }

  
    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

 
    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

  
    @Override
    public void windowActivated(WindowEvent e) {
        
    }

   
    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
    
}
