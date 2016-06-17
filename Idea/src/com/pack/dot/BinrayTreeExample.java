/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.dot;

import java.util.List;
import java.util.Random;

public class BinrayTreeExample {
    public static void main(String[] args){
        BinaryTreeNode<String> testTree = new BinaryTreeNode<String>("First");
        Random random = new Random(Integer.MAX_VALUE);
        for(int i =0 ; i<100;i++){
            testTree.addChild(new BinaryTreeNode<String>(""+random.nextLong()));
            System.out.println();
        }
        Watch watch = new Watch();
        watch.startWatch("BinaryTreeExample");
        List<Node<String>> list = testTree.sort();
        watch.stop();
        for(Node<String> n : list){
            System.out.println(n.getuserObject().hashCode()+","+n.getLevel());
        }
//        System.out.print("\b");
        System.out.println();
        System.out.println("AddedCount:"+testTree.getAddedCount());
        System.out.println("SortedTree size:"+list.size());
        System.out.println("Done!");
        watch.stats();
        
    }
    
}
