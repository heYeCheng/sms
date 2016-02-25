package com.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new RedisClient().show();

		Jedis redis = RedisUtil.getJedis();
		System.out.println(redis.get("newname"));
		redis.set("newname", "中12");
//		List<String> bb = redis.blpop(2, "list");
		List<String> bb = redis.blpop(0, "list");
		
//		if (bb != null) {
//			for (String b : bb) {
//				System.out.println(b);
//			}
//		}

		RedisUtil.returnResource(redis);
		System.out.println(bb.get(1));
	}
}
