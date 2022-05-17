package com.UPT.uptalk_spring.service.impl;

import com.UPT.uptalk_spring.model.ExistRoom;
import com.UPT.uptalk_spring.model.MessageMetaData;
import com.UPT.uptalk_spring.model.User;
import com.UPT.uptalk_spring.model.UserInfo;
import com.UPT.uptalk_spring.repository.IExistRoomRepository;
import com.UPT.uptalk_spring.service.IRoomService;
import com.UPT.uptalk_spring.service.ISessionPoolService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.*;

/**
 * @Title: SessionPoolServiceImpl
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/13
 */
@Slf4j
@NoArgsConstructor
public class SessionPoolServiceImpl implements ISessionPoolService {
    private IExistRoomRepository IExistRoomRepository;
    private static Map<Session, User> userPool = new HashMap<Session, User>();
    private static Map<Integer, RoomServiceImpl> roomPool = new HashMap<Integer, RoomServiceImpl>();
    private static Queue<Session> queuePool = new LinkedList<Session>();

    /**
     * 加入連線池
     *
     * @param session  要加入連線池的session
     * @param userInfo 要移出連線池的User
     */
    @Override
    public void addUser(Session session, UserInfo userInfo) {
        User user = new User();
        user.setUserInfo(userInfo);
        userPool.put(session, user);
        log.info(session.getId() + ":連線加入，目前總連線數:" + userPool.size());
    }

    /**
     * 移出連線池
     *
     * @param session 要移出連線池的session
     */
    @Override
    public void removeUser(Session session) {
        userPool.remove(session);
        log.info(session.getId() + ":連線退出，目前總連線數:" + userPool.size());
    }

    /**
     * 創建房間
     *
     * @param session 要建立房間的session
     * @return 建立出的房間Id
     */
    public Integer createRoom(Session session) {
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl();
        User user = userPool.get(session);
        int roomId = generateRoomId();

        roomServiceImpl.setId(roomId);
        roomServiceImpl.addUser(session, user);

        roomPool.put(roomId, roomServiceImpl);

        log.info(session.getId() + "建立房間:" + roomId + " 目前總房間數:" + roomPool.size());
        return roomId;
    }

    /**
     * 加入房間
     *
     * @param session 要加入房間的session
     * @param roomId  要加入的房間Id
     */
    public void joinRoom(Session session, int roomId) {
        User user = userPool.get(session);
        RoomServiceImpl roomServiceImpl = roomPool.get(roomId);
        roomServiceImpl.addUser(session, user);
    }

    /**
     * 離開房間
     *
     * @param session 要離開房間的session
     */
    public void quitRoom(Session session) {
        User user = userPool.get(session);
        IRoomService roomService = user.getRoomService();
        roomService.removeUser(session, user);
        if (roomService.getUserPool().size() == 0) {
            roomPool.remove(roomService.getId());
        }
    }

    /**
     * 送出訊息
     *
     * @param session         要送出訊息的session
     * @param messageMetaData 要送出的訊息物件
     */
    public void sendMessage(Session session, MessageMetaData messageMetaData) {
        User user = userPool.get(session);
        IRoomService roomService = user.getRoomService();
        roomService.sendMessage(session, messageMetaData);
    }

    /**
     * 加入列隊
     *
     * @param session 要加入列隊的session
     */
    public void joinQueue(Session session) {
        queuePool.add(session);
        if (queuePool.size() >= 2) {
            int roomId = this.createRoom(queuePool.poll());
            this.joinRoom(queuePool.poll(), roomId);
        }
        log.info(session.getId() + "加入列隊 目前列隊人數:" + queuePool.size());
    }

    /**
     * 離開列隊
     *
     * @param session 要離開列隊的session
     */
    public void leaveQueue(Session session) {
        queuePool.remove(session);
        log.info(session.getId() + "離開列隊 目前列隊人數:" + queuePool.size());
    }

    /**
     * 產生不重複的房間Id
     *
     * @return 不重複的房間Id
     */
    public int generateRoomId() {
        Optional<ExistRoom> existRoom;
        do {
            existRoom = this.IExistRoomRepository.findExistRoomByRoomNumber((int) (Math.random() * 100000));
        } while (existRoom.isPresent());
        int roomId = existRoom.get().getRoomNumber();
        log.info("產生房號:" + roomId);
        return roomId;
    }
}
