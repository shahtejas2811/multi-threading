package com.threadproblem;


class SharedObject1{
	
	private  boolean isRunning = true;

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
}

class Thread1 implements Runnable{

	private SharedObject1 sharedObject1;
	
	public Thread1(SharedObject1 sharedObject1) {
		this.sharedObject1 = sharedObject1;
	}
	
	@Override
	public void run() {
		
		while(sharedObject1.isRunning()){
			System.out.println("Task is in progress");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Thread is stop");
	}
	
}

class Thread2 implements Runnable{

	private SharedObject1 sharedObject1;
	
	public Thread2(SharedObject1 sharedObject1) {
		this.sharedObject1 = sharedObject1;
	}
	
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Stopping Thread");
		sharedObject1.setRunning(false);
	}
	
}
public class VisibilityProblem {

	public static void main(String[] args) {
		
		SharedObject1 sharedObject1 = new SharedObject1();
		
		Thread t1 = new Thread(new Thread1(sharedObject1));
		Thread t2 = new Thread(new Thread2(sharedObject1));
		
		t1.start();
		t2.start();
		
		
		

	}

}
