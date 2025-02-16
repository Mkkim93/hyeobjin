package com.hyeobjin.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyeobjin.application.common.dto.login.LoginDTO;
import com.hyeobjin.application.common.service.redis.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final RedisService redisService;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            // JSON 데이터에서 username과 password 추출
            // 클라이언트에서 넘어온 데이터 (JSON -> JAVA)
            ObjectMapper objectMapper = new ObjectMapper();

            LoginDTO LoginDTO = objectMapper.readValue(
                    request.getInputStream(), LoginDTO.class);

            String username = LoginDTO.getUsername();
            String password = LoginDTO.getPassword();

            log.info("username={}", username);
            log.info("password={}", password);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password, null);

            log.info("AuthToken={}", authToken);
            log.info("로그인 시각 ={}", LocalDateTime.now());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new AuthenticationException("Invalid authentication data") {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // access 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 36000000L); // TODO 원래 10 min 인데 일단 늘려놓음

        // refresh 토큰 생성
        String refresh = jwtUtil.createJwt("refresh", username, role, 36000000L);// 24 hours
        log.info("refresh value ={}", refresh.toString());

        // redis 에 최초 발급된 refresh token 저장
        redisService.save("refresh:" + username, refresh, 36000000L);

        // 응답 설정
        response.setHeader("Authorization", access); // 여기에서 넣은 토큰 키값을 doFilterInter~() 에서 getHeader 로 읽는다 키값 일치화 시켜야됨
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 10);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
