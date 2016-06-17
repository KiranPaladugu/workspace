package com.tcs.pack.search.ui;

import javax.swing.tree.DefaultMutableTreeNode;

public class SearchTreeNode extends DefaultMutableTreeNode {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object object;
    public SearchTreeNode() {
        super();

    }

    public SearchTreeNode(Object obj) {
        super(obj);
        this.object = obj;
    }

    public SearchTreeNode(Object obj, boolean bool) {
        super(obj, bool);
        this.object = obj;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }
}
