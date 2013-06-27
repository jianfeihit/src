package com.jeff.demo.baseAlgo.sort;

import java.util.BitSet;
/**
 * BitMap排序：
 * 1、需要的存储空间：2倍的原数组空间+至少原数组中最大数长度的比特队列
 * 2、时间复杂度：O(N)
 * 3、适应于：待排序的元素相对比较密集且不存在重复元素
 * 4、缺点：若排列1,10000,1000000，这三个数就不利于，因为需要循环遍历bitArray
 * 5、扩展：对于纯数字类型的数据进行排重（统计只出现过一次的需要进行再扩展一个bit位，0表示未出现；1表示出现1次；2表示出现大于1次）
 * @author jianfeihit
 *
 */
public class BitMapDemo {
	private static int[] oldData = new int[]{5,2,6,1,8,7,10};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BitSet set = new BitSet(oldData.length);
		for(int value : oldData){
			set.set(value, true);
		}
		
		int[] newData = new int[oldData.length];
		int length = set.size();
		int index = 0;
		for(int i=0;i<length;i++){
			if(set.get(i)){
				newData[index]=i;
				index++;
			}
		}
		
		printArray(oldData);
		printArray(newData);
	}
	
	private static void printArray(int[] values){
		StringBuffer sb = new StringBuffer();
		for(int t : values){
			sb.append(t).append(",");
		}
		System.out.println(sb.toString());
	}

}
