package com.UPT.uptalk_spring.service;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.UserInfo;

import javax.websocket.Session;

public interface ISessionPoolService {
    public void addUser(Session session, UserInfo userInfo);

    public void removeUser(Session session);

    public Integer createRoom(Session session);

    public void joinRoom(Session session, int roomId);

    public void quitRoom(Session session);

    public void sendMessage(Session session, MessageMetaData messageMetaData);

    public void joinQueue(Session session);

    public void leaveQueue(Session session);
}
