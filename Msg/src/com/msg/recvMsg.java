package com.msg;

import entity.sms;

public class recvMsg implements Runnable {
	public sms tempObject;
	public Object smsInstance;
	public controllerMsg cmsg;

	public recvMsg(String serial, int port, String method, controllerMsg cmsg) {
		tempObject = basicMsg.getSmsInstance(serial, port, method);
		smsInstance = tempObject.getSmsInstance();
		this.cmsg = cmsg;
	}

	public void recv() {
		try {
			while (true) {
				if (tempObject.getStatus().equals("exit")) {
					System.out.println("exit recv");
					break;
				} else {
					synchronized (cmsg.recvObj) {

						Thread.sleep(1500);

						System.out.println("recv msg : " + Thread.currentThread().getName() + " : "
								+ tempObject.getSerial() + " : " + tempObject.getStatus());
						
						cmsg.recvObj.wait();
					}
				}
				System.out.println("recv msg still work" + " : " + tempObject.getStatus());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// tempObject.notifyAll();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(" recv was started");
		recv();
		System.out.println("stop send msg : " + Thread.currentThread().getName() + " : " + tempObject.getSerial());
	}

}
