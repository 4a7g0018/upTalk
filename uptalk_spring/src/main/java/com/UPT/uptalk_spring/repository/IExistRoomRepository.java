package com.UPT.uptalk_spring.repository;

import com.UPT.uptalk_spring.model.ExistRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Title: ExistRoomRepository
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/17
 */

public interface IExistRoomRepository extends JpaRepository<ExistRoom,Integer> {

    Optional<ExistRoom> findExistRoomByRoomNumber(Integer roomNumber);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM exist_room WHERE room_number=?1",nativeQuery = true)
    void deleteRoomByRoomNumber(Integer roomNumber);
}
