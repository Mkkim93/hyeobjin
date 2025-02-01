package com.hyeobjin.web.admin.jwt.api;

import com.hyeobjin.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ADMIN_JWT_AUTH", description = "JWT TOKEN 을 관리하기 위한 API 입니다.")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtApiController {

    private final JwtUtil jwtUtil;

    // TODO 아래의 로직의 데이터를 내부 서비스로 숨겨야 함 현재 리플래시 토큰 만료 관련 로직 미구현
    @PostMapping
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            // 클라이언트에 존재하는 쿠키에 있는 리플래시 토큰을 꺼냄
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // TODO expired Time : @Value 사용
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);

       //  String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        response.setHeader("Authorization", newAccess);
       //  response.addCookie(createCookie("refresh", newRefresh));
        // 현재 쿠키에 새로운 리플래시 토큰을 추가하는 로직을 redis 에서 현재 리플래시 토큰이 존재하지 않을 때 추가하는데
        // 리플래시 토큰이 존재하지 않으면 재로그인 요청을 하고 새로운 리플래시 토큰을 쿠키에 저장해야함

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
