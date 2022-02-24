package com.numberprinting;

import java.util.concurrent.Semaphore;

/**
 * The same instance of ZeroEvenOdd will be passed to three different threads:
 * 
 * Thread A will call zero() which should only output 0'
 * Thread B will call even() which should only output even numbers.
 * Thread C will call odd() which should only output odd numbers.
 * Each of the threads is given a printNumber method to output an integer.
 * Modify the given program to output the series 010203040506... where the length of the series must be 2n.
 * @author Tejas
 *
 */
public class ZeroEvenOddUsingSemaphor {
	
	private int n;
	private int lastPrinted;
	private Semaphore semaphoreZero; 
	private Semaphore semaphoreOdd; 
	private Semaphore semaphoreEven; 
	
	public ZeroEvenOddUsingSemaphor(int n) {
		this.n = n;
		this.lastPrinted = 0;
		this.semaphoreZero = new Semaphore(1);
		this.semaphoreOdd = new Semaphore(1);
		this.semaphoreEven= new Semaphore(1);
	
		try {
			// t1 & t2 acquire permit  
			semaphoreOdd.acquire();
			semaphoreEven.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void zero() {
		try {
			int numZero = n;
			while (numZero-- > 0) {
				//During second iteration t0 wait here until either method odd or even release semaphore
				semaphoreZero.acquire();
				System.out.println(" "+Thread.currentThread().getName()  + ":  0" );
				
				if(lastPrinted % 2 == 0) { 
					//Last printed number is even then release odd semaphore
					 semaphoreOdd.release();
				} else {
					//Last printed number is odd then release even semaphore
					semaphoreEven.release();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void odd() {
		try {
			int numOdd = n - n / 2;
			while (numOdd-- > 0) {
				// t1 block here until permit release by zero method
				semaphoreOdd.acquire();
				System.out.println(" " + Thread.currentThread().getName() + ":" + ++lastPrinted);
				semaphoreZero.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void even() {
		try {
			int numeven = n / 2;
			while (numeven-- > 0) {
				// t2 block here until permit release by zero method
				semaphoreEven.acquire();
				System.out.println(" " + Thread.currentThread().getName() + ":" + ++lastPrinted);
				semaphoreZero.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor = new ZeroEvenOddUsingSemaphor(10);
		
		Thread t0 = new Thread(new Thread0(zeroEvenOddUsingSemaphor),"Zero");
		Thread t1 = new Thread(new Thread1(zeroEvenOddUsingSemaphor),"Odd");
		Thread t2 = new Thread(new Thread2(zeroEvenOddUsingSemaphor),"Even");
		
		t0.start();
		t1.start();
		t2.start();
		
	}
}

class Thread0 implements Runnable {
	
	private ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor;

	public Thread0(ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor) {
		this.zeroEvenOddUsingSemaphor = zeroEvenOddUsingSemaphor;
	}

	@Override
	public void run() {
		zeroEvenOddUsingSemaphor.zero();

	}

}

class Thread1 implements Runnable {

	private ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor;

	public Thread1(ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor) {
		this.zeroEvenOddUsingSemaphor = zeroEvenOddUsingSemaphor;
	}

	@Override
	public void run() {
		zeroEvenOddUsingSemaphor.odd();
	}

}

class Thread2 implements Runnable{
	
	private ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor;
	
	public Thread2 (ZeroEvenOddUsingSemaphor zeroEvenOddUsingSemaphor){
		this.zeroEvenOddUsingSemaphor = zeroEvenOddUsingSemaphor;
	}
	
	@Override
	public void run() {
		zeroEvenOddUsingSemaphor.even();
	}
	
}
