/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

public class ThreadRunner {
    public static void main(String args[]) {
        ThreadExample current = null;
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        Thread queThread = new Thread(new QueueProcessor(queue));
        queThread.start();
        for (int i = 0; i <24; i++) {            
            current = new ThreadExample(queue);
            Thread thread = new Thread(current);
            thread.start();
        }
    }
}
