package com.ob.dailyReport.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
	/**
	 * @param bytes
	 * @return
	 */
	public static String decode(final String string) {
		try {
			return new String(Base64.getDecoder().decode(string.getBytes("UTF-8")), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(final String string) {
		try {
			return new String(Base64.getEncoder().encode(string.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
