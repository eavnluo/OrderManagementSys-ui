package com.platform.e6yun.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * @author Mr.panbb
 * @date 2019-12-14
 */
public class MD5Util {
	public static String str2MD5(String str) {
		String ret = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				md.update(str.getBytes());
			}

			byte[] b = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}

			ret = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
