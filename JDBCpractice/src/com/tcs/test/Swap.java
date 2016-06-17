/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.test;

public class Swap {

    public static void main(String args[]){
        int x = Integer.MAX_VALUE;
        int y= Integer.MIN_VALUE;
        System.out.println("Initial values of  X :"+x+", Y:"+y);
        x = x ^ y ;
        System.out.println("FirstRout X :"+x+", Y:"+y);
        y = x ^ y;
        System.out.println("Second ROund X :"+x+", Y:"+y);
        x = x ^ y;
        System.out.println("END X :"+x+", Y:"+y);
    }
}
