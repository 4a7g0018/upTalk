package com.UPT.uptalk_spring.utils.mail;

public interface IMailUtils {

    /**
     * 發送驗證信
     *
     * @param to
     * @param authenticationCode
     */
    void sendAuthentication(String to, String authenticationCode);
}
