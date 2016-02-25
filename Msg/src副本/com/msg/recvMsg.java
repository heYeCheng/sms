package com.msg;

import entity.sms;

public class recvMsg implements Runnable {
	public sms tempObject;
	public Object smsInstance;

	public recvMsg(String serial, int port, String method) {

		tempObject = basicMsg.getSmsInstance(serial, port, method);
		smsInstance = tempObject.getSmsInstance();
	}

	public void recv() {
		try {
			while (true) {
				if (tempObject.getStatus().equals("exit")) {
					System.out.println("exit recv");
					break;
				} else {
					synchronized (tempObject) {
						while (tempObject.getStatus().equals("recv")) {
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("recv msg : " + Thread.currentThread().getName());
						}
						System.out.println(" send 改变状态ing");
						tempObject.notifyAll();
						tempObject.wait();
					}
				}
				System.out.println("recv msg still work");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		tempObject.notifyAll();
		System.out.println("stop send msg : " + Thread.currentThread().getName() + " : " + tempObject.getSerial());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(" recv was started");
		recv();

	}

}
