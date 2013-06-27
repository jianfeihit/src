package com.jeff.demo.baseAlgo.sort;

/**
 * Hello world!
 * 
 */
public class SortDemo {
	private static final int[] OLD = {5,3,9,8,2,7,6,1,4};
	
	public static void main(String[] args) {
		bubbleSort();
	}
	
	/**
	 * 冒泡排序
	 * 优化点：
	 * 1、外层循环是从尾到头开始循环
	 * 2、内存循环之道out即可，因为out之后的都已经排好序了
	 * 3、如果其中又一遍没有任何变化，即可终止排序，因为此时已经排好序了
	 * 4、时间复杂度为O(N)，可以计算比较的次数
	 */
	private static void bubbleSort(){
		System.out.println("冒泡排序：");
		int[] newData = OLD.clone();
		int count = 0;
		boolean ischanged = false;
		for(int out = newData.length-1;out>1;out --){
			count ++;
			ischanged = false;
			for(int t=0;t<out;t++){
				int a = newData[t];
				int b = newData[t+1];
				if(a>b){
					int c = newData[t];
					newData[t] = newData[t+1];
					newData[t+1] = c;
					ischanged = true;
				}
			}
			if(!ischanged){
				break;
			}
			System.out.println("第"+count+"次排序结果："+printArray(newData));
		}
	} 
	
	
	private static String printArray(int[] data){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int t=0;t<data.length;t++){
			if(t>0){
				sb.append(",");
			}
			sb.append(data[t]+"");
		}
		sb.append("]");
		return sb.toString();
	}
}
