/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.tcs.application.*;
import com.tcs.tools.Message;
import com.tcs.tools.UI.utils.*;
import com.tcs.tools.resources.ResourceLocator;

public class NewCoustomMessageView extends JDialog implements Subscriber, ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ButtonControlPanel buttonPanel;
    private JTextArea messageArea;
    private JButton ok, cancel, close, save, delete;
    private JLabel lbl_name;
    private JCheckBox replyExpected, sendWhenConnected;
    private JTextField name;
    private static final String txt_reset = "NewCoustomMessageView.Reset";
    private static final String txt_close = "NewCoustomMessageView.Close";
    private MessageMetaData metadata;
    private Message<String> message;

    /**
     * 
     */
    public NewCoustomMessageView() {
        this.setModal(true);
        DialogUtils.addEscapeListener(this);
        DialogUtils.setCenterLocation(this);
        this.setSize(600, 500);
        this.setLayout(new GridLayout());
        buttonPanel = new ButtonControlPanel();
        ok = new JButton("Ok");
        ok.addActionListener(this);
        ok.setIcon(ResourceLocator.getImageIcon("Ok.png"));
        save = new JButton("Save");
        save.addActionListener(this);
        save.setIcon(ResourceLocator.getImageIcon("save.png"));
        delete = new JButton("Delete");
        delete.setVisible(false);
        delete.addActionListener(this);
        delete.setIcon(ResourceLocator.getImageIcon("Delete.png"));
        cancel = new JButton("Clear");
        cancel.setIcon(ResourceLocator.getImageIcon("stop.png"));
        cancel.addActionListener(this);
        close = new JButton("Close");
        close.setIcon(ResourceLocator.getImageIcon("cross_1.png"));
        //        saveMessage = new JCheckBox("Save Message");
        replyExpected = new JCheckBox("Reply expected");
        sendWhenConnected = new JCheckBox("Send as FirstMessage");
        lbl_name = new JLabel("Name of the message:");
        name = new JTextField();
        buttonPanel.add(ok);
        buttonPanel.add(save);
        buttonPanel.add(delete);
        buttonPanel.add(cancel);
        buttonPanel.add(close);
        close.addActionListener(this);
        messageArea = new JTextArea();
        messageArea.setFont(new Font("Courier New", Font.PLAIN , 14));
        this.getContentPane().add(LayoutUtils.arrangeComponantsInRow(
                LayoutUtils.arrangeComponantsInColoumn(new JScrollPane(messageArea)).setExpandPolicy(ControlPanel.BOTH),
                new JSeparator(),
                LayoutUtils
                        .arrangeComponantsInColoumn(false, LayoutUtils.arrangeComponantsInRow(false, lbl_name, replyExpected),
                                LayoutUtils.arrangeComponantsInRow(false, name, sendWhenConnected))
                        .setExpandPolicy(ControlPanel.NONE),
                new JSeparator(), buttonPanel.setExpandPolicy(ControlPanel.HORIZONTAL_FULL)));
        Application.getSubscriptionManager().subscribe(this, UIConstants.CREATE_NEW_REQUEST, UIConstants.SAVE_MESSAGE,
                UIConstants.NEW_MESSAGE_DIALOG, txt_reset, txt_close, UIConstants.SAVE_MESSAGE_SUCCESS,
                UIConstants.CANCEL_MESSAGE,UIConstants.SAVED_MESSAGE_DELETE_SUCCESS);
        reset();
    }

    public static void main(String[] args) {
        NewCoustomMessageView msgView = new NewCoustomMessageView();
        msgView.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        msgView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ok))
            Application.getSubscriptionManager().notifySubscriber(UIConstants.CREATE_NEW_REQUEST, e);
        else if (e.getSource().equals(save))
            Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVE_MESSAGE, e);
        else if (e.getSource().equals(close))
            Application.getSubscriptionManager().notifySubscriber(txt_close, e);
        else if (e.getSource().equals(cancel))
            Application.getSubscriptionManager().notifySubscriber(UIConstants.CANCEL_MESSAGE);
        else if (e.getSource().equals(delete)) {
            Application.getSubscriptionManager().notifySubscriber(UIConstants.DELETE_MESSAGE_REQUEST, null, metadata);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {
        Object obj = event.getData();
        switch (event.getEvent()) {
        case UIConstants.NEW_MESSAGE_DIALOG:
            reset();
            if (obj != null && obj instanceof Message<?>) {
                this.message = (Message<String>) obj;
                if (event.getSource() != null && event.getSource() instanceof MessageMetaData) {
                    metadata = (MessageMetaData) event.getSource();
                    setData((Message<String>) obj);
                }
            }
            this.setVisible(true);
            break;
        case txt_reset:
            reset();
            break;
        case UIConstants.CREATE_NEW_REQUEST:
            UISavableMessage<String> msg = prepareMessageFromInput();
            if (msg != null) {
                this.setVisible(false);
                Application.getSubscriptionManager().notifySubscriber(UIConstants.NEW_REQUEST_OK, this, msg);
            }
            break;
        case txt_close:
            this.setVisible(false);
            break;
        case UIConstants.CANCEL_MESSAGE:
            reset();
            break;
        case UIConstants.SAVE_MESSAGE:
            UISavableMessage<String> saveMessage = prepareMessageFromInput();
            if (saveMessage != null)
                Application.getSubscriptionManager().notifySubscriber(UIConstants.DO_SAVE_MESSAGE, metadata, saveMessage);
            break;
        case UIConstants.SAVED_MESSAGE_DELETE_SUCCESS:
        case UIConstants.SAVE_MESSAGE_SUCCESS:
            this.setVisible(false);
        }
    }

    /**
     * @param obj
     */
    private void setData(Message<String> obj) {
        this.messageArea.setText(obj.getMessage());
        this.replyExpected.setSelected(obj.isReplyExpected());
        this.name.setText(obj.getName());
        this.name.setEditable(false);
        delete.setVisible(true);
        //        this.sendWhenConnected.setSelected(metadata.isAutoSend());

    }

    /**
     * @return
     * 
     */
    private UISavableMessage<String> prepareMessageFromInput() {
        if (validateData()) {
            UISavableMessage<String> msg = new UISavableMessage<String>();
            msg.setMessage(this.messageArea.getText().trim());
            msg.setReplyExpected(this.replyExpected.isSelected());
            msg.setAutoSend(this.sendWhenConnected.isSelected());
            //        msg.setSave(this.saveMessage.isSelected());
            msg.setName(this.name.getText());
            if (message != null) {
                msg.setName(message.getName());
                msg.setId(message.getId());
            }
            return msg;
        }
        return null;
    }

    /**
     * @return
     * 
     */
    private boolean validateData() {
        if (messageArea.getText().trim().length() < 1) {
            JOptionPane.showMessageDialog(this, "Message is EMPTY!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void reset() {
        this.messageArea.setText("");
        if (message == null) {
            this.name.setText("");
            this.name.setEditable(true);
        }
        this.replyExpected.setSelected(true);
        this.sendWhenConnected.setSelected(false);
        delete.setVisible(false);
        this.metadata = null;
        this.message = null;
    }
}
