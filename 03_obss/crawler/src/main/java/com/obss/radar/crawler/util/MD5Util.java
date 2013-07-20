package com.obss.radar.crawler.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.obss.radar.crawler.po.ProgramException;

public class MD5Util {

	private MD5Util() {
	}

	public static String byte2hex(byte[] b) {
		if (b == null) {
			return "";
		}
		StringBuffer hs = new StringBuffer();
		String stmp = null;
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append("0");
			}
			hs.append(stmp);
		}
		return hs.toString();
	}

	public static String MD5(String src) {
		if (src == null) {
			return "";
		}
		byte[] result = null;
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			result = alg.digest(src.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new ProgramException(e);
		} catch (UnsupportedEncodingException e) {
			throw new ProgramException(e);
        }
		return byte2hex(result);
	}

	public static String MD5(byte[] src) {
		if (src == null) {
			return "";
		}
		byte[] result = null;
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			result = alg.digest(src);
		} catch (NoSuchAlgorithmException e) {
			throw new ProgramException(e);
		}
		return byte2hex(result);
	}
	
	public static byte[] MD5Bytes(byte[] src) {
		byte[] result = null;
		if (src != null) {
			try {
				MessageDigest alg = MessageDigest.getInstance("MD5");
				result = alg.digest(src);
			} catch (NoSuchAlgorithmException e) {
				throw new ProgramException(e);
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		System.out.println(MD5("2221083"+"20111101"+"pagehis"));
	}
}
