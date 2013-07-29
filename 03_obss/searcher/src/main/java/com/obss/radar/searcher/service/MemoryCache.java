package com.obss.radar.searcher.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryCache extends TimerTask {
	private Map<String, Object> cache = new HashMap<String, Object>();

	private Timer timer = new Timer(); // 时钟

	/**
	 * 构造函数，默认缓存60秒
	 */
	public MemoryCache() {
		timer.schedule(this, 60 * 1000, 60 * 1000);
	}

	/**
	 * 构造函数
	 * 
	 * @param cacheSeconds 缓存时间
	 */
	public MemoryCache(int cacheSeconds) {
		timer.schedule(this, cacheSeconds * 1000, cacheSeconds * 1000);
	}

	@Override
	public void run() {
		cache = new HashMap<String, Object>();
	}

	public Object get(String key) {
		return cache.get(key);
	}
	
	public boolean contains(String key) {
		return cache.containsKey(key);
	}

	public void put(String key, Object value) {
		cache.put(key, value);
	}
}
