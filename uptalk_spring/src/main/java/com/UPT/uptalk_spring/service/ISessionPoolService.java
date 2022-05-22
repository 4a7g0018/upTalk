package com.UPT.uptalk_spring.service;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.UserInfo;

import javax.websocket.Session;

public interface ISessionPoolService {
    public void addUser(Session session, UserInfo userInfo);

    public void removeUser(Session session);

    public Integer createRoom(Session session);

    public Boolean joinRoom(Session session, int roomId);

    public Boolean quitRoom(Session session);

    public Boolean sendMessage(Session session, MessageMetaData messageMetaData);

    public Boolean joinQueue(Session session);

    public Boolean leaveQueue(Session session);
}
