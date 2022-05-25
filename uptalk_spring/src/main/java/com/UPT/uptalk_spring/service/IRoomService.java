package com.UPT.uptalk_spring.service;

import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.User;

import javax.websocket.Session;
import java.util.Map;

public interface IRoomService {
    public void addUser(Session session, User user);
    public void removeUser(Session session, User user);
    public void sendMessage(Session session, MessageMetaData messageMetaData);
    public Map<Session, User> getUserPool();
    public int getId();
}
