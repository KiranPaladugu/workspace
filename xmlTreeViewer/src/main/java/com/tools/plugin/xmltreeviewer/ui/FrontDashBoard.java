/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.tools.plugin.xmltreeviewer.tech.SimpleSaxXmlParser;

public class FrontDashBoard extends JPanel {

    private XMLTreeView treeView;
    private JSplitPane splitView;
    private Controller controller;
    private NodeView nodeView;
    //    private
    /**
     * 
     */
    public FrontDashBoard() {
        this.setLayout(new GridLayout(1, 1));
        treeView = new XMLTreeView();
        ApplicationContext.putObject(Application.NAVIGATOR, treeView);       
        controller = new Controller();
        ApplicationContext.putObject(Application.CONTROLLER, controller);
        nodeView = new NodeView();
        ApplicationContext.putObject(Application.NODEVIEW, nodeView);
        splitView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LayoutUtils.arrangeComponantsInColoumn(treeView).setExpandable(true), nodeView);
        JPanel panelToAdd = LayoutUtils.arrangeComponantsInRow( controller.setExpandable(true).setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
                LayoutUtils.arrangeComponantsInColoumn(true, ControlPanel.BOTH, splitView).setExpandable(true)
                        .setExpandPolicy(ControlPanel.BOTH));
        splitView.setDividerSize(2);
        splitView.setDividerLocation(175);
        this.add(panelToAdd);
        
        
      /*  String fileName="C:\\Users\\ekirpal\\Desktop\\getResponse.xml";        
        SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
        parser.parse(new File(fileName));
        treeView.visualizeTree(parser.getNodeModel());*/
//        nodeView.visualize(parser.getNodeModel().getFirstChild());
    }
    
    public void load(String xml){
        SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
        parser.parse(xml);
        treeView.visualizeTree(parser.getNodeModel());
        nodeView.visualize(treeView.getRootXmlElement());
    }

    /**
     * @param xml
     */
    public void displayXml(String xml) {
        load(xml);
        
    }
}
