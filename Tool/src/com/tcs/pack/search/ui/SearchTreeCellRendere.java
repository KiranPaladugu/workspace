package com.tcs.pack.search.ui;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class SearchTreeCellRendere extends DefaultTreeCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean,
     * boolean, boolean, int, boolean)
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }

}
