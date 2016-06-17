package com.tcs.pack.ui.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.tcs.pack.serarch.Mainwindow;
import com.tcs.pack.utils.DialogUtils;

public class MainWindowMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu file, help, settings;
	private JMenuItem exit, helpContents, about, config;
	private Mainwindow parent;

	/**
	 * 
	 */
	public MainWindowMenuBar(Mainwindow parent) {
		this.parent = parent;
		init();
		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.performExitOperation();
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
	 * 
	 */
	private void init() {
		file = new JMenu("File");
		settings = new JMenu("Settings");
		help = new JMenu("Help");

		exit = new JMenuItem("Exit");
		helpContents = new JMenuItem("Contents");
		helpContents.setEnabled(false);
		about = new JMenuItem("About");
		config = new JMenuItem("Configuration");
		config.setEnabled(false);

		file.add(exit);
		settings.add(config);
		help.add(helpContents);
		help.add(new JSeparator());
		help.add(about);

		this.add(file);
		this.add(settings);
		this.add(help);

	}
}
