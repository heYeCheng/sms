package com.msg;

import java.util.List;

import com.redis.RedisUtil;

import entity.sms;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class controllerMsg implements Runnable {
	protected Object sendObj = "send";
	protected Object recvObj = "recv";
	protected String serial;

	public controllerMsg(String serial) {
		this.serial = serial;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Jedis redis = RedisUtil.getJedis();

		while (true) {
			List signalList = redis.blpop(0, "msg" + serial);
			System.out.println(signalList);
			if (signalList == null) {
				System.out.println("  nerver used");
				break;
			} else {
				JSONObject msgObj = JSONObject.fromObject((String) signalList.get(1));
				String type = msgObj.getString("type");
				sms tempObject = basicMsg.getSmsInstance(serial);

				if (type.equals("exit")) {
					System.out.println(" 改为  关闭  短信状态");
					tempObject.setStatus("exit");
					sendObj.notify();
					recvObj.notify();
					break;
				} else if (type.equals("send")) {
					synchronized (sendObj) {
						System.out.println(" 改为  发送  短信状态");
						tempObject.setStatus("send");
						sendObj.notify();
					}
				} else if (type.equals("recv")) {
					synchronized (recvObj) {
						System.out.println(" 改为  接收  短信状态");
						tempObject.setStatus("recv");
						recvObj.notify();
					}
				}
			}

		}
	}

}
