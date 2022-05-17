package com.UPT.uptalk_spring.utils.jwt;

import com.UPT.uptalk_spring.model.Role;
import com.UPT.uptalk_spring.model.UserInfo;
import com.UPT.uptalk_spring.service.IUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JWT工具，包含生成、驗證、refresh
 *
 * @Title: JWTUtils
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/17
 */

@Component
public class JWTUtils {
    private static final String secret = "secret";
    private static final int lifeTime = 60 * 60 * 1000;
    private static final int refreshTime = 720 * 60 * 1000;

    @Autowired
    private IUserService userService;

    /**
     * 生成 account token 與 refresh token，並以 Map 回傳。
     */
    public Map<String, String> sing(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accountToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + lifeTime))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTime))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        Map<String, String> token = new HashMap<>();
        token.put("account_token", accountToken);
        token.put("refresh_token", refreshToken);

        return token;
    }

    /**
     * 進行 token 驗證，驗證成功返回 true，失敗則返回 false。
     */
    public boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decoderJWT = jwtVerifier.verify(token);
            String email = decoderJWT.getSubject();
            String[] roles = decoderJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 重新生成 account token，並將原本的 refresh token 與新產生的 account token 包成 map 回傳。
     */
    public Map<String, String> refresh(String refreshToken) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decoderJWT = jwtVerifier.verify(refreshToken);
        String email = decoderJWT.getSubject();
        UserInfo userInfo = this.userService.findUserByUserEmail(email);

        String accountToken = JWT.create()
                .withSubject(userInfo.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + lifeTime))
                .withClaim("roles", userInfo.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);

        Map<String, String> token = new HashMap<>();
        token.put("account_token", accountToken);
        token.put("refresh_token", refreshToken);

        return token;
    }
}
