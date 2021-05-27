package com.mythreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
/**
 * 1. newFixedThreadPool : 
 *      - Fix number of thread 
 *      - Internally use blocking queue to store task
 *      
 * 2. newCachedThreadPool()
 * 		- No Fix number of thread and there is no blocking queue to hold task.
 * 		- internally use syncronous queue to hold one task
 * 		- Thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available. 
 * 		- If no existing thread is available, a new thread will be created and added to the pool.
 * 		- Threads that have not been used for sixty seconds are terminated and removed from the cache. Thus, a pool that remains idle for long enough will not consume any resources.
 * 
 * 3. newScheduledThreadPool(5) from ScheduledExecutorService
 * 		- Creates a thread pool that can schedule task to run after a given delay
 * 		- internally use delay queue 
 * 		- Fix number of thread 
 * 
 * @author Tejas
 *
 */
public class InBuiltThreadPool {

	public static void main(String[] args) {

		ExecutorService es = Executors.newFixedThreadPool(5);
		
		@SuppressWarnings("unused")
		ExecutorService es1 = Executors.newCachedThreadPool();
		
		@SuppressWarnings("unused")
		ScheduledExecutorService es2 = Executors.newScheduledThreadPool(5);
		
		// es2.schedule(command, delay, unit)
		
		
		for (int i = 0; i < 50; i++) {
			// Execute method will take runnable and return void . this is from Executor Interface
			es.execute(new CPUIntensiveTask());
			// submit method will take callable and return future. This is from ExecutorService Inteface  
			//es.submit(new callable())
		}
	}

}

class CPUIntensiveTask implements Runnable {

	@Override
	public void run() {
		System.out.println("CPU Intensive Task.."+Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
