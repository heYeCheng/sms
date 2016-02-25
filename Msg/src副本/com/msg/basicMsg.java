package com.msg;

import java.util.HashMap;
import java.util.Map;

import entity.sms;

public class basicMsg {

	private static Map<String, sms> smsMap = new HashMap<String, sms>();
	
	public static sms getSmsInstance(String serial) {
		// 返回发短信 Gateway 的唯一实例
		if (smsMap.containsKey(serial)) {
			System.out.println("有 了");

			return smsMap.get(serial);
		} else {
			sms tempObject = null;
			return tempObject;
		}
	}
	
	public static sms getSmsInstance(String serial, int port, String method) {
		// 返回发短信 Gateway 的唯一实例
		if (smsMap.containsKey(serial)) {
			System.out.println("有 了");

			return smsMap.get(serial);
		} else {
			sms tempObject = new sms(serial, port, method);
			smsMap.put(serial, tempObject);

			return tempObject;
		}
	}

	public static void removeSms(String serial) {
		// 移除不需要的端口
		if (smsMap.containsKey(serial)) {
			smsMap.remove(serial);
		}
	}
}
