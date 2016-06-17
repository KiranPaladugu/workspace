/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.HashMap;
import java.util.Map;

public class QueueProcessor implements Runnable,Stoppable {

    private BlockingQueue<Integer> queue;
    private Map<String, Integer> counts = new HashMap<String, Integer>();
    private boolean stop = false;

    /**
     * 
     */
    public QueueProcessor(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Queue Processor");
        while (!stop) {
            try {
                System.out.println(counts);
                Integer value = queue.remove();
                if(counts.containsKey(value.toString())){
                    int count = counts.get(value.toString());
                    count++;
                    counts.put(value.toString(), count);
                }else{
                    counts.put(value.toString(), 0);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public synchronized void stop(){
        this.stop = true;
    }

}