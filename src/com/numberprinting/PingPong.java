package com.numberprinting;
/**
 * Print ping pong using two threads.
 * Thread 1 => ping
 * Thread 2 => pong
 * 
 * output : output should be in sequences 
 * ping pong ping pong ping pong 
 * @author Tejas
 *
 */

public class PingPong {
	
	public static void main(String[] args) {
		
		printPingPong printPingPong = new printPingPong();
		
		Thread t1 = new Thread(new p1(printPingPong,5));
		Thread t2 = new Thread(new p2(printPingPong,5));
		
		t1.start();
		t2.start();
		
	}

}

class printPingPong{
	
	private Object mutex = new Object();
	private volatile boolean  isPong = false;
	
	public void ping() {
		synchronized (mutex) {
			while (isPong) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("ping");
			isPong = true;
			mutex.notify();
		}

	}
	
	public void pong() {
		synchronized (mutex) {
			while (!isPong) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("pong");
			isPong = false;
			mutex.notify();
		}

	}
}

class p1 implements Runnable {

	private printPingPong printPingPong;
	private int max;

	public p1(printPingPong printPingPong, int max) {
		this.printPingPong = printPingPong;
		this.max = max;
	}

	@Override
	public void run() {
		for (int i = 0; i < max; i++)
			printPingPong.ping();

	}

}

class p2 implements Runnable {

	private printPingPong printPingPong;
	private int max;

	public p2(printPingPong printPingPong, int max) {
		this.printPingPong = printPingPong;
		this.max = max;
	}

	@Override
	public void run() {
		for (int i = 0; i < max; i++)
			printPingPong.pong();
	}

}