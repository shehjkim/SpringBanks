package com.company.common;

public class RandTest {
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		String str = Long.toString(time);
		System.out.println(str.substring(str.length() - 9));
	}
}
