package com.mylock;

public class Lock {

	private volatile boolean isLock = false;

	public synchronized void lock() throws InterruptedException {

		while (isLock) {
			wait();
		}
		isLock = true;
	}

	public synchronized void unlock() {

		isLock = false;
		notify();
	}
}
