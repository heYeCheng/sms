package com;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.msg.basicMsg;
import com.msg.controllerMsg;
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
		controllerMsg cmsg = new controllerMsg(serial);
		Thread temp = new Thread(cmsg, "cont" + serial);
		temp.start();
		
		temp = new Thread(new sendMsg(serial, port, method, cmsg), "st" + serial);
		temp.start();

		temp = new Thread(new recvMsg(serial, port, method, cmsg), "st" + serial);
		temp.start();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getMsg gm = new getMsg();

		for (int i = 0; i < 1; i++) {
			gm.init("com" + i, 115200, "wav");
		}

//		for (int i = 0; i < 3; i++) {
//			Thread obj = gm.sendMap.get("com" + i);
//			obj.start();
//
//			obj = gm.recvMap.get("com" + i);
//			obj.start();
//		}



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
