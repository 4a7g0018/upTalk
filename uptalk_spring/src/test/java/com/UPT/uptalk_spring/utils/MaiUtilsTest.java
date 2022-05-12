package com.UPT.uptalk_spring.utils;

import com.UPT.uptalk_spring.utils.mail.IMailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

/**
 * 郵件發送測試
 *
 * @Title: MaiUtilsTest
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/12
 */

@SpringBootTest
public class MaiUtilsTest {

    @Autowired
    private IMailUtils mailUtils;

    @Test
    public void TestSendAuthentication(){
        String to = "yan19991203@gmail.com";
        String authenticationCode = "mail utils test";

        try {
            this.mailUtils.sendAuthentication(to,authenticationCode);
        }catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void TestSendHtmlAuthentication(){
        String to = "yan19991203@gmail.com";
        String authenticationCode = "abcdefghijklmnopqrstuvwxyz";

        try{
            this.mailUtils.sendHtmlAuthentication(to,authenticationCode);
        }catch (RuntimeException ex){
            ex.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
