package com.tcs.pack.ui.popup;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.tcs.pack.searchJar.Result;
import com.tcs.pack.utils.DialogUtils;
import com.tcs.pack.utils.EditorUtils;
import com.tcs.pack.windows.TextViewWindow;

@SuppressWarnings("unused")
public class TextViewerMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu file, edit, help, options;
	private JMenuItem save, saveAs, exit, cut, find, copy, paste, add2Copy, selectAll, contents, about, settings, undo, redo, replace, replaceAll;

	private Result result;
	private JTextComponent textComponant;
	private TextViewWindow textViewWindow;

	/**
	 * @param textViewWindow
	 */
	public TextViewerMenuBar(TextViewWindow textViewWindow) {
		this.textViewWindow = textViewWindow;
		initDisplay();
	}

	/**
	 * 
	 */
	private void initDisplay() {
		file = new JMenu("File");
		options = new JMenu("Options");
		options.setEnabled(false);
		edit = new JMenu("Edit");
		help = new JMenu("Help");

		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveAs = new JMenuItem("SaveAs");
		exit = new JMenuItem("Exit");

		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		add2Copy = new JMenuItem("Add2Copy  (\u04BF)");
		add2Copy.setToolTipText("Appends at the end of copied content of Clipboard [Experimental]");
		selectAll = new JMenuItem("SelectAll");
		find = new JMenuItem("Find");
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		replace = new JMenuItem("Replace");
		replace.setEnabled(false);
		replaceAll = new JMenuItem("ReplaceAll");
		replace.setEnabled(false);

		contents = new JMenuItem("Contents");
		contents.setEnabled(false);
		about = new JMenuItem("About");
		settings = new JMenuItem("Settings");

		file.add(save);
		file.add(saveAs);
		file.add(new JSeparator());
		file.add(exit);

		edit.add(undo);
		edit.add(redo);
		edit.add(new JSeparator());
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(new JSeparator());
		edit.add(add2Copy);
		edit.add(new JSeparator());
		edit.add(find);
		edit.add(replace);
		edit.add(replaceAll);
		edit.add(new JSeparator());
		edit.add(selectAll);

		options.add(settings);

		help.add(contents);
		help.add(new JSeparator());
		help.add(about);

		this.add(file);
		this.add(edit);
		this.add(options);
		this.add(help);
		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performSaveOperation();
			}
		});

		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performSaveAsOperation();
			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performExitOperation();
			}
		});

		undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performUndoOperation();
			}
		});

		redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performRedoOperation();
			}
		});

		cut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textComponant.setText(EditorUtils.cutOperation(textComponant.getText(), textComponant.getSelectedText()));
			}
		});

		copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditorUtils.copyOperation(textComponant.getSelectedText());
			}
		});

		add2Copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditorUtils.add2CopyOperation(textComponant.getSelectedText());
			}
		});

		find.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performFindOperation();
			}
		});

		replace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performFindOperation();
			}
		});

		replaceAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewWindow.performFindOperation();
			}
		});

		selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textComponant.setSelectionStart(0);
				textComponant.setSelectionEnd(textComponant.getText().length());
			}
		});
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogUtils.showAboutDialog(null);
			}
		});
	}

	/**
	 * @param result
	 */
	public void setEditableResult(Result result) {
		this.result = result;
	}

	public void setJTextComponant(JTextComponent textComponent) {
		this.textComponant = textComponent;
	}

}
