package com.jeff.demo.baseAlgo.recursion;

import java.util.ArrayList;
import java.util.List;


public class RecursionDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 递归计算power
//		System.out.println(power(2, 10));
		
		// 递归解决背包问题
//		int[] src = {2, 4, 5, 7, 10 };
//		select(18, src, 0, new int[src.length]);
		
		// 递归打印出组合问题
		List<String> items = new ArrayList<String>();
		items.add("A");
		items.add("B");
		items.add("C");
		List<String> result = permutation(2,items);
		System.out.println(result);
	}

	/**
	 * 使用递归实现x^y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static int power(int x, int y) {
		if (y == 0) {
			return 1;
		} else if (y == 1) {
			return x;
		} else {
			int t = power(x * x, y / 2);
			if (y % 2 == 0) {
				return t;
			} else {
				return t * x;
			}
		}
	}

	/**
	 * @param total 总数
	 * @param src 源数据数组
	 * @param offset 源数组的起始位置
	 * @param bag 背包，装选中的数据
	 */
	private static void select(int total, int[] src, int offset, int[] bag) {
		if (total == 0) { // 背包要求增加的重量为0,则直接打印背包 ,即递归结束条件
			print(bag);
			return;
		}
		// 从最大值开始找，找到第一个小于total的数字
		while (offset < src.length && total < src[offset]){
			offset++;
		}
			
		// 一直没有找到则退出
		if (offset >= src.length){
			return; 
		}
		
		// 将问题化简为在剩下的源数据中，两个找到重量的问题
		select(total, src, offset + 1, bag.clone()); // 背包中含有offset位置的
		select(total - src[offset], src, offset + 1, put(bag, src[offset]));// 背包中不含有offset位置的
	}

	private static int[] put(int[] bag, int n) { // 将数字放入背包中
		int pos = -1;
		while (bag[++pos] > 0)
			; // 找到背包中第一个数字为0的位置
		bag[pos] = n;
		return bag;
	}

	private static void print(int[] bag) { // 打印背包中的数字
		System.out.print("bag: ");
		for (int n : bag) {
			if (n == 0) break;
			System.out.print(n + " ");
		}
		System.out.println();
	}
	
	/**
	 * 使用递归实现组合算法
	 * 1、使用数组存储待选元素，是因为数组复制的优点，从数组中移除元素比较麻烦
	 * 2、使用list的好处是移除元素方便，但是复制不方便
	 * @param n
	 * @param items
	 * @return
	 */
	private static List<String> permutation(int n, List<String> items){
		List<String> result = new ArrayList<String>();
		if(n == items.size()){
			StringBuffer sb = new StringBuffer();
			for(String item : items){
				sb.append(item);
			}
			result.add(sb.toString());
		}
		if(n == 1){
			return items;
		}else{
			for(String item:items){
				List<String> items2 = copyList(items);
				items2.remove(item);
				List<String> anotherR = permutation(n-1,items2);
				for(String temp : anotherR){
					StringBuffer sb = new StringBuffer();
					sb.append(item);
					sb.append(temp);
					result.add(sb.toString());
				}
				
			}
			ThreadGroup t;
			return result;
		}
	}
	
	private static List<String> copyList(List<String> src){
		List<String> list = new ArrayList<String>();
		for(String str : src){
			list.add(str);
		}
		return list;
	}

}
