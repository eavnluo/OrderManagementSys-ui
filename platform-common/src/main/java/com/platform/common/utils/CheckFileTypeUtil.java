package com.platform.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @date  创建时间 2016年7月17日 上午10:47:26
 * <p>
 * 类描述：获取和判断文件头信息
 * |--文件头是位于文件开头的一段承担一定任务的数据，一般都在开头的部分。
 * |--头文件作为一种包含功能函数、数据接口声明的载体文件，用于保存程序的声明(declaration),而定义文件用于保存程序的实现(implementation)。
 * |--为了解决在用户上传文件的时候在服务器端判断文件类型的问题，故用获取文件头的方式，直接读取文件的前几个字节，来判断上传文件是否符合格式。
 */
public class CheckFileTypeUtil {
	/**
	 * 缓存文件头信息-文件头信息
	 */
	public static final HashMap<String, String> FILE_TYPES = new HashMap<String, String>();

	static {
		// images
		FILE_TYPES.put("FFD8FF", "jpg");
		FILE_TYPES.put("89504E47", "png");
		FILE_TYPES.put("47494638", "gif");
		FILE_TYPES.put("49492A00", "tif");
		FILE_TYPES.put("424D", "bmp");

		// CAD
//		FILE_TYPES.put("41433130", "dwg");
//		FILE_TYPES.put("38425053", "psd");
		// 日记本
//		FILE_TYPES.put("7B5C727466", "rtf");
//		FILE_TYPES.put("3C3F786D6C", "xml");
//		FILE_TYPES.put("68746D6C3E", "html");
		// 邮件
//		FILE_TYPES.put("44656C69766572792D646174653A", "eml");
		FILE_TYPES.put("D0CF11E0", "doc");
		//excel2003版本文件
		FILE_TYPES.put("D0CF11E0", "xls");
		FILE_TYPES.put("5374616E64617264204A", "mdb");
		FILE_TYPES.put("252150532D41646F6265", "ps");
		FILE_TYPES.put("255044462D312E", "pdf");
		FILE_TYPES.put("504B0304", "docx");
		//excel2007以上版本文件
		FILE_TYPES.put("504B0304", "xlsx");
		FILE_TYPES.put("52617221", "rar");
		FILE_TYPES.put("57415645", "wav");
		FILE_TYPES.put("41564920", "avi");
		FILE_TYPES.put("2E524D46", "rm");
		FILE_TYPES.put("000001BA", "mpg");
		FILE_TYPES.put("000001B3", "mpg");
		FILE_TYPES.put("6D6F6F76", "mov");
		FILE_TYPES.put("3026B2758E66CF11", "asf");
		FILE_TYPES.put("4D546864", "mid");
		FILE_TYPES.put("1F8B08", "gz");

		FILE_TYPES.put("504B030414000600", "pptx");
		FILE_TYPES.put("D0CF11E0A1B11AE1", "ppt");
	}

	/**
	 * 方法描述：根据文件路径获取文件头信息
	 *
	 * @param in 文件输入流
	 * @return 文件头信息
	 */
	public static String getFileType(InputStream in) {
		return FILE_TYPES.get(getFileHeader(in));
	}

	/**
	 * 方法描述：根据文件路径获取文件头信息
	 *
	 * @param is 文件输入流
	 * @return 文件头信息
	 */
	public static String getFileHeader(InputStream is) {
		String value = null;
		try {
			byte[] b = new byte[4];
			/*
			 * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length
			 * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
			 * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
			 */
			is.read(b, 0, b.length);
			value = bytesToHexString(b);
		} catch (Exception e) {
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return value;
	}

	/**
	 * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
	 *
	 * @param src 要读取文件头信息的文件的byte数组
	 * @return 文件头信息
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
		return builder.toString();
	}
}