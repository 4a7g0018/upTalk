package com.UPT.uptalk_spring.service.impl;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.User;
import com.UPT.uptalk_spring.service.IRoomService;
import com.UPT.uptalk_spring.utils.websocket.MessageMetaDataUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: Room
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/17
 */

@Data
@Slf4j
public class RoomServiceImpl implements IRoomService {
    private int id;
    private Map<Session, User> userPool = new HashMap<Session, User>();

    /**
     * 加入房間
     *
     * @param session 要加入房間的session
     * @param user    要加入房間的user
     */
    public void addUser(Session session, User user) {
        this.userPool.put(session, user);
        user.setRoomService(this);
        log.info(session.getId() + ":加入房間" + this.id + "，目前房間連線數:" + userPool.size());
    }

    /**
     * 離開房間
     *
     * @param session 要離開房間的session
     * @param user    要離開房間的user
     */
    public void removeUser(Session session, User user) {
        this.userPool.remove(session);
        user.setRoomService(null);
        log.info(session.getId() + ":退出房間" + this.id + "，目前房間連線數:" + userPool.size());
    }

    /**
     * 送出訊息
     *
     * @param session         要送出訊息的session
     * @param messageMetaData 要送出的訊息物件
     */
    public void sendMessage(Session session, MessageMetaData messageMetaData) {
        String returnMessage = MessageMetaDataUtil.encodeMessageMetaData(messageMetaData);
        for (Session s : this.userPool.keySet()) {
            try {
                if (session != s) {
                    s.getBasicRemote().sendText(session.getId() + ":" + returnMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info(session.getId() + ":送出訊息到:" + this.id + "房間 內容:"+returnMessage);
    }
}
