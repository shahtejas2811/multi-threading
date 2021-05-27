package com.mythreadpool;

public class MyThreadPoolApp {

	public static void main(String[] args) {
		
		MyThreadPool myThreadPool = new  MyThreadPool(3,10);
		
		for (int i =0 ; i <10 ;i++){
			int i1 = i;
			myThreadPool.execute(() -> {
				
				System.out.println(Thread.currentThread().getName() + " : Task :"+i1);
			});
		}
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	myThreadPool.execute(() -> {
				
				System.out.println(Thread.currentThread().getName() + " : Task :"+11);
			});
		myThreadPool.shutdown();
		
	}
}
