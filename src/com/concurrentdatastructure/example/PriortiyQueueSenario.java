package com.concurrentdatastructure.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * user defined object must be comparable if you put in priority queue other
 * wise you will get exception Exception in thread "main"
 * java.lang.ClassCastException: com.concurrentdatastructure.example.Message
 * cannot be cast to java.lang.Comparable
 * 
 * @author Tejas
 *
 */
class Message implements Comparable<Message> {

	private int id;
	private int version;

	public Message(int id, int version) {
		super();
		this.id = id;
		this.version = version;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", version=" + version + "]";
	}

	@Override
	public int compareTo(Message message) {
		return this.version - message.version;
	}

}

/**
 * A scenario where I have message object with attributes id and version. I need
 * to insert a message objects with latest version into the database. My system
 * has these messages on a queue for processing. Suggest an implementation for
 * the same.
 * 
 * @author Tejas
 *
 */
public class PriortiyQueueSenario {

	public static void main(String[] args) {
		BlockingQueue<Message> priorityQueue = new PriorityBlockingQueue<Message>(4);
		
		priorityQueue.add(new Message(1, 10));
		priorityQueue.add(new Message(2, 20));
		priorityQueue.add(new Message(3, 1));
		priorityQueue.add(new Message(4, 5));

		System.out.println("Poll elements from Queue.");

		while (!priorityQueue.isEmpty()) {
			// poll method Retrieves and removes the head of this queue,or returns null if
			// this queue is empty.
			System.out.println(" " + priorityQueue.poll());
		}

	}
}
