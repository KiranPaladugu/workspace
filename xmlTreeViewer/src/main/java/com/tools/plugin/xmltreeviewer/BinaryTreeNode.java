/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer;

import java.util.*;

public class BinaryTreeNode<T> extends TreeNode<T> {
    /**
     * @param userObject
     */
    public BinaryTreeNode(T userObject) {
        super(userObject);
    }

    private Node<T> leftChild;
    private Node<T> rightChild;
    private List<Node<T>> childrentList = new LinkedList<Node<T>>();
    private List<Node<T>> sortedList = null;
    private int count;
    
    /* (non-Javadoc)
     * @see com.pack.dot.GenericTree#addChild(com.pack.dot.Tree)
     */
    @Override
    public int addChild(Node<T> child) {
        count++;
        System.out.println(getuserObject()+":[#"+getuserObject().hashCode()+"]");
//        super.addChild(child);
        int chidHash = child.getuserObject().hashCode();
        if(getuserObject().hashCode()>=chidHash){
            if(leftChild !=null){
                System.out.println("[#"+chidHash+"]->Add To LeftChild");
                return leftChild.addChild(child);
            }else{
                System.out.println("[#"+chidHash+"]->set as LeftChild");
                leftChild = child;
                leftChild.setParent(this);
                childrentList.add(leftChild);
                if(rightChild!=null){
                    rightChild.setPrevious(child);                    
                }
                leftChild.setNext(rightChild);
                return -1;
            }
        }else{
            if(rightChild!=null){
                System.out.println("[#"+chidHash+"]->Add To RightChild");
                return rightChild.addChild(child);
            }else{
                System.out.println("[#"+chidHash+"]->set as RightChild");
                rightChild = child;
                rightChild.setParent(this);
                childrentList.add(rightChild);
                if(leftChild!=null){
                    leftChild.setNext(rightChild);
                }
                rightChild.setPrevious(leftChild);
                return 1;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.pack.dot.GenericTree#getAllChildren()
     */
    @Override
    public List<Node<T>> getAllChildren() {
        return childrentList;
    }
    
    public Node<T> getLeftChild(){
        return this.leftChild;
    }
    
    public Node<T> getRightChild(){
        return this.rightChild;
    }
    
    public boolean isPresentLeftChild(){
        return this.leftChild!=null;
    }
    
    public boolean isPresentRightChild(){
        return this.rightChild!=null;
    }
    
    public boolean hasChildren() {
     return isPresentLeftChild()||isPresentRightChild();   
    }

    @Override
    public String toString() {
        return "[leftChild=" + leftChild + ", rightChild=" + rightChild + ", childrentList=" + childrentList + "]";
    }
    
    public List<Node<T>> sort(){
        if(sortedList!=null){
            return sortedList;
        }
        List<Node<T>> sorted = new ArrayList<Node<T>>();
        if(this.leftChild!=null){
            if(leftChild.hasChildren()){
                sorted.addAll(leftChild.sort());
            }
//            sorted.add(leftChild);
        }
        sorted.add(this);
        if(rightChild!=null){
            if(rightChild.hasChildren()){
                sorted.addAll(rightChild.sort());
            }
//            sorted.add(rightChild);
        }
        this.sortedList = sorted;
        return this.sortedList;
    }
    
    public int getAddedCount(){
        return count;
    }
}
