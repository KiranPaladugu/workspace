/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.dot;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Node<T> {
    private List<Node<T>> children = new LinkedList<Node<T>>();
    private Node<T> previous;
    private Node<T> next;
    private Node<T> parent;
    private T UserObject;
    private int level;
    
    /**
     * 
     */
    public TreeNode(T userObject) {
        this.UserObject = userObject;
    }
    
    
    @Override
    public Node<T> getNextNode() {
        return this.next;
    }

    @Override
    public Node<T> getPreviousNode() {
        return this.previous;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#getParent()
     */
    @Override
    public Node<T> getParent() {
        return parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#getAllChildren()
     */
    @Override
    public List<Node<T>> getAllChildren() {
        return this.children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#addChild(com.pack.dot.Tree)
     */
    @Override
    public int addChild(Node<T> child) {
        boolean flag = this.children.add(child);
        return (flag)? 1 : 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#hasParent()
     */
    @Override
    public boolean hasParent() {
        return parent != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#isLatst()
     */
    @Override
    public boolean isLast() {
        return next == null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#isFirst()
     */
    @Override
    public boolean isFirst() {
        return previous == null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#setParent(com.pack.dot.Tree)
     */
    @Override
    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#setNext(com.pack.dot.Tree)
     */
    @Override
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pack.dot.Tree#setPrevious(com.pack.dot.Tree)
     */
    @Override
    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    /* (non-Javadoc)
     * @see com.pack.dot.Node#setUserObject(java.lang.Object)
     */
    @Override
    public void setUserObject(T userData) {
        this.UserObject = userData;
    }

    /* (non-Javadoc)
     * @see com.pack.dot.Node#getuserObject()
     */
    @Override
    public T getuserObject() {
        return UserObject;
    }


    @Override
    public String toString() {
        return "[children=" + children + ", previous=" + previous + ", next=" + next + ", parent=" + parent
                + ", UserObject=" + UserObject + "]";
    }


    /* (non-Javadoc)
     * @see com.pack.dot.Node#getTreeRoot()
     */
    @Override
    public Node<T> getTreeRoot() {
        if(parent!=null){
            return parent.getTreeRoot();
        }
        return this;
    }
    
    public List<Node<T>> sort(){
        return null;
    }


    /**
     * @return the level
     */
    public int getLevel() {
        setLevel();
        return level;
    }


    /**
     * @param level the level to set
     */
    public void setLevel() {
        if(parent!=null){
            this.level = parent.getLevel()+1;
        }
    }

}
