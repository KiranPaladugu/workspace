package com.tcs.pack.ui.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.tcs.pack.utils.EditorUtils;

public class EditorPopup extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem copy, cut, paste, selectAll, add2Copy;
	private JTextComponent component;

	/**
	 * 
	 */
	public EditorPopup(JTextComponent component) {
		this.component = component;
		init();
	}

	/**
	 * 
	 */
	private void init() {
		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		add2Copy = new JMenuItem("Add2Copy  (\u04BF)");
		add2Copy.setToolTipText("Appends at the end of copied content of Clipboard [Experimental]");
		selectAll = new JMenuItem("SelectAll");

		cut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField textField = (JTextField) component;
				String result = EditorUtils.cutOperation(textField.getText(), textField.getSelectionStart(), textField.getSelectionEnd());
				textField.setText(result);
			}
		});

		copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditorUtils.copyOperation(component.getText());
			}
		});
		
		paste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditorUtils.pasteOpertion(component);
			}
		});
		
		add2Copy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditorUtils.add2CopyOperation(component.getText());
			}
		});
		
		selectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				component.setSelectionStart(0);
				component.setSelectionEnd(component.getSelectedText().length());
			}
		});
		
		this.add(cut);
		this.add(copy);
		this.add(paste);
		this.add(new JSeparator());
		this.add(add2Copy);
		this.add(new JSeparator());
		this.add(selectAll);
		this.setEnabled(true);
//		System.out.println("Initialized POPUP");
		this.component.add(this);
	}

}
