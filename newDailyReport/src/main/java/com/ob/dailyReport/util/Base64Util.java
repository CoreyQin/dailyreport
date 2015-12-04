package com.ob.dailyReport.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	/**
	 * @param bytes
	 * @return
	 */
	public static String decode(final String string) {
		if (string == null || string.equals("")) {
			return string;
		}
		try {
			return new String(Base64.decodeBase64(string.getBytes("UTF-8")), "UTF-8");
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
		if (string == null || string.equals("")) {
			return string;
		}
		try {
			return new String(Base64.encodeBase64(string.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
