package com.UPT.uptalk_spring.repository;

import com.UPT.uptalk_spring.model.ExistRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Title: ExistRoomRepository
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/17
 */

public interface IExistRoomRepository extends JpaRepository<ExistRoom,Integer> {

    Optional<ExistRoom> findExistRoomByRoomNumber(Integer roomNumber);
}
