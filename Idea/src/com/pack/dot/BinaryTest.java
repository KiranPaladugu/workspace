/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.dot;

public class BinaryTest {
    public static void main(String args[]){
        int a =180522 ;
        int b =0;
        int n = 16;
        System.out.println(Integer.toBinaryString(a|b)+":"+(a|b));
        System.out.println(Integer.toBinaryString(a&b)+":"+(a&b));
        System.out.println(Integer.toBinaryString(a^b)+":"+(a^b));
        int c =0;
        c^= a;
        c ^= (c >>> 20) ^ (c >>> 12);
        System.out.println("Expression :"+(c ^ (c >>> 7) ^ (c >>> 4)));
        System.out.println("Expression2 :"+(c & n));
//        System.out.println(Integer.toBinaryString(a^b)+":"+(a^b));
    }
}
