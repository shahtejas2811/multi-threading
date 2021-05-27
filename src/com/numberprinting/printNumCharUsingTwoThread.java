package com.numberprinting;
/**
 * Thread 1 printing 1,2,3.n
 * Thread 2 printing A,B,C..
 * 
 * Output should be in sequence 1A2B3C
 * @author Tejas
 *
 */
public class printNumCharUsingTwoThread {

	public static void main(String[] args) {
		
		NumberCharPrint ncp = new NumberCharPrint();
		 
		Thread t1 = new Thread(new NumberPrintTask(ncp,5));
		Thread t2 = new Thread(new CharPrintTask(ncp, 5));
		
		t1.start();
		t2.start();
		
	}
}

class NumberCharPrint{
	
	private int n =1;
	private char c = 'A';
	private Object mutex  = new Object();
	private volatile boolean isCharPrinting = false;

	public void printNum() {

		synchronized (mutex) {
			try {
				while (isCharPrinting) {

					mutex.wait();

				}
				System.out.print("" + n);
				n++;
				isCharPrinting = true;
				mutex.notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printChar(){
		synchronized (mutex) {
			try {
				while (!isCharPrinting) {
					mutex.wait();
				}
				System.out.print("" + c);
				c++;
				isCharPrinting = false;
				mutex.notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

class NumberPrintTask implements Runnable{
	
	private NumberCharPrint ncp;
	private int max;
	
	public  NumberPrintTask( NumberCharPrint ncp ,int max) {
		this.max = max;
		this.ncp = ncp;
	}

	@Override
	public void run() {
		for (int i = 0; i < max; i++) {
			ncp.printNum();
		}
		
	}
	
}

class CharPrintTask implements Runnable{

	private NumberCharPrint ncp;
	private int max;
	
	public  CharPrintTask(NumberCharPrint ncp ,int max) {
		this.max = max;
		this.ncp = ncp;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < max; i++) {
			ncp.printChar();
		}
		
	}
	
}