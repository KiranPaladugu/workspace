package com.tcs.pack.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class FindAction implements ActionListener {

	/**
	 * @param text
	 * @param txtComp
	 */
	private String search;
	private JTextComponent txtComp;
	private JTextComponent strComp;
	private JLabel status;
	private String fullText = "";
	private int prev = 0;

	public FindAction(String search, JTextComponent txtComp) {
		this.search = search;
		this.txtComp = txtComp;

	}

	public FindAction(JTextComponent strComp, JTextComponent txtComp) {
		this.strComp = strComp;
		this.txtComp = txtComp;
	}

	public FindAction(JTextComponent strComp, JTextComponent txtComp, JLabel statusLabel) {
		this.strComp = strComp;
		this.txtComp = txtComp;
		this.status = statusLabel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean foundString = false;
		boolean end = false;

		if (strComp != null) {
			String tmpSearch = strComp.getText();
			if (search == null || !search.equals(tmpSearch) || fullText.length() == 0 || e == null) {
				reset();
				search = tmpSearch;
				fullText = txtComp.getText();
			}

		}
		if (search.length() == 0) {
			return;
		}
		int startIndex = fullText.indexOf(search, prev);
		int endIndex = startIndex + search.length();
		if (startIndex != endIndex && startIndex < endIndex && startIndex != -1 && endIndex != -1) {
			txtComp.setSelectionStart(startIndex);
			txtComp.setSelectionEnd(endIndex);
			prev = endIndex;
			foundString = true;
		} else {
			end = true;
		}
		if (!foundString) {
			if (status != null)
				status.setText("String not found!!");
			else
				JOptionPane.showMessageDialog(txtComp, "String Not Found", "Find", JOptionPane.INFORMATION_MESSAGE);
		} else if (foundString && end) {
			if (status != null)
				status.setText("End of Content");
			else
				JOptionPane.showMessageDialog(txtComp, "End of Content", "Find", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * 
	 */
	public void reset() {
		fullText = "";
		search = "";
		prev = 0;
		if (this.status != null) {
			status.setText("");
		}
	}

}
