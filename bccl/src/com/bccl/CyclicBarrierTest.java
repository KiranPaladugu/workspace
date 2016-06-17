/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.bccl;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    private CyclicBarrier barrier;

    class Worker implements Runnable {
        int i;
        public Worker(int value) {
            
        }
        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            for(int i =0;i<50;i++){
                System.out.println(Thread.currentThread().getName() +"-"+i);
            }
        }
    }

}
