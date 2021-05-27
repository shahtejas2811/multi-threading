package com.threadproblem;

class Writer1 extends Thread {

	private Object book;
	private Object pen;

	public Writer1(Object book, Object pen) {
		this.book = book;
		this.pen = pen;
	}

	public void run() {

		synchronized (book) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (pen) {
				System.out.println("Writer 1 start");
			}
		}
	}
}

class Writer2 extends Thread {

	private Object book;
	private Object pen;

	public Writer2(Object book, Object pen) {
		this.book = book;
		this.pen = pen;
	}

	public void run() {
		// Lock sequencing is one possible solution for deadlock avoidance.
		// Adjust the lock sequence for Writer2
		synchronized (pen) { // book to avoid deadlock
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (book) { // pen to avoid deadlock
				System.out.println("Writer 2 start");
			}
		}
	}
}

public class DeadLock {
	
	public static void main(String[] args) {

		Object book = new Object();
		Object pen = new Object();

		Writer1 w1 = new Writer1(book, pen);
		w1.start();

		Writer2 w2 = new Writer2(book, pen);
		w2.start();

		System.out.println("Main Thread completed.");

	}
}
