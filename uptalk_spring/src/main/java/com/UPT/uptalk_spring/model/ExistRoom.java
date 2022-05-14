package com.UPT.uptalk_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 儲存當下以使用房號
 *
 * @Title: ExistRoom
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/11
 */

@Entity
@Table(name = "exist_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExistRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_number")
    private Integer roomNumber;
}
