package com.jeff.demo.spring.data.redis;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

public class RedisTest {
	private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
	private static BeanFactory beanFactory;
	private static RedisTemplate<String, String> redisTemplate;
	private static final String TEST_LIST = "test_list";

	public static void init() throws Exception {
		try {
			beanFactory = new GenericXmlApplicationContext(
			        "classpath*:applicationContext-redis.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void testRedisListTemp() {
		redisTemplate = (RedisTemplate<String, String>) beanFactory.getBean("redisTemplate");
		final ListOperations<String, String> listOperations = redisTemplate.opsForList();

		// 清空
//		listOperations.trim("test_list_1", 0, 0);
//		listOperations.leftPop("test_list_1");
		System.out.println(listOperations.size("test_list_1"));

//		listOperations.trim(TEST_LIST, 0, 0);
//		listOperations.leftPop(TEST_LIST);
//		Assert.assertNull(listOperations.leftPop(TEST_LIST));
	}

	@SuppressWarnings("unchecked")
	public static void testRedisListBasic() {
		redisTemplate = (RedisTemplate<String, String>) beanFactory.getBean("redisTemplate");
		final ListOperations<String, String> listOperations = redisTemplate.opsForList();

		// 清空
		listOperations.trim(TEST_LIST, 0, 0);
		listOperations.leftPop(TEST_LIST);

		// 基本测试
		listOperations.rightPush(TEST_LIST, "value1");
		Assert.assertEquals("value1", listOperations.leftPop(TEST_LIST));
		Assert.assertEquals(0L, listOperations.size(TEST_LIST).longValue());

		listOperations.rightPush(TEST_LIST, "value1");
		listOperations.rightPush(TEST_LIST, "value1");
		listOperations.trim(TEST_LIST, 0, 0);
		listOperations.leftPop(TEST_LIST);
		Assert.assertEquals(0L, listOperations.size(TEST_LIST).longValue());
	}

	@SuppressWarnings("unchecked")
	public static void testGet() {
		redisTemplate = (RedisTemplate<String, String>) beanFactory.getBean("redisTemplate");
		ListOperations<String, String> listOperations = redisTemplate.opsForList();
		System.out.println(listOperations.rightPop(TEST_LIST));
	}

	@SuppressWarnings("unchecked")
	public static void testRedisList() {
		redisTemplate = (RedisTemplate<String, String>) beanFactory.getBean("redisTemplate");
		final ListOperations<String, String> listOperations = redisTemplate.opsForList();

		// 清空
		listOperations.trim(TEST_LIST, 0, 0);
		listOperations.leftPop(TEST_LIST);

		// 性能测试
		int threadCount = 50;
		final int loopCount = 200;
		ExecutorService executors = Executors.newFixedThreadPool(threadCount);
		for (int x = 0; x < threadCount; x++) {
			executors.submit(new Runnable() {
				@Override
				public void run() {
					long cost = 0;
					int count = loopCount;

					logger.info("ThreadID=" + Thread.currentThread().getId());
					Date startTime = new Date();
					for (int i = 0; i < count; i++) {
						long start = System.currentTimeMillis();

						try {
							listOperations
							        .rightPush(TEST_LIST,
							                "value1adfadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasfasdfasdfasdfasdfasdfasdfasfdaadfadfsdaf");
						} catch (Exception e) {
							logger.info("ThreadId=" + Thread.currentThread().getId() + "异常。异常信息："
							        + e.getMessage());
							return;
						}

						cost = cost + (System.currentTimeMillis() - start);
					}

					logger.info("ThreadId=" + Thread.currentThread().getId() + ";total cost="
					        + cost + "ms");
					logger.info("ThreadId=" + Thread.currentThread().getId() + ";average cost="
					        + cost / count + "ms");
					Object[] args = {"redisTest", Thread.currentThread().getId(), new Date(), cost,
					        cost / count };
					logger.info("taskName={};threadId={};startTime={};total={};average={}", args);
				}
			});
		}

		try {
			executors.shutdown();
			while (!executors.isTerminated()) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			long size = listOperations.size(TEST_LIST);
			logger.info("list size = {}", size);
			logger.info("Finish");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void testGetKeys() {
		redisTemplate = (RedisTemplate<String, String>) beanFactory.getBean("redisTemplate");
		System.out.println(redisTemplate.keys("*"));
	}

	public static void main(String... args) throws Exception {
		RedisTest.init();
		RedisTest.testGetKeys();
	}
	
	@Test
	public void testJedis(){
		Jedis jedis = new Jedis("10.10.160.66",6379);
		jedis.auth("open");
		Set<String> keys = jedis.keys("*");
		for(String key : keys){
			System.out.println(key);
		}
	}

}
