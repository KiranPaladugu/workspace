/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo, UIKeyboardInteractive {
	private String password;
	JTextField passwordField = (JTextField) new JPasswordField();

	public String getPassphrase() {
		String pss = password;
		password = null;
		return pss;
	}

	public String getPassword() {
		return null;
	}

	public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo) {
		return null;
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

	public boolean promptYesNo(String str) {
		return false;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void showMessage(String message) {
	}
}