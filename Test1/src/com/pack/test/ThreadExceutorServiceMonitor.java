/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadExceutorServiceMonitor implements Runnable{
    private int count =1; 
    private int jobs=Integer.MAX_VALUE;
    private Stoppable stopObj;
    private ExecutorService executorService;
    
    public ThreadExceutorServiceMonitor(ExecutorService executorService, int totalJobs,Stoppable stopableObj) {
        this.executorService=executorService;
        this.jobs=totalJobs;
        this.stopObj = stopableObj;
    }
    
    @Override
    public void run() {
        while(count<=100){
            if(executorService instanceof ThreadPoolExecutor){
                ThreadPoolExecutor excecutor = (ThreadPoolExecutor) executorService;
                if(excecutor.getCompletedTaskCount()==jobs){
                    System.out.println("Completed all Executions");
                    if(stopObj!=null){
                        stopObj.stop();
                    }
                   break;
                }
            }
            System.out.println(executorService);
            try {
                Thread.sleep(2000);
                count ++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
