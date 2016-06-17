/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;

public class Visualizer implements Subscriber{
    public static final String MESSAGE_DISPLAY_POPUP_ADD_MENU = "messageDisplayPopupAddMenu";
    private static MainWindow window = new MainWindow();
    private Visualizer visual;
    /**
     * 
     */
    public Visualizer() {
        visual = this;
    }
    
    public static void main(String[] args){
        new Excecutor(window);
    }
    
    static class Excecutor extends Thread{
        private Runnable awtRunnable;
        public Excecutor(Runnable awtRunnable) {
            this.awtRunnable = awtRunnable;
            this.setName(this.getClass().getSimpleName());
            this.start();
        }
        @Override
        public void run() {
            SwingUtilities.invokeLater(awtRunnable);
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    public void loadPlugin(){
        Application.getSubscriptionManager().subscribe(this, this.getClass().getName()+".OK",this.getClass().getName()+".RESPONSE");
        
        JMenuItem display = new JMenuItem("Display in XML tree");
        display.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(visual.getClass().getName()+".REQUEST", visual, null);
            }
        });
        
        Application.getSubscriptionManager().notifySubscriber("messageDisplayPopupAddMenu", this, display);
    }
    
    public void displayXmlView(String xmlString){
        if(xmlString!=null){
            window.displayXml(xmlString);
        }
        
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
     */
    @Override
    public void onSubscriptionEvent(SubscriptionEvent arg0) {
        String event = arg0.getEvent();
        Object data =  arg0.getData();
        if(event.equals(this.getClass().getName()+".OK")){
            System.out.println("Request SUCESS...");
        }else if(event.equals(this.getClass().getName()+".RESPONSE")){
            System.out.println("Got the response..");
            displayXmlView((String) arg0.getData());
        }
    }
}
