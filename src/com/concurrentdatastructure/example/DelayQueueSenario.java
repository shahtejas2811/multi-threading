package com.concurrentdatastructure.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueSenario {

	public static void main(String... args) {

		BlockingQueue<DeleyedObject> delayQueue = new DelayQueue<>();

		delayQueue.add(new DeleyedObject("Tejas", 2000));
		delayQueue.add(new DeleyedObject("Bhavik", 3000));
		delayQueue.add(new DeleyedObject("Ankit", 1000));
		delayQueue.add(new DeleyedObject("Viral", 5000));
		delayQueue.add(new DeleyedObject("Vishal", 200));

		try {
			for (int i = 0; i < 5; i++) {
				System.out.println(delayQueue.take());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

class DeleyedObject implements Delayed {

	private String element;
	private long expriryTime;

	public DeleyedObject(String element, long expriryTime) {
		super();
		this.element = element;
		this.expriryTime = expriryTime;
	}

	@Override
	public int compareTo(Delayed o) {
		if (this.expriryTime < ((DeleyedObject) o).expriryTime) {
			return -1;
		} else if (this.expriryTime > ((DeleyedObject) o).expriryTime) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(expriryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return "DeleyedObject [element=" + element + ", expriryTime=" + expriryTime + "]";
	}

}
