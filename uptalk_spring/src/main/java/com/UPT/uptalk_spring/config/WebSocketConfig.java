package com.UPT.uptalk_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @Title: WebSocketConfig
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/20
 */

@Configuration
public class WebSocketConfig{
    @Profile({"dev","test"})
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
