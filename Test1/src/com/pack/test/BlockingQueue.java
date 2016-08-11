/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.test;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {

	private final Queue<T> queue = new LinkedList<T>();
	private final int capacity;

	public BlockingQueue(final int capacity) {
		this.capacity = capacity;
	}

	public synchronized void insert(final T element) throws InterruptedException {
		while (queue.size() == capacity) {
			log("Queue is Full : Block this until queue is available");
			wait();
		}

		queue.add(element);
		log("Queue is NOT Empty : notify others to read");
		notify();
		// notifyAll();
	}

	public synchronized T remove() throws InterruptedException {
		while (queue.isEmpty()) {
			log("Queue is Empty : Block this until queue is available");
			wait();
		}

		final T item = queue.remove();
		log("Making Queue is avilable to fill : notify others to Fill");
		notify();
		// notifyAll();
		return item;
	}

	public synchronized void insert(final T element, final long timeout) throws InterruptedException {
		while (queue.size() == capacity) {
			log("Queue is Full : Block this until queue is available");
			wait(timeout);
		}

		queue.add(element);
		log("Queue is NOT Empty : notify others to read");
		notify();
		// notifyAll();
	}

	public synchronized T remove(final long timeout) throws InterruptedException {
		while (queue.isEmpty()) {
			log("Queue is Empty : Block this until queue is available");
			wait(timeout);
		}

		final T item = queue.remove();
		log("Making Queue is avilable to fill : notify others to Fill");
		notify();
		// notifyAll();
		return item;
	}

	public synchronized T peek() {
		return queue.peek();
	}

	public String getThreadName() {
		return Thread.currentThread().getName();
	}

	public void log(final String log) {
		// System.out.println(getThreadName()+" - "+log);
	}
}