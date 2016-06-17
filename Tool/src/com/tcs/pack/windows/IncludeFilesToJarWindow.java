/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.windows;

import java.util.Vector;

import javax.swing.*;

import com.tcs.pack.search.ui.ControlPanel;
import com.tcs.pack.utils.LayoutUtils;
import com.tcs.pack.utils.WindowUtils;

public class IncludeFilesToJarWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> jarFileSelected, fileOrPath;
	private JCheckBox multiSelect, singleSelection;
	private Vector<String> selectedJars = new Vector<String>();
	private Vector<String> selectedPaths = new Vector<String>();
	private JButton browsePath, browseJar, ok, cancel, close;
	private JLabel statusBar;

	/**
	 * 
	 */
	public IncludeFilesToJarWindow() {
		init();
	}

	/**
	 * 
	 */
	private void init() {
		DefaultComboBoxModel<String> jarSelectionModel = new DefaultComboBoxModel<String>(selectedJars);
		jarFileSelected = new JComboBox<>();
		jarFileSelected.setModel(jarSelectionModel);
		jarFileSelected.setEditable(true);
		browseJar = new JButton("Browse");

		DefaultComboBoxModel<String> pathSelectionModel = new DefaultComboBoxModel<>(selectedPaths);
		fileOrPath = new JComboBox<>();
		fileOrPath.setModel(pathSelectionModel);
		browsePath = new JButton("Browse");

		multiSelect = 		new JCheckBox("Multiple file Selection");
		singleSelection = 	new JCheckBox("Single  file  selection");
		
		ButtonGroup selectionGroup = new ButtonGroup();
		selectionGroup.add(multiSelect);
		selectionGroup.add(singleSelection);

		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		close = new JButton("Close");
		statusBar = new JLabel(" ");

		ControlPanel input_text = LayoutUtils.arrangeComponantsInRow(true, ControlPanel.HORIZONTAL_FULL,
				LayoutUtils.arrangeComponantsInColoumn("Selected JarFile", ControlPanel.HORIZONTAL_FULL, true, jarFileSelected, browseJar)
						.setExpandable(true).setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
				LayoutUtils.arrangeComponantsInColoumn("Select Files or path to inlcue", ControlPanel.HORIZONTAL_FULL, true, fileOrPath, browsePath)
						.setExpandable(true).setExpandPolicy(ControlPanel.HORIZONTAL_FULL));

		ControlPanel controller = LayoutUtils.arrangeComponantsInColoumn(false, ok, cancel, close);

		ControlPanel input = LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.HORIZONTAL_FULL, input_text,
				LayoutUtils.arrangeComponantsInRow("Options", ControlPanel.VERTICAL_FULL, true, multiSelect, singleSelection).setExpandable(true)
						.setExpandPolicy(ControlPanel.TWENTY_PERCENT));
		ControlPanel panelToAdd = LayoutUtils.arrangeComponantsInRow(false, ControlPanel.HORIZONTAL_FULL, input, new JSeparator(), controller,
				new JSeparator(), statusBar);

		this.add(panelToAdd);
	}

	public static void main(String args[]) {
		JFrame frame = new IncludeFilesToJarWindow();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 250);
		WindowUtils.addEscapeListener(frame);
		WindowUtils.setCenterLocation(frame);
		frame.setVisible(true);
	}

}
