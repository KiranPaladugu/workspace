package com.tcs.pack.ui.popup;

import java.awt.event.*;

import javax.swing.*;

import com.tcs.pack.utils.EditorUtils;
import com.tcs.pack.windows.TextViewWindow;

public class TextViewerPopup extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem undo , redo ,cut,copy,paste, add2Copy, find;
	private JMenuItem selectAll;
	private JTextPane textPane;
	private TextViewWindow parent;
	
	/**
	 * 
	 */
	public TextViewerPopup(final JTextPane textPane, final TextViewWindow parent) {
		this.textPane = textPane;
		this.parent = parent;
		init();

	}

	/**
	 * @param textPane
	 * @param parent
	 */
	private void init() {
		undo = new JMenuItem("Undo");
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.performUndoOperation();
			}
		});
		redo = new JMenuItem("Redo");
		redo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.performRedoOperation();
			}
		});
		cut = new JMenuItem("Cut");
		cut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.performCutOperation();
				
			}
		});
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		paste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.performPasteOperation();
			}
		});
		copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textPane != null) {
					EditorUtils.copyOperation(textPane.getSelectedText());
				}

			}
		});
		add2Copy = new JMenuItem("Add2Copy  (\u04BF)");
		add2Copy.setToolTipText("Appends at the end of copied content of Clipboard [Experimental]");
		add2Copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textPane != null) {
					EditorUtils.add2CopyOperation(textPane.getSelectedText());
				}
			}
		});
		find = new JMenuItem("Find");
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		
		selectAll = new JMenuItem("SelectAll");
		selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textPane != null) {
					textPane.select(0, textPane.getText().length());
				}

			}
		});

		find.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.performFindOperation();
			}
		});

		this.add(undo);
		this.add(redo);
		this.add(new JSeparator());
		this.add(cut);
		this.add(copy);
		this.add(paste);
		this.add(new JSeparator());
		this.add(add2Copy);
		this.add(new JSeparator());
		this.add(find);
		this.add(new JSeparator());
		this.add(selectAll);
	}
}
