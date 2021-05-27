package com.producecosumer.myblockingqueue;

import java.util.LinkedList;
import java.util.Queue;
/**
 * Create own blocking queue using wait & notify
 * @author Tejas
 *
 * @param <E>
 */
public class MyBlockingQueueUsingWaitNotify<E> {

	private Queue<E> queue;
	private int max = 0;
	private Object lock = new Object();
	

	public MyBlockingQueueUsingWaitNotify(int size) {
		queue = new LinkedList<E>();
		max = size;
	}

	public void put(E e) {
		
		synchronized(lock) {
			try {
				// check if queue is full
				while (queue.size() == max) {
					// wait producer thread here
					lock.wait();
				}
				queue.add(e);
				lock.notifyAll();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public  E take() {
		E e = null;
		synchronized(lock) {
			try {
				// check if queue is empty
				while (queue.size() == 0) {
					// wait consumer thread here
					lock.wait();
				}
				e = queue.remove();
				lock.notifyAll();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return e;
	}
}
