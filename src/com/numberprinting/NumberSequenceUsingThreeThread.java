package com.numberprinting;

/**
 * 
 * Problem You are given 3 threads. You need to print sequence using these 3
 * threads.You need to print in natural order up to MAX.
 * 
 * For example: Let’s say you have 3 threads. T1,T2 and T3. If MAX is 10, you
 * need to print: t1 -> 1 t2 -> 2 t3 -> 3 t1 ->4 so on
 * 
 * @author Tejas
 *
 */
public class NumberSequenceUsingThreeThread {

	public static void main(String[] args) {

		Thread t1 = new Thread(new PrintSequenceRunnable(1), "T1");
		Thread t2 = new Thread(new PrintSequenceRunnable(2), "T2");
		Thread t3 = new Thread(new PrintSequenceRunnable(0), "T3");

		t1.start();
		t2.start();
		t3.start();
	}
}

/**
 * If number%3==1 then T1 will print the number and increment it else will go in the wait state. 
 * If number%3==2 then T2 will print the number and increment it else will go in the wait state.
 * If number%3==0 then T3 will print the number and increment it else will go in the wait state. 
 * @author Tejas
 *
 */
class PrintSequenceRunnable implements Runnable {

	private int PRINT_NUMBERS_UPTO = 10;
	private int remainder;

	// Below variable shared by all thread
	private static int number = 1;
	private static Object lock = new Object();

	PrintSequenceRunnable(int remainder) {
		this.remainder = remainder;
	}

	@Override
	public void run() {
		while (number < PRINT_NUMBERS_UPTO) {
			synchronized (lock) {
				while (number % 3 != remainder) { // wait for numbers other than remainder
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + " is printing --> " + number);
				number++;
				lock.notifyAll();
			}
		}
	}
}
