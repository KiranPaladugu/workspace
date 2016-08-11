/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadExceutorServiceMonitor implements Runnable {
	private int count = 1;
	private int jobs = Integer.MAX_VALUE;
	private final Stoppable stopObj;
	private final ExecutorService executorService;

	public ThreadExceutorServiceMonitor(final ExecutorService executorService, final int totalJobs, final Stoppable stopableObj) {
		this.executorService = executorService;
		this.jobs = totalJobs;
		this.stopObj = stopableObj;
	}

	@Override
	public void run() {
		while (count <= 100) {
			if (executorService instanceof ThreadPoolExecutor) {
				final ThreadPoolExecutor excecutor = (ThreadPoolExecutor) executorService;
				if (excecutor.getCompletedTaskCount() == jobs && excecutor.getActiveCount() == 2) {
					System.out.println("Completed all Executions");
					if (stopObj != null) {
						stopObj.stop();
					}
					break;
				}
			}
			System.out.println(executorService);
			try {
				Thread.sleep(5);
				count++;
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(executorService);
		executorService.shutdown();
	}

}
