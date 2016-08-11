/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import com.tools.plugin.xmltreeviewer.tech.XmlNode;

public class XMLTreeView extends ControlPanel {

    private JTree treeView;
    private JScrollPane scroll;

    /**
     * 
     */
    public XMLTreeView() {
        this.setLayout(new GridLayout());
        this.setExpandable(true);
        this.setExpandPolicy(BOTH);
        init();

    }

    public void reset() {
        this.removeAll();
    }

    private void init() {        
        TreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("XML-Tree"));
        treeView = new JTree();
        treeView.setModel(treeModel);
        scroll = new JScrollPane(treeView);
        this.add(LayoutUtils.arrangeComponantsInColoumn(scroll).setExpandable(true).setExpandPolicy(BOTH));
        treeView.addTreeSelectionListener(new TreeSelectionListener() {
            private NodeView view = (NodeView) ApplicationContext.getObject(Application.NODEVIEW); 
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode element = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();                
                if(view!=null){
                    view.visualize(element);
                }
            }
        });
    }

    public void visualizeTree(XmlNode rootNode) {
        reset();
        init();
        DefaultTreeModel treeModel = (DefaultTreeModel) getTree().getModel();
        DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) treeModel.getRoot();
        treeRoot.setUserObject(rootNode);
        if (rootNode.hasChildren()) {
            updateTree(treeRoot, rootNode);
        }
        treeModel.reload();
        this.repaint();
        this.revalidate();

    }

    private void updateTree(DefaultMutableTreeNode parent, XmlNode parentNode) {
        if (parentNode.hasChildren()) {
            for (XmlNode childNode : parentNode.getChildren()) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(childNode);
                parent.add(child);
                updateTree(child, childNode);
            }
        }
    }

    protected JTree getTree() {
        return treeView;
    }

    /**
     * @return
     */
    public DefaultMutableTreeNode getRootXmlElement() {
        DefaultTreeModel treeModel = (DefaultTreeModel) getTree().getModel();
        DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) treeModel.getRoot();
        return treeRoot;
    }
}
