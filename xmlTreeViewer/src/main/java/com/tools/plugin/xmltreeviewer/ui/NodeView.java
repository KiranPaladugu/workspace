/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.tools.plugin.xmltreeviewer.tech.XmlNode;

public class NodeView extends ControlPanel {
    /**
     * 
     */
    private JTable attributes;
    private JLabel lbl_name,lbl_uri;
    private JTextField txt_name;
    private ChildNodeButton parent;
    private JTextArea txt_data,txt_xml;
//    private XmlNode parentNode;
    private JScrollPane chilrenScroll;
//    private JList<XmlNode> children;
    private ControlPanel children = new ControlPanel(true, VERTICAL_FULL);
    private JTextField txt_uri;
    private DefaultTableModel tableModel = new DefaultTableModel();
    public NodeView() {
        this.setExpandable(true);
        this.setExpandPolicy(BOTH);
        this.setLayout(new GridLayout());
        init();
    }
    
    public void init(){
        tableModel.addColumn("Name");
        tableModel.addColumn("Vaule");
        children.setLayout(new GridBagLayout());
        chilrenScroll = new JScrollPane(children);
        lbl_name = new JLabel("Tag name:");
        lbl_uri = new JLabel("Uri");
        txt_name = new JTextField();
        txt_name.setEditable(false);
        txt_uri = new JTextField();
        txt_uri.setEditable(false);
        txt_data = new JTextArea();
        txt_data.setEditable(false);
        txt_xml = new JTextArea();        
        txt_xml.setEditable(false);
//        children.add(LayoutUtils.arrangeComponantsInRow(new JButton("child1"),new JButton("child2"),new JButton("child3")));
        parent = new ChildNodeButton("Parent");
        attributes = new JTable(tableModel);
        
        
        ControlPanel props = LayoutUtils.arrangeComponantsInColoumn(LayoutUtils.arrangeComponantsInRow(false,lbl_name,lbl_uri).setExpandPolicy(NONE),LayoutUtils.arrangeComponantsInRow(true, txt_name,txt_uri)).setExpandPolicy(HORIZONTAL_FULL);
        ControlPanel data = LayoutUtils.arrangeComponantsInRow(props,LayoutUtils.arrangeComponantsInColoumn("data",BOTH,true,new JScrollPane(txt_data)).setExpandPolicy(TWENTY_PERCENT),parent);
        ControlPanel others = LayoutUtils.arrangeComponantsInColoumn(true,BOTH,LayoutUtils.arrangeComponantsInColoumn("children",chilrenScroll).setExpandPolicy(BOTH),LayoutUtils.arrangeComponantsInColoumn("Attributes",new JScrollPane(attributes)));
        ControlPanel main = LayoutUtils.arrangeComponantsInColoumn(true,data,others).setExpandPolicy(BOTH);
        this.add(LayoutUtils.arrangeComponantsInRow(true,main,LayoutUtils.arrangeComponantsInColoumn("XML Data",BOTH, true, new JScrollPane(txt_xml)).setExpandPolicy(BOTH)));
    }
    
    
    public void visualize(DefaultMutableTreeNode nodeElement) {
        XmlNode element = (XmlNode) nodeElement.getUserObject();
        this.setTagName(element.getTagName());
        this.setUri(element.getUri());
        this.setAttributes(element);
        this.setData(element.getValue());   
        this.setChildren(nodeElement);
        this.setParent((DefaultMutableTreeNode) nodeElement.getParent());
        this.setXmlData(element.toXml());
        this.repaint();
    }

    /**
     * @param nodeElement
     */
    private void setChildren(DefaultMutableTreeNode nodeElement) {
        LayoutUtils utils = LayoutUtils.getUtils("util");
        ControlPanel panel = new ControlPanel(false, VERTICAL_FULL);
        panel.setLayout(new GridBagLayout());
        for(int i=0; i<nodeElement.getChildCount();i++){
            ChildNodeButton button =new ChildNodeButton(((DefaultMutableTreeNode)nodeElement.getChildAt(i)).getUserObject().toString());
            button.setNode((DefaultMutableTreeNode)nodeElement.getChildAt(i));
            panel.add(button,utils.getNextRowConstaints());
        }
        this.children.removeAll();
        this.children.add(panel);
        this.children.repaint();
        this.children.revalidate();
        this.chilrenScroll.repaint();
        this.chilrenScroll.revalidate();
    }
    
    public void setParent(DefaultMutableTreeNode defaultMutableTreeNode){
        this.parent.setNode(defaultMutableTreeNode);
    }
    
    public void setXmlData(String xmlData){
        this.txt_xml.setText(xmlData);
    }

    /**
     * @param tagName
     */
    public void setTagName(String tagName) {
       txt_name.setText(tagName);
    }
    public void setUri(String uri){
        txt_uri.setText(uri);
    }
    
    public void setAttributes(XmlNode node){
        Set<String> attribNames = node.getAttributeNames();
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }        
        for(String name:attribNames){
            String[] newAttribute = {name,node.getAttribute(name)};
            tableModel.addRow(newAttribute);
        }
        this.attributes.repaint();
    }
    
    public void setData(String data){
        txt_data.setText(data);
    }
    
    
}
