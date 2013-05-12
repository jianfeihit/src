package com.jeff.demo.network.multicast;

import java.io.IOException;


/**
 * Hello world!
 * 
 */
public class MulticastDemo {
	public static void main(String[] args) throws Exception {
		(new Receiver()).start();
		(new Receiver()).start();
		(new Receiver()).start();
		MulticastUtils.sendMessage("你好，世界。",MulticastUtils.MULTICAST_IP, MulticastUtils.MULTICAST_PORT);
	}
	
	
}

class Receiver extends Thread{
	@Override
	public void run(){
		try {
            MulticastUtils.getMessage(MulticastUtils.MULTICAST_IP, MulticastUtils.MULTICAST_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
};
