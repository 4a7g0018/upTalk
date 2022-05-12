package com.UPT.uptalk_spring.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * mail 發送工具
 *
 * @Title: MailUtils
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/12
 */

@Component
public class MailUtils implements IMailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${mail.subject}")
    private String subject;

    /**
     * 發送驗證信
     *
     * @param to                 收件人
     * @param authenticationCode 驗證碼
     */
    public void sendAuthentication(String to, String authenticationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(this.fromEmail);
        mailMessage.setSubject(this.subject);
        mailMessage.setTo(to);

        String mailContent = String.format("您的驗證碼 : %s", authenticationCode);
        mailMessage.setText(mailContent);

        this.mailSender.send(mailMessage);
    }
}
