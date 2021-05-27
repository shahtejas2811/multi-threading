package com.mythreadpool;

import java.util.concurrent.BlockingQueue;

public class WorkerThread implements Runnable {
	private BlockingQueue<Runnable> taskQueue;

	public WorkerThread(BlockingQueue<Runnable> taskQueue) {
		this.taskQueue = taskQueue;

	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {

			Runnable task;
			try {
				task = taskQueue.take();
				task.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public synchronized void stopWorker() {
		Thread.currentThread().interrupt();
	}

}
