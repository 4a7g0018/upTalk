package com.UPT.uptalk_spring.utils.mail;

import javax.mail.MessagingException;

public interface IMailUtils {

    /**
     * 發送驗證信
     *
     * @param to                 收件人
     * @param authenticationCode 驗證碼
     */
    void sendAuthentication(String to, String authenticationCode);

    /**
     * 以html格式發送驗證信
     *
     * @param to                 收件人
     * @param authenticationCode 驗證碼
     */
    void sendHtmlAuthentication(String to , String authenticationCode) throws MessagingException;
}
