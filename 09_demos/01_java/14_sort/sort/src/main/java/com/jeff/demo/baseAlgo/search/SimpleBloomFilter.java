package com.jeff.demo.baseAlgo.search;

import java.util.BitSet;
/**
 * 布隆过滤器的简单实现：
 * 1、适应场景：大规模数据去重，比如反垃圾邮件
 * 2、seeds中元素越多，则发生碰撞的可能性就越小
 * 3、布隆过滤器的误差推导：http://www.cnblogs.com/allensun/archive/2011/02/16/1956532.html
 * @author jianfeihit
 *
 */
public class SimpleBloomFilter {
	private static final int DEFAULT_SIZE = 2 << 24;
	private static final int[] seeds = new int[] {5, 7, 11, 13, 31, 37, 61 };
	private BitSet bits = new BitSet(DEFAULT_SIZE);
	private SimpleHash[] func = new SimpleHash[seeds.length];

	public SimpleBloomFilter() {
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}
	}

	public void add(String value) {
		for (SimpleHash f : func) {
			bits.set(f.hash(value), true);
		}
	}

	public boolean contains(String value) {
		if (value == null) {
			return false;
		}
		boolean ret = true;
		for (SimpleHash f : func) {
			ret = ret && bits.get(f.hash(value));
		}
		return ret;
	}

	public static class SimpleHash {
		private int cap;
		private int seed;

		public SimpleHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}
		
		/**
		 * 选定一个好的hash函数是关键
		 * @param value
		 * @return
		 */
		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}

	public static void main(String[] args) {
		String value = "stone2083@yahoo.cn";
		SimpleBloomFilter filter = new SimpleBloomFilter();
		System.out.println(filter.contains(value));
		filter.add(value);
		System.out.println(filter.contains(value));
	}
}
