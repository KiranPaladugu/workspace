/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
	public static void main(final String args[]) {
		final BlockingQueue<Integer> queue = new BlockingQueue<>(5);
		final ExecutorService executorService = Executors.newFixedThreadPool(5);
		final QueueProcessor processor = new QueueProcessor(queue);
		executorService.submit(processor);
		executorService.submit(new ThreadExceutorServiceMonitor(executorService, 9, processor));
		ThreadExample current = null;
		for (int i = 0; i < 9; i++) {
			current = new ThreadExample(queue);
			executorService.submit(current);
		}

	}
}
