package com.threadproblem;

/**
 * Following sharedObject class shared by thread t1 & t2
 * 
 * @author Tejas
 *
 */
class SharedObject {

	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	// increment value of x
	public void increment() {

		// to make below code thread safe using synchronized block
		synchronized (this) {

			int y = getX();
			y = y + 1;
		
			// put delay for simulation
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setX(y);
		}
	}

}

class IncrementThread extends Thread {

	private SharedObject sharedObject;

	public IncrementThread(SharedObject sharedObject) {
		this.sharedObject = sharedObject;
	}

	public void run() {
		sharedObject.increment();
	}

}

public class SyncronizationUsingShreadObject {

	public static void main(String[] args) {

		// passing share object to both thread t1 & t2

		SharedObject sharedObject = new SharedObject();
		sharedObject.setX(10);

		IncrementThread t1 = new IncrementThread(sharedObject);
		IncrementThread t2 = new IncrementThread(sharedObject);

		t1.start();
		t2.start();

		try {
			// wait for t1 & t2 should be completed
			t1.join();
			t2.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Value of variable " + sharedObject.getX());

	}
}
