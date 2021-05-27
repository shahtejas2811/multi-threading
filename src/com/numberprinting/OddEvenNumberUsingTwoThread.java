package com.numberprinting;
/*
 * Following program print odd-even number using two thread
 * First thread print odd number and second thread print even number
 * 
 * @author: Tejas Shah
 * @since: 26 April 2015
 *
 */

public class OddEvenNumberUsingTwoThread {

	public static void main(String[] args) {

		Thread t1 = new Thread(new NumberPrintingRunnable(1), "t1");
		Thread t2 = new Thread(new NumberPrintingRunnable(0), "t2");
		t1.start();
		t2.start();
	}

}

/**
 * Worker which is responsible printing number.
 * 
 * @author Tejas
 * 
 */
class NumberPrintingRunnable implements Runnable {

	public static final int MAX = 10;
	private static int number = 1;
	private static Object mutex = new Object();
	private int reminder;

	public NumberPrintingRunnable(int reminder) {
		this.reminder = reminder;
	}

	@Override
	public void run() {
		try {
			synchronized (mutex) {
				while (number < MAX) {
					if (number % 2 != reminder) {
						mutex.wait();
					}
					System.out.printf("%d print by Thread %s \n", number, Thread.currentThread().getName());
					number++;
					mutex.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}
}
