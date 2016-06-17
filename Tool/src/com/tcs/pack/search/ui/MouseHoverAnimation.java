package com.tcs.pack.search.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MouseHoverAnimation extends MouseAdapter {
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
     */
    private String defaultValue;
    private JLabel lbl_status;

    /**
     * 
     */
    public MouseHoverAnimation(JLabel lblStatus) {
        this.lbl_status = lblStatus;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        defaultValue = lbl_status.getText();
        if(((JButton)me.getSource()).getText().equals("Clear")){
            lbl_status.setText("Click Clear to clear all the records and input values");
        }else if(((JButton)me.getSource()).getText().equals("Close")){
            lbl_status.setText("Click close to exit the application");
        }else if(((JButton)me.getSource()).getText().equals("Browse")){
            lbl_status.setText("Click browse to select search path");
        }
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        lbl_status.setText(defaultValue);
    }
    
}
