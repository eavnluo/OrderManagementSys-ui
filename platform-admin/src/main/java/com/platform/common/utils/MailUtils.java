package com.platform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件发送工具类
 * @author Mr.panbb
 * @date 2020-04-17
 */
@Component
public class MailUtils {
    private Logger logger = LoggerFactory.getLogger(MailUtils.class);
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送邮件账户
     */
    private final static String MAIL_FROM = "panbb_00@163.com";

    /**
     * 发送普通邮件
     * @param subject 主题
     * @param text 内容
     * @param toMail 发送邮箱
     */
    public void sendMail(String subject, String text, String toMail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_FROM);
            message.setTo(toMail);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
