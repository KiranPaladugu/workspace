/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import javax.swing.*;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo, UIKeyboardInteractive {
    private String password;
    JTextField passwordField = (JTextField) new JPasswordField();

    public String getPassword() {
        return null;
    }

    public boolean promptYesNo(String str) {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassphrase() {
        String pss = password;
        password = null;
        return pss;
    }

    public boolean promptPassphrase(String message) {
        Object[] ob = { passwordField };
        int result = JOptionPane.showConfirmDialog(null, ob, message, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (passwordField.getText().length() < 0) {
                this.password = passwordField.getText();
                return true;
            }
        }
        return false;

    }

    public boolean promptPassword(String message) {
        Object[] ob = { passwordField };
        int result = JOptionPane.showConfirmDialog(null, ob, message, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            this.password = passwordField.getText();
            return true;
        } else {
            return false;
        }
    }

    public void showMessage(String message) {
    }

    public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt,
            boolean[] echo) {
        return null;
    }
}