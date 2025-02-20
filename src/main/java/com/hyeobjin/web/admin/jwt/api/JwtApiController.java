package com.hyeobjin.web.admin.jwt.api;

import com.hyeobjin.application.admin.service.jwt.JwtService;
import com.hyeobjin.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@Tag(name = "ADMIN_JWT_AUTH", description = "JWT TOKEN 을 관리하기 위한 API 입니다.")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtApiController {

    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        log.info("/auth 실행");

        Cookie[] cookies = request.getCookies();

        Map<String, String> checkAuth = jwtService.jwtTokenReissue(cookies);

        String reissue = checkAuth.get("checkReissue");

        if (reissue.equals("cookie is empty")) {
            return new ResponseEntity<>(reissue, HttpStatus.UNAUTHORIZED);
        } else {

            response.setHeader("Authorization", reissue);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
