package com.producecosumer.myblockingqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create own blocking queue using lock interface & conditions
 * 
 * @author Tejas
 *
 */
public class MyBlockingQueueUsingLock<E> {

	// data structure to hold object
	private Queue<E> queue;
	private int max = 0;
	private Lock lock = new ReentrantLock(true);
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();

	public MyBlockingQueueUsingLock(int max) {
		super();
		this.queue = new LinkedList<E>();
		this.max = max;
	}

	public void put(E e) {
		lock.lock();
		try {
			// wait if queue is full
			while (queue.size() == max) {
				// block thread here
				notFull.await();
			}
			queue.add(e);
			// signal that queue is not empty
			notEmpty.signalAll();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public E take() {
		E e = null;
		lock.lock();
		try {
			// wait if queue is empty
			while (queue.size() == 0) {
				// block thread here
				notEmpty.await();
			}
			e = queue.remove();
			// signal that queue is not full
			notFull.signalAll();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
		return e;
	}
}
