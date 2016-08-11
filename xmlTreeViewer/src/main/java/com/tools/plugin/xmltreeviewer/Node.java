/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer;

import java.util.List;

public interface Node<T> {
    public Node<T> getNextNode();
    public Node<T> getPreviousNode();
    public Node<T> getParent();
    public void setParent(Node<T> parent);
    public void setNext(Node<T> next);
    public void setPrevious(Node<T> previous);
    public List<? extends Node<T>> getAllChildren();
    public int addChild(Node<T> child);
    public boolean hasParent();
    public boolean hasChildren();
    public boolean isLast();
    public boolean isFirst();
    public void setUserObject(T userData);
    public T getuserObject();
    public Node<T> getTreeRoot();
    public List<Node<T>> sort();
    public int getLevel();
    public void setLevel();
}
