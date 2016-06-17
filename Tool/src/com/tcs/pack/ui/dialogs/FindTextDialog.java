package com.tcs.pack.ui.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.tcs.pack.search.ui.ControlPanel;
import com.tcs.pack.ui.actions.FindAction;
import com.tcs.pack.utils.DialogUtils;
import com.tcs.pack.utils.LayoutUtils;

public class FindTextDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextComponent txtComp = null;
	private JLabel lbl_find, lbl_replace, lbl_status;
	private JComboBox<String> txt_findString, txt_replace;
	private JCheckBox matchCase, wholeword, wrapText, incrementalSearch;
	private JButton findNext, clear, close, replace, replaceAll, find;
	private JDialog dialog = null;
	private FindAction findAction = null;
	private Vector<String> findStrs = new Vector<String>();
	private Vector<String> replaceStrs = new Vector<String>();

	/**
	 * @param parent
	 * 
	 */
	public FindTextDialog(JTextComponent txtComp, Frame parent) {
		super(parent);
		this.setTxtComp(txtComp);
		initDisplay();
		this.setSize(400, 275);
		DialogUtils.addHideOnEscapeListener(this);
		DialogUtils.setCenterLocation(this);
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setResizable(false);
		dialog = this;
	}

	private void initDisplay() {
		lbl_find = new JLabel("Find : ");
		lbl_status = new JLabel(" ");
		lbl_replace = new JLabel("Replace With : ");
		lbl_replace.setEnabled(false);
		txt_findString = new JComboBox<String>();
		DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>(findStrs);
		txt_findString.setModel(searchModel);
		txt_findString.setEditable(true);

		txt_replace = new JComboBox<String>();
		DefaultComboBoxModel<String> replaceModel = new DefaultComboBoxModel<String>(replaceStrs);
		txt_replace.setModel(replaceModel);
		txt_replace.setEditable(true);

		matchCase = new JCheckBox("Match Case");
		matchCase.setSelected(true);
		matchCase.setEnabled(false);
		wholeword = new JCheckBox("Whole word");
		wholeword.setEnabled(false);
		wrapText = new JCheckBox("Wrap text");
		wrapText.setSelected(true);
		wrapText.setEnabled(false);
		incrementalSearch = new JCheckBox("Incremental Search");
		incrementalSearch.setEnabled(false);

		// find = new JButton("Find");
		findNext = new JButton("Find Next");
		clear = new JButton("Clear");
		close = new JButton("Close");
		find = new JButton("Find/Replace");
		find.setEnabled(false);
		replace = new JButton("Replace");
		replace.setEnabled(false);
		replaceAll = new JButton("ReplaceAll");
		replaceAll.setEnabled(false);

		findAction = new FindAction((JTextField) txt_findString.getEditor().getEditorComponent(), this.txtComp, lbl_status);

		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((JTextField) txt_findString.getEditor().getEditorComponent()).setText("");
				// matchCase.setSelected(false);
				// wholeword.setSelected(false);
				lbl_status.setText(" ");
				findAction.reset();
			}
		});

		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

		findNext.addActionListener(findAction);
		txt_findString.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findAction.actionPerformed(null);
					findNext.requestFocus();
				}
			}
		});

		txt_findString.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				findAction.actionPerformed(null);
				findNext.requestFocus();
			}
		});

		this.getContentPane()
				.add(LayoutUtils
						.arrangeComponantsInRow(null, true,
								LayoutUtils.arrangeComponantsInRow(true, ControlPanel.HORIZONTAL_FULL,
										LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.HORIZONTAL_FULL, lbl_find, txt_findString)
												.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
										LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.HORIZONTAL_FULL, lbl_replace, txt_replace)
												.setExpandPolicy(ControlPanel.HORIZONTAL_FULL))
										.setExpandPolicy(
												ControlPanel.HORIZONTAL_FULL),
								LayoutUtils.arrangeComponantsInColoumn("Options", ControlPanel.HORIZONTAL_FULL, false,
										LayoutUtils.arrangeComponantsInRow(false, ControlPanel.HORIZONTAL_FULL, matchCase, wholeword)
												.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
										LayoutUtils.arrangeComponantsInRow(false, ControlPanel.HORIZONTAL_FULL, wrapText, incrementalSearch)
												.setExpandPolicy(ControlPanel.HORIZONTAL_FULL))
										.setExpandPolicy(
												ControlPanel.BOTH),
				LayoutUtils
						.arrangeComponantsInRow(false, ControlPanel.HORIZONTAL_FULL,
								LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.HORIZONTAL_FULL, findNext, find, clear).setExpandable(true),
								LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.HORIZONTAL_FULL, replace, replaceAll, close)
										.setExpandable(true))
						// LayoutUtils.arrangeComponantsInColoumn(false, ControlPanel.HORIZONTAL_FULL, clear, close).setExpandable(true))
						.setExpandable(true), new JSeparator(), lbl_status));
		this.addWindowListener(new WindowAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				findAction.reset();
			}
		});
	}

	/**
	 * @return the txtComp
	 */
	public JTextComponent getTxtComp() {
		return txtComp;
	}

	/**
	 * @param txtComp
	 *            the txtComp to set
	 */
	public void setTxtComp(JTextComponent txtComp) {
		this.txtComp = txtComp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (!b) {
			resetSearch();
		}
	}

	/**
	 * 
	 */
	private void resetSearch() {
		findAction.reset();
	}

	public static void main(String args[]) {
		final FindTextDialog dialog = new FindTextDialog(null, null);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
	}

}
