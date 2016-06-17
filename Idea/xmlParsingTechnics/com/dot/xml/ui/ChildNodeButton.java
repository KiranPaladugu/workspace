/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ChildNodeButton extends JButton implements ActionListener {
    private DefaultMutableTreeNode node;

    /**
     * @param string
     */
    public ChildNodeButton(String string) {
        super(string);
    }

    /**
     * @return the node
     */
    public DefaultMutableTreeNode getNode() {
        return node;
    }

    /**
     * @param node
     *            the node to set
     */
    public void setNode(DefaultMutableTreeNode node) {
        this.node = node;
        if ((node!=null) && node.getPath() != null) {
            this.addActionListener(this);
        }else{
            this.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        XMLTreeView view = (XMLTreeView) ApplicationContext.getObject(Application.NAVIGATOR);
        if (view != null && node !=null) {
            TreePath path = new TreePath(node.getPath());
            view.getTree().setSelectionPath(path);
        }
    }

}
