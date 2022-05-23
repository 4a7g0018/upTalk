package com.UPT.uptalk_spring.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    @Value("${static.IP}")
    private String staticIP;

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


    /**
     * 以html格式發送驗證信
     *
     * @param to                 收件人
     * @param authenticationCode 驗證碼
     */
    @Override
    public void sendHtmlAuthentication(String to, String authenticationCode) {
        MimeMessage message = this.mailSender.createMimeMessage();
        try {
            StringBuffer sendHtml = new StringBuffer();
            sendHtml.append("<html lang=\"en\">")
                        .append("<head>")
                            .append("<meta charset=\"UTF-8\">")
                            .append("<title>Title</title>")
                            .append("<style>")
                                .append("* {box-sizing: border-box;}")
                                .append(".container {width: 500px;height: 500px;border: 2px solid black;border-radius: 30px;margin: auto}")
                                .append(".container .title {font-size: 35px;width: 100%;height: 80px;line-height: 100px;text-align: center;font-weight: bold}")
                                .append(".container .message {font-size: 20px;width: 100%;height: 250px;line-height:50px;margin: 10px}")
                                .append(".container .authenticationBtn {margin: auto;display: block;width: 200px;height: 50px;border-radius: 20px;text-align: center;line-height: 50px;font-weight: bold;font-size: 26px;text-decoration: none;background: #45a9ff;color: white;}")
                                .append(".container .authenticationBtn:hover {background: white;color: #45a9ff;border: 2px solid #45a9ff;}")
                            .append("</style>")
                        .append("</head>")
                        .append("<body>")
                            .append("<div class=\"container\">")
                                .append("<div class=\"title\">歡迎您的加入</div>")
                                .append("<div class=\"message\">")
                                    .append("<div>親愛的up Talk會員您好:</div>")
                                    .append("<div>這封認證信是由up Talk發出，快點擊下方完成驗證吧!</div>")
                                .append("</div>")
                                .append("<a class=\"authenticationBtn\" href=\"").append(this.staticIP).append("/mail/authenticationCode?").append(authenticationCode).append("\">完成驗證</a>")
                            .append("</div>")
                        .append("</body>")
                    .append("</html>");


            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.fromEmail);
            helper.setSubject(this.subject);
            helper.setTo(to);
            helper.setText(sendHtml.toString(), true);
            this.mailSender.send(message);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }
}
