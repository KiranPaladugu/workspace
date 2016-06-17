/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.dot;

public class StackTest {
    public static void main(String args[]){
        BlockingStack<Integer> stack = new BlockingStack<Integer>();
        for(int i =0; i<15;i++){
            System.out.println("PUSHING: "+i);
            if(stack.push(i)){
                System.out.println("PUSHING RESUT: SUCCESS");
            }else{
                System.out.println("PUSHING RESUT: FAILED");
            }
            System.out.println("PEEK:"+stack.peek());
            System.out.println();
        }
        
        for(int i =0; i<15;i++){           
            System.out.println("Stack PEEK:"+stack.peek());
            System.out.println("POPPED:"+stack.pop());
            System.out.println();
        }
    }
}
