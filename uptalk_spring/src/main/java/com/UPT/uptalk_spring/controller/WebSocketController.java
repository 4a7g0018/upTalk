package com.UPT.uptalk_spring.controller;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.MessageType;
import com.UPT.uptalk_spring.model.UserInfo;
import com.UPT.uptalk_spring.service.ISessionPoolService;
import com.UPT.uptalk_spring.service.impl.SessionPoolServiceImpl;
import com.UPT.uptalk_spring.utils.websocket.MessageMetaDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static com.UPT.uptalk_spring.model.MessageType.*;

/**
 * @Title: WebSocketController
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/13
 */
@Slf4j
@Controller
@ServerEndpoint("/api/websocket")
public class WebSocketController {

    private static ISessionPoolService sessionPoolService;

    @Autowired
    public WebSocketController(ISessionPoolService sessionPoolService) {
        this.sessionPoolService = sessionPoolService;
    }

    public WebSocketController() {
    }

    /**
     * 接收message的方法
     *
     * @param message 客戶端傳送過來的訊息
     * @param session 客戶端session
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {

        MessageMetaData messageMetaData = MessageMetaDataUtil.decodeMessageMetaData(message);
        if(messageMetaData==null){
            log.warn(session.getId() +":未定義命令類型");
            this.sendCommand(session,UndefinedType,"fail");
        }else{
            log.info(session.getId() + ":接收到訊息:" + messageMetaData.toString());
            MessageType messageMetaDataType = messageMetaData.getType();
            String messageMetaDataTextMessage = messageMetaData.getMessage();
            switch (messageMetaDataType) {
                case CreateRoom:
                    Integer roomId = sessionPoolService.createRoom(session);
                    if (roomId != null) {
                        this.sendCommand(session, CreateRoom, String.valueOf(roomId));
                    } else {
                        log.warn(session.getId()+":CreateRoom失敗");
                        this.sendCommand(session, CreateRoom, "fail");
                    }
                    break;
                case JoinRoom:
                    int roomId2 = Integer.parseInt(messageMetaDataTextMessage);
                    if (sessionPoolService.joinRoom(session, roomId2)) {
                        this.sendCommand(session, JoinRoom, "success");
                    } else {
                        log.warn(session.getId()+":JoinRoom失敗");
                        this.sendCommand(session, JoinRoom, "fail");
                    }
                    break;
                case QuitRoom:
                    if (sessionPoolService.quitRoom(session)) {
                        this.sendCommand(session, QuitRoom, "success");
                    } else {
                        log.warn(session.getId()+":QuitRoom失敗");
                        this.sendCommand(session, QuitRoom, "fail");
                    }
                    break;
                case JoinQueue:
                    if (sessionPoolService.joinQueue(session)) {
                        this.sendCommand(session, JoinQueue, "success");
                    } else {
                        log.warn(session.getId()+":JoinQueue失敗");
                        this.sendCommand(session, JoinQueue, "fail");
                    }
                    break;
                case LeaveQueue:
                    if (sessionPoolService.leaveQueue(session)) {
                        this.sendCommand(session, LeaveQueue, "success");
                    } else {
                        log.warn(session.getId()+":LeaveQueue失敗");
                        this.sendCommand(session, LeaveQueue, "fail");
                    }
                    break;
                case Text:
                    if (sessionPoolService.sendMessage(session, messageMetaData)) {
                        this.sendCommand(session, Text, "success");
                    } else {
                        log.warn(session.getId()+":Text失敗");
                        this.sendCommand(session, Text, "fail");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 連線建立成功呼叫的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info(session.getId() + ":連線開啟");
        UserInfo userInfo = new UserInfo("test", "test", "test", true, null);
        sessionPoolService.addUser(session, userInfo);
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
