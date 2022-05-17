package com.UPT.uptalk_spring.filter;

import com.UPT.uptalk_spring.utils.jwt.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.UPT.uptalk_spring.filter.AuthenticationFilter.LOGIN_URL;
import static com.UPT.uptalk_spring.filter.AuthenticationFilter.REFRESH_TOKEN_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 權限驗證 Filter
 *
 * @Title: CustomAuthorizationFilter
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/17
 */

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //略過不認證權限的 URL
        // LOGIN_URL : /api/login
        // REFRESH_TOKEN_URL : /api/token/refresh
        if (request.getRequestURI().equals(LOGIN_URL) || request.getRequestURL().equals(REFRESH_TOKEN_URL)) {
            filterChain.doFilter(request, response);
        } else {
            //JWT驗證
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                log.info("toke : {}",token);
                JWTUtils jwtUtils =new JWTUtils();
                boolean authenticationSuccess = jwtUtils.verify(token);

                if (authenticationSuccess) {
                    filterChain.doFilter(request,response);
                }else {
                    log.error("Logging error");
                    response.setHeader("error","token authentication error");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("Error_Message", "token authentication error");
                    response.setContentType(APPLICATION_JSON_VALUE);

                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }else {
                filterChain.doFilter(request,response);
            }
        }
    }
}
