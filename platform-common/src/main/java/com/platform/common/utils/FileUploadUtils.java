package com.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author Tepu
 */
@Slf4j
public class FileUploadUtils {


	/**
	 * 将文件保存到本地
	 *
	 * @param file 上传的文件
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return 文件存储路径
	 */
	public static String doUpload(MultipartFile file, String filePath, String fileName) {
		// 目录不存在创建目录
		File fp = new File(filePath);
		if(!fp.exists()){
			fp.mkdirs();
		}
		// 文件不存在创建文件
		File f = new File(filePath + fileName);
		if (!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				log.error("", e);
			}
		}
		FileOutputStream fos = null;
		InputStream fis = null;
		try {
			fis = file.getInputStream();
			fos = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (fos != null) {
				// 关闭文件流
				try {
					fos.flush();
					fos.close();
				} catch (Exception e) {
					log.error("", e);
				}
			}
		}
		return filePath + fileName;
	}

	/**
	 * 获取新文件名称
	 *
	 * @param oldFileName 旧文件名称
	 * @return 返回新文件名称
	 */
	public static String createNewFileName(String oldFileName) {
		// 获取文件格式
		String pattern = oldFileName.substring(oldFileName.lastIndexOf("."));
		// 获取原始文件名
		String oldFile = oldFileName.substring(0, oldFileName.lastIndexOf("."));
		// 获取当前时间
		String nowDate = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS);
		return new StringBuilder().append(oldFile).append("_").append(nowDate).append(pattern).toString();
	}
}
