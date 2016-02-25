package com;

public class MyThreadPrinter2 {
	private String flag[] = { "true" };
	private String bb = "true";

	class NotifyThread extends Thread {
		public NotifyThread(String name) {
			super(name);
		}

		public void run() {
			try {
				Thread.sleep(3000);// 推迟3秒钟通知
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			synchronized (flag[0]) {
				System.out.println("dsf");
				bb = "false";
				flag[0].notify();
			}
		}
	};

	class WaitThread extends Thread {
		public WaitThread(String name) {
			super(name);
		}

		public void run() {
		
			synchronized (flag[0]) {
				System.out.println(bb);
				while (bb != "false") {
					System.out.println(getName() + " begin waiting!");
					long waitTime = System.currentTimeMillis();
					try {
						flag[0].wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					waitTime = System.currentTimeMillis() - waitTime;
					System.out.println("wait time :" + waitTime);
				}
				System.out.println(getName() + " end waiting!");
			}
			
			
			synchronized (flag[1]) {
				System.out.println(bb);
				while (bb != "false") {
					System.out.println(getName() + " begin waiting!");
					long waitTime = System.currentTimeMillis();
					try {
						flag[1].wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					waitTime = System.currentTimeMillis() - waitTime;
					System.out.println("wait time :" + waitTime);
				}
				System.out.println(getName() + " end waiting!");
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Main Thread Run!");
		MyThreadPrinter2 test = new MyThreadPrinter2();
		NotifyThread notifyThread = test.new NotifyThread("notify01");
		WaitThread waitThread01 = test.new WaitThread("waiter01");
		WaitThread waitThread02 = test.new WaitThread("waiter02");
		WaitThread waitThread03 = test.new WaitThread("waiter03");
		notifyThread.start();
		waitThread01.start();
		waitThread02.start();
		waitThread03.start();
	}

}