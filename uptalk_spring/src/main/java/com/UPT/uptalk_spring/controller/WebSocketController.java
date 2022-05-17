package com.UPT.uptalk_spring.controller;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.MessageType;
import com.UPT.uptalk_spring.model.UserInfo;
import com.UPT.uptalk_spring.service.ISessionPoolService;
import com.UPT.uptalk_spring.utils.websocket.MessageMetaDataUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static com.UPT.uptalk_spring.model.MessageType.CreateRoom;

/**
 * @Title: WebSocketController
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/13
 */
@Slf4j
@NoArgsConstructor
@ServerEndpoint("/websocket/{roomId}")
public class WebSocketController {

    ISessionPoolService sessionPoolService;

    /**
     * 接收message的方法
     *
     * @param message 客戶端傳送過來的訊息
     * @param session 客戶端session
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        MessageMetaData messageMetaData = MessageMetaDataUtil.decodeMessageMetaData(message);
        log.info(session.getId() + ":接收到訊息:" + messageMetaData);
        MessageType messageMetaDataType = messageMetaData.getType();
        String messageMetaDataTextMessage = messageMetaData.getMessage();
        switch (messageMetaDataType) {
            case CreateRoom:
                int roomId = sessionPoolService.createRoom(session);
                this.sendCommand(session, CreateRoom, String.valueOf(roomId));
                break;
            case JoinRoom:
                int roomId2 = Integer.parseInt(messageMetaDataTextMessage);
                sessionPoolService.joinRoom(session, roomId2);
                break;
            case QuitRoom:
                sessionPoolService.quitRoom(session);
                break;
            case JoinQueue:
                sessionPoolService.joinQueue(session);
                break;
            case LeaveQueue:
                sessionPoolService.leaveQueue(session);
                break;
            case Text:
                sessionPoolService.sendMessage(session, messageMetaData);
                break;
        }
    }

    /**
     * 連線建立成功呼叫的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info(session.getId() + ":連線開啟");
        sessionPoolService.addUser(session, new UserInfo());
    }

    /**
     * 連線關閉呼叫的方法
     */
    @OnClose
    public void onClose(Session session) {
        log.info(session.getId() + ":連線關閉");
        sessionPoolService.removeUser(session);
    }

    /**
     * 連線發生錯誤呼叫的方法
     */
    @OnError
    public void onError(Session session, Throwable t) {
        log.error(session.getId() + ":連線發生錯誤");
        sessionPoolService.removeUser(session);
    }

    /**
     * 發送命令的方法
     *
     * @param session 目標session
     * @param type    命令類型
     * @param command 命令內容
     */
    public void sendCommand(Session session, MessageType type, String command) {
        MessageMetaData messageMetaData = new MessageMetaData(type, command);
        String returnMessage = MessageMetaDataUtil.encodeMessageMetaData(messageMetaData);
        try {
            session.getBasicRemote().sendText(returnMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(session.getId() + "送出命令:" + messageMetaData);
    }
}
