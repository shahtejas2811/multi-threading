package com.threadLocal;

import java.util.HashMap;

class MyThreadLocal {

	private HashMap<Thread, Object> threadLocalMap = new HashMap<>();

	public void set(Object o) {
		threadLocalMap.put(Thread.currentThread(), o);
	}

	public Object get() {
		return threadLocalMap.get(Thread.currentThread());
	}

}

class UserContext {

	private String userid;
	private String username;

	public UserContext(String userid, String username) {
		super();
		this.userid = userid;
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserContext [userid=" + userid + ", username=" + username + "]";
	}

}

public class MyThreadLocalApp {
	
	
	
	public static void main(String[] args) {
		
		MyThreadLocal myThreadLocal = new MyThreadLocal();
		
		UserContext user1 = new UserContext("1","Tejas");
		UserContext user2 = new UserContext("1","Roshan");
		
		Task task1 = new Task(myThreadLocal,user1);
		Task task2 = new Task(myThreadLocal,user2);
		
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	
		
		
		
	}
}

class Task implements Runnable{
	
	private MyThreadLocal myThreadLocal;
	private UserContext userContext;
	
	public Task(MyThreadLocal myThreadLocal, UserContext userContext) {
		this.myThreadLocal = myThreadLocal;
		this.userContext = userContext;
	}

	@Override
	public void run() {
		
		myThreadLocal.set(userContext);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(myThreadLocal.get());
	}
}

