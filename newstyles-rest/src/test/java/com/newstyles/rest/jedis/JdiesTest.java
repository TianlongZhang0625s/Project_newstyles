package com.newstyles.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.tools.internal.xjc.model.SymbolSpace;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JdiesTest {

	public void testJdiesSingle(){
		//创建一个jedis对象
		Jedis jedis = new Jedis("192.168.59.129",6379);
		//调用jedis对象的方法，方法名称和redis一样
		jedis.set("key1","jedis test" );
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jiedis
		jedis.close();
	}
	
	public void testJdiespool(){
		//创建一个jedis对象
		JedisPool jedisPool = new JedisPool("192.168.59.129",6379);
		//调用jedis对象的方法，方法名称和redis一样
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jiedis
		jedis.close();
		jedisPool.close();
	}
	
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.59.129", 7001));
		nodes.add(new HostAndPort("192.168.59.129", 7002));
		nodes.add(new HostAndPort("192.168.59.129", 7003));
		nodes.add(new HostAndPort("192.168.59.129", 7004));
		nodes.add(new HostAndPort("192.168.59.129", 7005));
		nodes.add(new HostAndPort("192.168.59.129", 7006));
		
		JedisCluster cluster = new JedisCluster(nodes);
		
		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		
		cluster.close();
	}
	
//	@Test
	public void testSpingsingle(){
		ApplicationContext applicaitonContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicaitonContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	@Test
	public void testSpringCluster(){
		ApplicationContext applicaitonContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster = (JedisCluster) applicaitonContext.getBean("redisClient");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
		//测试成功，返回值为1000
	}


}
