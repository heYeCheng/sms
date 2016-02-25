package com.msg;

import entity.sms;

public class sendMsg implements Runnable {
	public sms tempObject;
	public Object smsInstance;
	public controllerMsg cmsg;

	public sendMsg(String serial, int port, String method, controllerMsg cmsg) {
		tempObject = basicMsg.getSmsInstance(serial, port, method);
		smsInstance = tempObject.getSmsInstance();
		this.cmsg = cmsg;
	}

	public void send() {
		// 死循环，不断运行。
		try {
			while (true) {
				if (tempObject.getStatus().equals("exit")) {
					System.out.println("exit send");
					break;
				} else {
					synchronized (cmsg.sendObj) {

						Thread.sleep(11500);

						System.out.println("send msg : " + Thread.currentThread().getName() + " : "
								+ tempObject.getSerial() + " : " + tempObject.getStatus());

						cmsg.sendObj.wait();
					}
				}
				System.out.println("send msg still work" + " : " + tempObject.getStatus());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(" send was started");
		send();
		System.out.println(" stop send msg : " + Thread.currentThread().getName() + " : " + tempObject.getSerial());
	}

}
