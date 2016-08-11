/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

public class ThreadExample implements Runnable {

	private final BlockingQueue<Integer> queue;
	private String name;

	/**
	 *
	 */
	public ThreadExample(final BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	private static int count = 1;

	@Override
	public void run() {
		this.name = "Thread-" + count++;
		Thread.currentThread().setName(name);
		for (int i = 0; i < 10; i++) {
			log("- executing:" + i);
			try {
				queue.insert(i);
				Thread.sleep(10);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void log(final String log) {
		System.out.println(getThreadName() + " " + log);
	}

	public String getThreadName() {
		return this.name;
	}

}
