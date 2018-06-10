package com.avenger.declare.util;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

public class IdGeneratorUtil {
	
	/** 随机数对象*/
	private static Random random=new Random();
	
	/** ip地址尾数*/
	private static String ip = null;
	
	static {
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			String temp = "";
			char[] ch = address.toCharArray();
			for (char c : ch) {
			    if (c != '.') {
				temp += c;
			    }
			}
			ip = new DecimalFormat("00000000").format(Long.valueOf(temp));
			ip = ip.substring(0, ip.length() - 3);
		} catch (Exception e) {
			e.printStackTrace();
			ip = new DecimalFormat("00000000").format(random.nextInt(1000000000));
		}
	}
	
	/**
	 * 获取30位的id
	 * @return
	 */
	public static String getId(){
		return new Date().getTime()+ip+new DecimalFormat("00000000").format(random.nextInt(10000000));
	}
}
