package com;

//public class TestThread extends Thread {
//	private static int threadCount = 0;
//	private int threadNum = ++threadCount;
//	private int i = 5;
//
//	public void run() {
//		while (true) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				System.out.println("Interrupted");
//			}
//			System.out.println("Thread " + threadNum + " = " + i);
//			if (--i == 0)
//				return;
//		}
//	}
//
//	public static void main(String[] args) {
//		for (int i = 0; i < 5; i++)
//			new TestThread().start();
//	}
//}

public class TestThread implements Runnable {
	public synchronized void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + " : " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
	}

	public static void main(String[] args) {
		TestThread testThread = new TestThread();
		for (int i = 0; i < 5; i++)
			// new Thread(testThread, "t" + i).start();（1）
			new Thread(new TestThread(), "t" + i).start();
	}
}