package com.msg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redis.RedisUtil;

import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import redis.clients.jedis.Jedis;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis redis = RedisUtil.getJedis();
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("type", "exit");
//		map.put("type", "send");
		map.put("type", "recv");
//		map.put("serial", 0);
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
		
		redis.lpush("msgcom0", jsonObject.toString());
		
//		List signalList = redis.brpop(4, "signal");
//		String obj = (String) signalList.get(1);
//		System.out.println(obj);
//		JSONObject jb = JSONObject.fromObject(obj);
//		System.out.println(jb.get("type"));
		
	}

}
