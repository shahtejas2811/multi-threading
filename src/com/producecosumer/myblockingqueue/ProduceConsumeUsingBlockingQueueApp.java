package com.producecosumer.myblockingqueue;

/**
 * Producer & Consumer using Blocking queue
 * 
 * @apiNote BlockingQueue.put() : Inserts the specified element at the tail of
 *          this queue, waiting for space to become available if the queue is
 *          full.
 * @apiNote BlockingQueue.take() : Retrieves and removes the head of this queue,
 *          waiting if necessary until an element becomes available.
 * 
 * @author Tejas
 *
 */
public class ProduceConsumeUsingBlockingQueueApp {

	public static void main(String[] args) {

		//BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		//MyBlockingQueueUsingLock<Integer> queue = new MyBlockingQueueUsingLock<>(10);
		//MyBlockingQueueUsingWaitNotify<Integer> queue = new MyBlockingQueueUsingWaitNotify<>(10);
		MyBlockingQueueUsingSemaphore<Integer> queue = new MyBlockingQueueUsingSemaphore<>(10);
		// Producer
		Runnable producer = () -> {

			int i = 0;
			while (true) {
				try {
					queue.put(i); // wait if queue is full
					i++;
					System.out.println(
							"Putting value in queue -> " + i + " by Thread  " + Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		new Thread(producer, "Producer-1").start();
		new Thread(producer, "Producer-2").start();

		// consumer

		Runnable consumer = () -> {

			while (true) {
				try {
					// wait if queue is empty
					System.out.println("Consuming value from queue -> " + queue.take() + " by Thread  "
							+ Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		new Thread(consumer, "consumer-1").start();
		new Thread(consumer, "consumer-2").start();
	}

}
