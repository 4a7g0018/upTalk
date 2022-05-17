package com.UPT.uptalk_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: MessageMetaData
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/14
 *
 * type : Command代表message是命令、Text代表message是訊息
 * message : 實際訊息內容或命令內容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageMetaData {
    private MessageType type;
    private String message;
}