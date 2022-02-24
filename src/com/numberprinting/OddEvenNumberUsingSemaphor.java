package com.numberprinting;

import java.util.concurrent.Semaphore;

public class OddEvenNumberUsingSemaphor {
	Semaphore semOdd = new Semaphore(1);
	Semaphore semEven = new Semaphore(0);

	public void printEven() {
		try {
			for (int i = 0; i < 10; i = i + 2) {
				semEven.acquire();
				System.out.printf("%d is printed by %s \n", i, Thread.currentThread().getName());
				semOdd.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void printOdd() {
		try {
			for (int i = 1; i < 10; i = i + 2) {
				semOdd.acquire();
				System.out.printf("%d is printed by %s \n", i, Thread.currentThread().getName());
				semEven.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		OddEvenNumberUsingSemaphor pc = new OddEvenNumberUsingSemaphor();

		Runnable even = () -> pc.printEven();
		Runnable odd = () -> pc.printOdd();

		Thread t1 = new Thread(odd, "t1");
		Thread t2 = new Thread(even, "t2");

		t1.start();
		t2.start();

	}

}
