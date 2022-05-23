package com.UPT.uptalk_spring.controller;

import com.UPT.uptalk_spring.utils.jwt.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @Title: TokenController
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/17
 */

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private JWTUtils jwtUtils;

    /**
     * Headers 中將Authorization帶入 refresh token 即可驗證並更新 account token。
     */
    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            Map<String, String> token = jwtUtils.refresh(refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            System.out.println(token.get("account_token"));
            new ObjectMapper().writeValue(response.getOutputStream(), token);
        }
    }
}
