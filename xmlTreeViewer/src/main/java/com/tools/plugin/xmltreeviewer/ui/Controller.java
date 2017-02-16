/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.tools.plugin.xmltreeviewer.tech.Message;
import com.tools.plugin.xmltreeviewer.tech.SimpleSaxXmlParser;

public class Controller extends ControlPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> input;
	private JButton browse;
	private JButton load;
	private final Vector<String> inputs = new Vector<String>();
	private final JFileChooser chooser = new JFileChooser();
	private FileFilter filter;

	public Controller() {
		filter = new FileFilter() {

			@Override
			public String getDescription() {
				return "*.xml";
			}

			@Override
			public boolean accept(final File f) {
				return (f.isDirectory() || f.getName().endsWith(".xml"));
			}
		};
		chooser.setFileFilter(filter);
		// chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setLayout(new GridLayout());
		this.setExpandPolicy(HORIZONTAL_FULL);
		input = new JComboBox<String>();
		final DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>(inputs);
		input.setModel(searchModel);
		input.setEditable(true);
		browse = new JButton("Browse");
		load = new JButton("Load");
		browse.addActionListener(this);
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final XMLTreeView navigator = (XMLTreeView) ApplicationContext.getObject(Application.NAVIGATOR);
				final NodeView nodeView = (NodeView) ApplicationContext.getObject(Application.NODEVIEW);
				chooser.showOpenDialog(null);
				final File file = chooser.getSelectedFile();
				if (file != null) {
					final SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
					final boolean status = parser.parse(file);
					if (status) {
						navigator.visualizeTree(parser.getNodeModel());
						nodeView.visualize(navigator.getRootXmlElement());
					} else {
						final Message message = ApplicationContext.getErrorMessageFromQueue();
						JOptionPane.showMessageDialog(null, "Opearion Failed\n" + ((Exception) message.getPayLoad()).getMessage(), "FATAL ERROR!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		this.load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = (String) input.getSelectedItem();
				if(fileName!=null && fileName.trim().length()>0){
					File fileToLoad = new File(fileName);
					if(fileToLoad.exists() && fileToLoad.isFile()){
						final XMLTreeView navigator = (XMLTreeView) ApplicationContext.getObject(Application.NAVIGATOR);
						final NodeView nodeView = (NodeView) ApplicationContext.getObject(Application.NODEVIEW);
						final SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
						final boolean status = parser.parse(fileToLoad);
						if (status) {
							navigator.visualizeTree(parser.getNodeModel());
							nodeView.visualize(navigator.getRootXmlElement());
						} else {
							final Message message = ApplicationContext.getErrorMessageFromQueue();
							JOptionPane.showMessageDialog(null, "Opearion Failed\n" + ((Exception) message.getPayLoad()).getMessage(), "FATAL ERROR!",
									JOptionPane.ERROR_MESSAGE);
						}
					}else{
						
						JOptionPane.showMessageDialog(null, "Can not load file..", "ERROR!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		this.add(LayoutUtils
				.arrangeComponantsInColoumn(false, HORIZONTAL_FULL,
						LayoutUtils.arrangeComponantsInColoumn(browse, LayoutUtils.arrangeComponantsInColoumn(input)),
						LayoutUtils.arrangeComponantsInColoumn(load).setExpandPolicy(TWENTY_PERCENT))
				.setExpandPolicy(HORIZONTAL_FULL).setExpandable(true));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(browse)) {

		}
	}
}
