package com.UPT.uptalk_spring.utils.websocket;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title: UnPackMetaData
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/14
 */

public class MessageMetaDataUtil {
    /**
     * 序列化
     *
     * @param messageMetaData 被序列化的物件
     * @return Json內容
     */
    public static String encodeMessageMetaData(MessageMetaData messageMetaData) {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(messageMetaData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 反序列化
     *
     * @param message 被反序列化的Json內容
     * @return 反序列化出的物件
     */
    public static MessageMetaData decodeMessageMetaData(String message) {
        ObjectMapper mapper = new ObjectMapper();
        MessageMetaData result = new MessageMetaData();
        try {
            result = mapper.readValue(message, MessageMetaData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
