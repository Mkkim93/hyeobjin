package com.hyeobjin.web.admin.jwt.api;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "ADMIN_JWT_AUTH", description = "JWT TOKEN 을 관리하기 위한 API 입니다.")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtApiController {

    private final JwtUtil jwtUtil;

    @GetMapping("/checkrefresh")
    public ResponseEntity<String> checkedRefreshToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return new ResponseEntity<>("refresh token null!", HttpStatus.UNAUTHORIZED);
        }

        String refresh = null;

        for (Cookie cookie : cookies) {
            if ("refresh".equals(cookie.getName())) {
                refresh = cookie.getValue();
                log.info("쿠키에 존재하는 refresh token = {}", refresh);
                break;
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null!", HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(refresh);
    }

    @PostMapping
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        log.info("/auth 실행");

        String refresh = null;

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return new ResponseEntity<>("cookie is empty", HttpStatus.UNAUTHORIZED);
        }

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
                log.info("현재 쿠키에 있는 리플래시 토큰 ={}", refresh);
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.UNAUTHORIZED);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.UNAUTHORIZED);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.UNAUTHORIZED);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // TODO expired Time : @Value 사용
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);

        response.setHeader("Authorization", newAccess);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
