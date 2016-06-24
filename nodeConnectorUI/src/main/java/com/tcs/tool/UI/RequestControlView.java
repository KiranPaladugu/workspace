/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import com.tcs.application.*;
import com.tcs.tools.Message;
import com.tcs.tools.Request;
import com.tcs.tools.UI.utils.LayoutUtils;
import com.tcs.tools.UI.utils.UIConstants;
import com.tcs.tools.resources.ResourceLocator;

public class RequestControlView extends ControlPanel implements Subscriber {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JComboBox<Object> templet;
    private DefaultComboBoxModel<Object> model;
    private Vector<Object> tmplts = new Vector<>();;
    private JButton send, newMessage, editMessage;
    private JLabel connectionStatus;
    private static Message<String> message;
    private static final String txt_Reset = "RequestControlView.Reset";
    private ConnectionAnimator animator;

    /**
     * 
     */
    public RequestControlView() {
        this.setExpandPolicy(HORIZONTAL_FULL);
        tmplts.add("Select");
        //        tmplts.add("New");
        model = new DefaultComboBoxModel<>(tmplts);
        templet = new JComboBox<>(model);
        send = new JButton("Send");
        send.setIcon(ResourceLocator.getImageIcon("send.png"));
        editMessage = new JButton("Edit");
        editMessage.setIcon(ResourceLocator.getImageIcon("edit.png"));
        editMessage.setEnabled(false);
        editMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(UIConstants.EDIT_SAVED_MESSAGE, e);
            }
        });
        newMessage = new JButton("New");
        newMessage.setIcon(ResourceLocator.getImageIcon("New.png"));
        newMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(UIConstants.TEMPLET_NEW_COUSTOM_MESSAGE, e);

            }
        });
        connectionStatus = new JLabel(ResourceLocator.getImageIcon("Off.png"));
        connectionStatus.setToolTipText("Offline");
        send.setEnabled(false);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSubscriptionManager().notifySubscriber(UIConstants.SEND_REQUEST, e.getSource(), message);
                Application.getSubscriptionManager().notifySubscriber(txt_Reset);

            }
        });
        //        templet.add
        templet.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                //                if (e.getStateChange() == ItemEvent.SELECTED && templet.getSelectedItem().equals("New"))
                //                    Application.getSubscriptionManager().notifySubscriber(UIConstants.TEMPLET_NEW_COUSTOM_MESSAGE, e);
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (templet.getSelectedItem().equals("Select"))
                        Application.getSubscriptionManager().notifySubscriber(UIConstants.REQUEST_CANCEL_SEND);
                    else
                        Application.getSubscriptionManager().notifySubscriber(UIConstants.MESSAGE_SELECTED, templet,
                                templet.getSelectedItem());
                }
            }

        });
        Application.getSubscriptionManager().subscribe(this, UIConstants.PREPARE_CONNECT, UIConstants.CONNECTING,
                UIConstants.CONNECTION_SUCCESS, UIConstants.DICONNECTED, UIConstants.TEMPLET_NEW_COUSTOM_MESSAGE,
                UIConstants.NEW_REQUEST_OK, UIConstants.NEW_REQUEST, UIConstants.REQUEST_CANCEL_SEND, txt_Reset,
                UIConstants.EDIT_SAVED_MESSAGE, UIConstants.SAVED_MESSAGES_AS_LIST, UIConstants.MESSAGE_SELECTED,
                UIConstants.GET_SAVEDMESSAGE_SUCCSS, UIConstants.SAVE_MESSAGE_SUCCESS,UIConstants.SAVED_MESSAGE_DELETE_SUCCESS);
        this.setLayout(new GridLayout());
        this.add(LayoutUtils.arrangeComponantsInColoumn(
                LayoutUtils.arrangeComponantsInColoumn("Templet", templet).setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
                LayoutUtils
                        .arrangeComponantsInColoumn(
                                LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.BOTH,
                                        LayoutUtils.arrangeComponantsInRow(true, ControlPanel.HORIZONTAL_FULL, editMessage,
                                                newMessage),
                                        send).setExpandPolicy(NONE),
                                LayoutUtils.arrangeComponantsInColoumn("status", connectionStatus).setExpandPolicy(TEN_PERCENT))
                        .setExpandPolicy(ControlPanel.TWENTY_PERCENT)));
        connectionStatus.setSize(18, 18);
        Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_SAVED_MESSAGES_AS_LIST);
    }

    public static Message<String> getMessage() {
        return message;
    }

    public static void setMessage(Message<String> msg) {
        message = msg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        Object data = event.getData();
        switch (event.getEvent()) {
        case UIConstants.CONNECTION_SUCCESS:
            if (animator != null) {
                animator.stopAnimation();
                animator = null;
            }
            connectionStatus.setIcon(ResourceLocator.getImageIcon("On.png"));
            connectionStatus.setToolTipText("Online");
            sendOnConnectMessages();
            Application.getSubscriptionManager().notifySubscriber(txt_Reset);
            break;
        case UIConstants.DICONNECTED:
            if (animator != null) {
                animator.stopAnimation();
                animator = null;
            }
            connectionStatus.setIcon(ResourceLocator.getImageIcon("Off.png"));
            connectionStatus.setToolTipText("Offline");
            break;
        case UIConstants.PREPARE_CONNECT:
        case UIConstants.CONNECTING:
            connectionStatus.setIcon(ResourceLocator.getImageIcon("Wait.png"));
            //            animator = ConnectionAnimator.startAnimation(connectionStatus, "Wait.png", "Off.png");
            connectionStatus.setToolTipText("Connection inprogress.....");
            break;
        case UIConstants.TEMPLET_NEW_COUSTOM_MESSAGE:
            Application.getSubscriptionManager().notifySubscriber(UIConstants.NEW_MESSAGE_DIALOG);
            break;
        case UIConstants.NEW_REQUEST_OK:
            message = (Message<String>) event.getData();
            if (message != null) {
                this.send.setEnabled(true);
            }
            break;
        case UIConstants.REQUEST_CANCEL_SEND:
            this.send.setEnabled(false);
            this.editMessage.setEnabled(false);
            break;
        case UIConstants.EDIT_SAVED_MESSAGE:
            Application.getSubscriptionManager().notifySubscriber(UIConstants.NEW_MESSAGE_DIALOG, templet.getSelectedItem(),
                    message);
            break;
        case txt_Reset:
            resetView();
            break;
        case UIConstants.SAVED_MESSAGES_AS_LIST:
            if (data != null && data instanceof List<?>) {
                this.tmplts.addAll((List<?>) data);
                this.templet.repaint();
            }
            break;
        case UIConstants.MESSAGE_SELECTED:
            if (data != null && data instanceof MessageMetaData) {
                Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_SAVED_MESSAGE, null, data);
            }
            break;

        case UIConstants.SAVE_MESSAGE_SUCCESS:
            if (data != null && data instanceof MessageMetaData) {
                if (!tmplts.contains(data)) {
                    this.model.addElement(data);
                    this.templet.repaint();
                }
                if (event.getSource() != null && event.getSource() instanceof Message<?>) {
                    message =  (Message<String>) event.getSource() ;
                    this.send.setEnabled(true);
                    this.editMessage.setEnabled(true);
                }
            }
            break;
        case UIConstants.GET_SAVEDMESSAGE_SUCCSS:
            if (data != null && data instanceof UISavableMessage<?>) {
                message = (Message<String>) data;
                this.send.setEnabled(true);
                this.editMessage.setEnabled(true);
            }
            break;
        case UIConstants.SAVED_MESSAGE_DELETE_SUCCESS:
            resetView();
            if(data!=null && data instanceof MessageMetaData){
                if(tmplts.contains(data)){
                    this.model.removeElement(data);
                }
            }
            break;
        default:
            System.out.println("Unmanaged Event :" + event.getEvent() + " Caller is " + event.getCaller());
            break;
        }
    }

    /**
     * 
     */
    private void sendOnConnectMessages() {
        System.out.println("Load message to send on connection...");
        InputStream inputStream = Application.getResourceResolver().getResourceAsStream("HelloMessage");
        if(inputStream!=null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            try {
                StringBuffer buffer = new StringBuffer();
                while((line=reader.readLine())!=null){
                    if(line.trim().equals("")){
                        pushRequest(buffer.toString());
                        buffer = new StringBuffer();
                    }else{
                        buffer.append(line+"\n");
                    }
                }
                if(buffer.length()>0){
                    pushRequest(buffer.toString());
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    /**
     * 
     */
    private void pushRequest(String message) {
        if(message.length()>0){
            System.out.println(String.format("Pushing message as request on connect: %s", message));
            Application.getSubscriptionManager().notifySubscriber(UIConstants.SEND_REQUEST, this, new Request<String>(message));
        }
    }

    /**
     * 
     */
    private void resetView() {
        message = null;
        templet.setSelectedItem("Select");
//        this.send.setEnabled(false);
//        this.editMessage.setEnabled(false);

    }

}
