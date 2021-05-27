package com.concurrentpackage;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchEx {

	public static void main(String[] args) {
		
		CountDownLatch countDownLatch = new CountDownLatch(3);
		
		Thread t1 = new Thread(new CachingService(countDownLatch, 100));
		Thread t2 = new Thread(new configSetupService(countDownLatch, 200));
		Thread t3 = new Thread(new DataBaseService(countDownLatch, 300));
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All services are ready..");
		
		while(true){
			
		}
		// proceede with main service
		
	}

}

class CachingService implements Runnable{
	
	private CountDownLatch countDownLatch;
	private long delay ;

	public CachingService(CountDownLatch countDownLatch,long delay) {
		this.countDownLatch = countDownLatch;
		this.delay = delay;
	}

	@Override
	public void run() {
		System.out.println("Caching Service is start.");
		try {
			Thread.sleep(delay);
			System.out.println("Caching Service is ready.");
			countDownLatch.countDown();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class configSetupService implements Runnable{
	
	private CountDownLatch countDownLatch;
	private long delay;
	
	public configSetupService(CountDownLatch countDownLatch,long delay) {
		this.countDownLatch = countDownLatch;
		this.delay = delay;
	}

	@Override
	public void run() {
		System.out.println("Config Setup Service is start.");
		try {
			Thread.sleep(delay);
			System.out.println("Config Setup Service is ready.");
			countDownLatch.countDown();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}

class DataBaseService implements Runnable{
	
	private CountDownLatch countDownLatch;
	private long delay;
	
	public DataBaseService(CountDownLatch countDownLatch,long delay) {
		this.countDownLatch = countDownLatch;
		this.delay = delay;
	}

	@Override
	public void run() {
		System.out.println("DataBase Service is start.");
		try {
			Thread.sleep(delay);
			System.out.println("DataBase Service is ready.");
			countDownLatch.countDown();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}