package com.jeff.demo.network.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastUtils {
	public static final String MULTICAST_IP = "230.3.3.3";
	public static int MULTICAST_PORT = 6789;

	public static void sendMessage(String msg, String ip, int port) throws IOException {
		byte[] data = msg.getBytes();
		MulticastSocket socket = new MulticastSocket(port);
		socket.setLoopbackMode(true);
		InetAddress address = InetAddress.getByName(ip);
		socket.joinGroup(address);
		DatagramPacket dp = new DatagramPacket(data, data.length, address, port);
		socket.send(dp);
	}

	public static void getMessage(String ip, int port) throws IOException {
		InetAddress group = InetAddress.getByName(ip);
		MulticastSocket s = new MulticastSocket(port);
		byte[] arb = new byte[1024];
		s.joinGroup(group);
		while (true) {
			DatagramPacket datagramPacket = new DatagramPacket(arb, arb.length);
			s.receive(datagramPacket);
			System.out.println(arb.length);
			System.out.println(new String(arb));
		}
	}

}
