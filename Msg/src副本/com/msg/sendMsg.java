package com.msg;

import entity.sms;

public class sendMsg implements Runnable {
	public sms tempObject;
	public Object smsInstance;

	public sendMsg(String serial, int port, String method) {

		tempObject = basicMsg.getSmsInstance(serial, port, method);
		smsInstance = tempObject.getSmsInstance();
	}

	public void send() {
		// 死循环，不断运行。
		try {
			while (true) {
				if (tempObject.getStatus().equals("exit")) {
					System.out.println("exit send");
					break;
				} else {
					synchronized (tempObject) {
						while (tempObject.getStatus().equals("send")) {
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("send msg : " + Thread.currentThread().getName() + " : "
									+ tempObject.getSerial() + " : " + tempObject.getStatus());
						}

						System.out.println(" recv 改变状态ing");
						tempObject.notifyAll();
						tempObject.wait();
					}
				}
				System.out.println("send msg still work" + " : " + tempObject.getStatus());
			}
			synchronized (tempObject) {
				tempObject.notifyAll();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("exi颠三倒四t");
		System.out.println("stop send msg : " + Thread.currentThread().getName() + " : " + tempObject.getSerial());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(" send was started");
		send();

	}

}
