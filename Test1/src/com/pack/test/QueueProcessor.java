/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.HashMap;
import java.util.Map;

public class QueueProcessor implements Runnable, Stoppable {

	private final BlockingQueue<Integer> queue;
	private final Map<String, Integer> counts = new HashMap<String, Integer>();
	private final boolean stop = false;

	/**
	 *
	 */
	public QueueProcessor(final BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("Queue Processor");
		while (!stop) {
			try {
				System.out.println(counts);
				final Integer value = queue.remove();
				if (value == -1) {
					break;
				}
				if (counts.containsKey(value.toString())) {
					int count = counts.get(value.toString());
					count++;
					counts.put(value.toString(), count);
				} else {
					counts.put(value.toString(), 0);
				}
				System.out.println("Processed:" + value);
				Thread.sleep(5);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Exiting processor");
	}

	@Override
	public synchronized void stop() {
		try {
			queue.insert(-1);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
