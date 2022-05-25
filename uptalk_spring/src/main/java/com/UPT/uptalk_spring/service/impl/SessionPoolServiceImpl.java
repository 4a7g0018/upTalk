package com.UPT.uptalk_spring.service.impl;

import com.UPT.uptalk_spring.model.*;
import com.UPT.uptalk_spring.repository.IExistRoomRepository;
import com.UPT.uptalk_spring.service.IRoomService;
import com.UPT.uptalk_spring.service.ISessionPoolService;
import com.UPT.uptalk_spring.utils.websocket.MessageMetaDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

import static com.UPT.uptalk_spring.model.MessageType.CreateRoom;
import static com.UPT.uptalk_spring.model.MessageType.JoinRoom;

/**
 * @Title: SessionPoolServiceImpl
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/13
 */
@Slf4j
@Service
public class SessionPoolServiceImpl implements ISessionPoolService {

    @Autowired
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
        leaveQueue(session);
        quitRoom(session);
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
        try {
            User user = userPool.get(session);
            int roomId = generateRoomId();
            RoomServiceImpl roomServiceImpl = new RoomServiceImpl();
            roomServiceImpl.setId(roomId);
            roomServiceImpl.addUser(session, user);

            roomPool.put(roomId, roomServiceImpl);

            log.info(session.getId() + ":建立房間:" + roomId + " 目前總房間數:" + roomPool.size());
            return roomId;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加入房間
     *
     * @param session 要加入房間的session
     * @param roomId  要加入的房間Id
     * @return 是否成功
     */
    public Boolean joinRoom(Session session, int roomId) {
        try {
            User user = userPool.get(session);
            RoomServiceImpl roomServiceImpl = roomPool.get(roomId);
            roomServiceImpl.addUser(session, user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 離開房間
     *
     * @param session 要離開房間的session
     * @return 是否成功
     */
    public Boolean quitRoom(Session session) {
        try {
            User user = userPool.get(session);
            IRoomService roomService = user.getRoomService();
            if (roomService != null) {
                roomService.removeUser(session, user);
                if (roomService.getUserPool().size() == 0) {
                    int roomId = roomService.getId();
                    IExistRoomRepository.deleteRoomByRoomNumber(roomId);
                    roomPool.remove(roomId);
                    log.info(session.getId() + ":移除房間:" + roomId + " 目前總房間數:" + roomPool.size());
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 送出訊息
     *
     * @param session         要送出訊息的session
     * @param messageMetaData 要送出的訊息物件
     * @return 是否成功
     */
    public Boolean sendMessage(Session session, MessageMetaData messageMetaData) {
        try {
            User user = userPool.get(session);
            IRoomService roomService = user.getRoomService();
            roomService.sendMessage(session, messageMetaData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 加入列隊
     *
     * @param session 要加入列隊的session
     * @return 是否成功
     */
    public Boolean joinQueue(Session session) {
        try {
            queuePool.add(session);
            log.info(session.getId() + ":加入列隊 目前列隊人數:" + queuePool.size());
            if (queuePool.size() >= 2) {
                Session session1 = queuePool.poll();
                Session session2 = queuePool.poll();
                Integer roomId = this.createRoom(session1);
                if (roomId != null) {
                    sendCommand(session1, CreateRoom, "success");
                }
                if (this.joinRoom(session2, roomId)) {
                    sendCommand(session2, JoinRoom, "success");
                }
                log.info(session.getId() + ":列隊人數超過兩人，創建房間 目前列隊人數:" + queuePool.size());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 離開列隊
     *
     * @param session 要離開列隊的session
     * @return 是否成功
     */
    public Boolean leaveQueue(Session session) {
        try {
            queuePool.remove(session);
            log.info(session.getId() + ":離開列隊 目前列隊人數:" + queuePool.size());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 產生不重複的房間Id
     *
     * @return 不重複的房間Id
     */
    public int generateRoomId() {
        Optional<ExistRoom> existRoom;
        int roomId;
        do {
            roomId = (int) (Math.random() * 100000)+100000;
            existRoom = IExistRoomRepository.findExistRoomByRoomNumber(roomId);
        } while (existRoom.isPresent());
        IExistRoomRepository.save(new ExistRoom(roomId));
        log.info("產生房號:" + roomId);
        return roomId;
    }

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
