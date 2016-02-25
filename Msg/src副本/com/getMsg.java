package com;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.msg.basicMsg;
import com.msg.recvMsg;
import com.msg.sendMsg;
import com.redis.RedisUtil;
import entity.sms;

import redis.clients.jedis.Jedis;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class getMsg {
	public Map<String, Thread> sendMap = new HashMap<String, Thread>();
	public Map<String, Thread> recvMap = new HashMap<String, Thread>(); //
	public Map<String, String> usedMap = new HashMap<String, String>(); // Gateway
																		// 使用情况

	public getMsg() {
	}

	public void init(String serial, int port, String method) {
		// 调用 创建端口届发短信 的 接口
		Thread temp = new Thread(new sendMsg(serial, port, method), "st" + serial);
		sendMap.put(serial, temp);

		temp = new Thread(new recvMsg(serial, port, method), "st" + serial);
		recvMap.put(serial, temp);
		usedMap.put(serial, "1");

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getMsg gm = new getMsg();

		for (int i = 0; i < 3; i++) {
			gm.init("com" + i, 115200, "wav");
		}

		for (int i = 0; i < 3; i++) {
			Thread obj = gm.sendMap.get("com" + i);
			obj.start();

			obj = gm.recvMap.get("com" + i);
			obj.start();
		}

		Jedis redis = RedisUtil.getJedis();

		while (true) {
			List signalList = redis.blpop(0, "signal");
			System.out.println(signalList);
			if (signalList == null) {
				System.out.println("  nerver used");
				break;
			} else {
				JSONObject msgObj = JSONObject.fromObject((String) signalList.get(1));
				String type = msgObj.getString("type");
				String serial = "com" + msgObj.getString("serial");
				sms tempObject = basicMsg.getSmsInstance(serial);

				if (type.equals("exit")) {
					System.out.println(" 改为  关闭  短信状态");
					tempObject.setStatus("exit");
					break;
				} else if (type.equals("send")) {
					System.out.println(" 改为  发送  短信状态");
					tempObject.setStatus("send");
				} else if (type.equals("recv")) {
					System.out.println(" 改为  接收  短信状态");
					tempObject.setStatus("recv");
				}
			}
		}


		// Iterator tempSendEntry = gm.sendMap.entrySet().iterator();
		// while (tempSendEntry.hasNext()) {
		// Map.Entry<String, Thread> tempEntry = (Entry<String, Thread>)
		// tempSendEntry.next();
		// try {
		// Thread obj = tempEntry.getValue();
		// obj.start();
		// obj.wait();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// System.out.println(e);
		// e.printStackTrace();
		// }
		// }
	}

}
