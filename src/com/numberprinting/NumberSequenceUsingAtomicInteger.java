package com.numberprinting;

import java.util.concurrent.atomic.AtomicInteger;

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
public class NumberSequenceUsingAtomicInteger {

	private static AtomicInteger number = new AtomicInteger(1);

	public static void main(String args[]) {

		Thread t1 = new Thread(new ThreadTasks(1, number), "T1");
		Thread t2 = new Thread(new ThreadTasks(2, number), "T2");
		Thread t3 = new Thread(new ThreadTasks(0, number), "T3");

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
class ThreadTasks implements Runnable {

	private final int remainder;
	private AtomicInteger number;

	public ThreadTasks(int remainder, AtomicInteger number) {
		this.remainder = remainder;
		this.number = number;
	}

	@Override
	public void run() {

		while (number.get() < 10) {

			synchronized (number) {

				if (number.get() % 3 == this.remainder) {

					System.out.println("Printing output for Thread: " + Thread.currentThread().getName() + "  "
							+ number.getAndIncrement());

				}
			}

		}
	}

}
