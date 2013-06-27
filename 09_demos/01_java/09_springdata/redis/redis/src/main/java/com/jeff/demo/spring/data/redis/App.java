package com.jeff.demo.spring.data.redis;

import org.springframework.util.StringUtils;


/**
 * Hello world!
 * 
 */
public class App {
	private static byte[] buf = new byte[8192];
	
	private final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

    private final static byte[] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    };

    private final static byte[] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };

    private final static byte[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };
	
	public static void main(String[] args) {
		
//		writeIntCrLf(75536);
//		long time = 1000000000L;
//		long value1,q, r;
//		int old = Integer.MAX_VALUE;
//		
//		long t1 = System.currentTimeMillis();
//		for(long j=0;j<time;j++){
//			q = old / 100; // q=755 value=75536
////            r = old - ((q << 6) + (q << 5) + (q << 2));// q左移6位+q左移5位+q左移2位
//		}
//		
//		long t2 = System.currentTimeMillis();
//		for(long t=0;t<time;t++){
//			value1 = old % 100;
//		}
//		long t3 = System.currentTimeMillis();
//		
//		System.out.println("t2-t1="+(t2-t1));
//		System.out.println("t3-t2="+(t3-t2));
		
		Test t = Test.A;
		System.out.println(t.getName());
	}
	
	public static void writeIntCrLf(int value) {
        int size = 0;
        // 使用sizeTable判断数字的长度
        while (value > sizeTable[size])
            size++;

        size++;

        int q, r;
        int count = 0;
        int charPos = count + size;// 从后向前设置

        while (value >= 65536) {// 16位的长度是65536
            q = value / 100; // q=755 value=75536
            r = value - ((q << 6) + (q << 5) + (q << 2));// q左移6位+q左移5位+q左移2位
            value = q;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        for (; ;) {
            q = (value * 52429) >>> (16 + 3);
            r = value - ((q << 3) + (q << 1));
            buf[--charPos] = digits[r];
            value = q;
            if (value == 0) break;
        }
        count += size;

    }
	
	
}

enum Test{
	A,B;
	
	public String getName(){
		return this.name();
	}
}


