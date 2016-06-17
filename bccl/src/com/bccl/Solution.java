/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.bccl;

import java.util.Date;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int arr[] = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = in.nextInt();
        }

    }

    public static void sort(int[] arr) {
        int pivote = arr[arr.length - 1];
        int lastSmallIndex = -1;
        int bigIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > pivote) {
                bigIndex = i;
                if (lastSmallIndex != -1) {

                }
                //This Element has to be moved after pivote.
            } else if (arr[i] < pivote) {
                //This Elemnt has to moved before.
            }

        }
    }

    public static int[] swap(int one, int two, int index1, int index2,int[] arr) {
       int temp = arr[index1];
       arr[index1] = arr[index2];
       arr[index2] = temp;
       return arr;
    }

}
