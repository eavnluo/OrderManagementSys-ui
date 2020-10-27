package com.platform.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 * @author Mr.panbb
 * @date 2020-03-26
 */
@Slf4j
public class FileDownloadUtils {

    /**
     * 下载文件
     * @param filePath 文件路径
     * @param fileName 下载文件显示名称
     * @param response
     */
    public static void download(String filePath, String fileName, HttpServletResponse response) {
        BufferedInputStream br = null;
        OutputStream out = null;
        try {
            String path = URLDecoder.decode(filePath, "UTF-8");
            File f = new File(path);
            if (!f.exists()) {
                response.sendError(404, "文件没找到!");
                return;
            }
            br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len;
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

}
