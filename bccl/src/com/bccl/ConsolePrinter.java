/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.bccl;

public class ConsolePrinter {
    public synchronized void printNumber(int number, int maxNum) {
        try {
            notify();
            System.out.println(Thread.currentThread().getName()+"-"+number);
            if (number < maxNum)
                wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
