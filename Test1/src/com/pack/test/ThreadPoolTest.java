/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void main(String args[]) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        QueueProcessor processor = new QueueProcessor(queue);
        executorService.submit(processor);
        executorService.submit(new ThreadExceutorServiceMonitor(executorService,15,processor));
        ThreadExample current = null;
        for (int i = 0; i < 15; i++) {
            current = new ThreadExample(queue);
            executorService.submit(current);
        }
        
    }
}
