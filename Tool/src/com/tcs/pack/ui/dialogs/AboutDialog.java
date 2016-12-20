package com.tcs.pack.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.tcs.pack.utils.DialogUtils;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel data, leftBorder, topHeader;
	private JButton ok_button;
	private JDialog dialog;
	private String version = "1.0";

	public AboutDialog(Window parent) {
		super(parent);
		this.dialog = this;
		init();
	}

	/**
	 * 
	 */
	private void init() {
		data = new JLabel();
		leftBorder = new JLabel(" ");
		topHeader = new JLabel("Credits!!");

		String headerText = "<html><body><h1><center><font color=\"red\"><u>Jar Search Tool !</u></font></center></h1>"
				+ "<center><h5><u><font color=\"blue\">version:" + version + "</u></h5></font></center><hr>";
		String text = headerText + "<center><p>Developed by"
				+ "<font color=\"green\"> <b><I>Kiran Paladugu</I></b></font>. To provide any suggestions and improvements contact me at below mail(s)<br><br>"
				+ "<b><u><font color=\"blue\">paladugu.kiran@tcs.com</font></u></b><br> or <br>"
				+ "<b><u><font color=\"blue\">paladugukiran@gmail.com</font></u></b>" + "</p></center></body></html>";
		data.setText(text);
		// topHeader.setText(headerText);
		;
		ok_button = new JButton("OK");
		ok_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		this.setModal(true);
		this.setLayout(new BorderLayout());
//		this.add(topHeader, BorderLayout.NORTH);
		this.add(leftBorder, BorderLayout.WEST);
		this.add(data, BorderLayout.NORTH);
		this.add(ok_button, BorderLayout.SOUTH);
		this.setSize(400, 300);
		DialogUtils.setCenterLocation(this);
		this.setResizable(false);
		DialogUtils.addHideOnEscapeListener(this);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.setTitle("Credits..");
		// this.setVisible(true);
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/*
	 * public static void main(String args[]) { new AboutDialog(null); }
	 */
}
