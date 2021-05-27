package com.mythreadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {

	private BlockingQueue<Runnable> taskQueue;
	private List<Runnable> workerThreadList;
	
	public MyThreadPool(int noOfThread,int NoOfTask){
		
		
		this.workerThreadList = new ArrayList<Runnable>(noOfThread);
		this.taskQueue = new ArrayBlockingQueue<>(NoOfTask);
		
		for (int i =1; i <  noOfThread ; i++){
			workerThreadList.add(new WorkerThread(taskQueue));
		}
		
		for (Runnable runnable : workerThreadList) {
			 new Thread(runnable).start();
		}
		
		
	}
	public void execute(Runnable task){
		taskQueue.offer(task);
	}
	
	public void shutdown(){
		for (Runnable workerThread : workerThreadList) {
			((WorkerThread) workerThread).stopWorker();
		}
	}


}
