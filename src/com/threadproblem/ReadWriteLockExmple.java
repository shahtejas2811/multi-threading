package com.threadproblem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Following sharedObject class shared by thread t1 & t2 
 * Read/Write Lock gives us more flexibility during locking and unlocking. Based on the type of
 * operation being performed over the object we can segregate the locks into
 * 
 * 1) readLock
 * 
 * 2) writeLock
 * 
 * readLock allows us to lock the object for read operation, and the interesting
 * point is that the read operation can be shared i.e if two threads are waiting
 * for readLock then both of them can proceed forward with the operation as read
 * operation doesn't change the data.
 * 
 * Where as writeLock is mutually exclusive i.e. if a writeLock is accepted then
 * all the other lock requests should wait till the thread that owns the lock
 * releases it.
 * 
 * @author Tejas
 *
 */
class ReadWriteSharedObject {

	private int x;

	// ReadWriteLock object for requesting the lock.
	ReadWriteLock rw_lock = new ReentrantReadWriteLock();
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	// increment value of x
	public void increment() {

		// This is in contrast to synchronized methods/blocks because for synchronized
		// method/block there is no segregation of read and write operations. Object is
		// locked no matter whether it is read or write.

		// Request the write lock as the operation is intended to modify the data.
		Lock lock = rw_lock.writeLock();
		try {
			lock.lock();
			int y = getX();
			y = y + 1;

			// put delay for simulation
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setX(y);
		} finally {
			lock.unlock();
		}
	}

}

class IncrementTask extends Thread {

	private ReadWriteSharedObject readWriteSharedObject;

	public IncrementTask(ReadWriteSharedObject readWriteSharedObject) {
		this.readWriteSharedObject = readWriteSharedObject;
	}

	public void run() {
		readWriteSharedObject.increment();
	}

}

public class ReadWriteLockExmple {

	public static void main(String[] args) {

		// passing share object to both thread t1 & t2

		ReadWriteSharedObject readWriteSharedObject = new ReadWriteSharedObject();
		readWriteSharedObject.setX(10);

		IncrementTask t1 = new IncrementTask(readWriteSharedObject);
		IncrementTask t2 = new IncrementTask(readWriteSharedObject);

		t1.start();
		t2.start();

		try {
			// wait for t1 & t2 should be completed
			t1.join();
			t2.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Value of variable " + readWriteSharedObject.getX());

	}
}
