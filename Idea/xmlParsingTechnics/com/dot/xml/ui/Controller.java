/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.dot.xml.parsing.tech.Message;
import com.dot.xml.parsing.tech.SimpleSaxXmlParser;

public class Controller extends ControlPanel implements ActionListener{

    /**
     * 
     */
    private JComboBox<String> input;
    private JButton browse;
    private JButton load;
    private Vector<String> inputs = new Vector<String>();
    private JFileChooser chooser = new JFileChooser();
    private FileFilter filter ;   

    public Controller() {
        filter = new FileFilter() {
            
            @Override
            public String getDescription() {
                return "*.xml";
            }
            
            @Override
            public boolean accept(File f) {
                return (f.isDirectory() || f.getName().endsWith(".xml"));
            }
        };
        chooser.setFileFilter(filter);
//        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.setLayout(new GridLayout());
        this.setExpandPolicy(HORIZONTAL_FULL);
        input = new JComboBox<String>();
        DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>(inputs);
        input.setModel(searchModel);
        input.setEditable(true);
        browse = new JButton("Browse");
        load = new JButton("Load");
        browse.addActionListener(this);
        browse.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                XMLTreeView navigator = (XMLTreeView) ApplicationContext.getObject(Application.NAVIGATOR);
                NodeView nodeView = (NodeView) ApplicationContext.getObject(Application.NODEVIEW);
                chooser.showOpenDialog(null);
                File file = chooser.getSelectedFile();
                if(file!=null){
                    SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
                    boolean status=parser.parse(file);
                    if(status){
                    navigator.visualizeTree(parser.getNodeModel());
                    nodeView.visualize(navigator.getRootXmlElement());
                    }else{
                        Message message = ApplicationContext.getErrorMessageFromQueue();
                        JOptionPane.showMessageDialog(null, "Opearion Failed\n"+((Exception)message.getPayLoad()).getMessage(), "FATAL ERROR!", JOptionPane.ERROR_MESSAGE);                        
                    }
                }
            }
        });
        this.add(LayoutUtils.arrangeComponantsInColoumn(false, HORIZONTAL_FULL, LayoutUtils.arrangeComponantsInColoumn(browse,LayoutUtils.arrangeComponantsInColoumn(input)), LayoutUtils.arrangeComponantsInColoumn(load).setExpandPolicy(TWENTY_PERCENT))
                .setExpandPolicy(HORIZONTAL_FULL).setExpandable(true));
    }

   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(browse)){
            
        }
    }
}
