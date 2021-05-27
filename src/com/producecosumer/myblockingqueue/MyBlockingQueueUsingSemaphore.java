package com.producecosumer.myblockingqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Create own blocking queue using binary Semaphores
 * 
 * @author Tejas
 *
 */
public class MyBlockingQueueUsingSemaphore<E> {

	private Queue<E> queue;
	private int max = 0;

	private Semaphore semPro = new Semaphore(1);
	// semCon initialized with 0 permits to ensure put() executes first
	private Semaphore semCon = new Semaphore(0);

	public MyBlockingQueueUsingSemaphore(int maxsize) {
		this.queue = new LinkedList<E>();
		this.max = maxsize;
	}

	public void put(E e) {
		try {
			// Before producer can produce an item,it must acquire a permit from semCon
			semPro.acquire();
			while (queue.size() == max) {
				// A hint to the scheduler that the current thread is willing to yield its
				// current use of a processor. The scheduler is free to ignore this hint.
				Thread.yield();
			}
			queue.add(e);
			// After producer produce the item, it releases semCon to notify consumer
			semCon.release();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public E take() {
		E e = null;
		try {
			// Before consumer can consume an item,it must acquire a permit from semCon
			semCon.acquire();
			while (queue.size() == 0) {
				Thread.yield();
			}
			e = queue.remove();
			// After consumer consumes the item, it releases semProd to notify producer
			semPro.release();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return e;

	}
}
