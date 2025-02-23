package com.hyeobjin.application.admin.service.jwt;

import com.hyeobjin.application.common.service.redis.RedisService;
import com.hyeobjin.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public Map<String, String> jwtTokenReissue(Cookie[] cookies, String checkAccessToken) {
        HashMap<String, String> reissueMap = new HashMap<>();
        reissueMap.put("checkReissue", "cookie is empty");

        String usernameAccess = null;

        if (checkAccessToken != null) {
            try {
                usernameAccess = jwtUtil.getUsername(checkAccessToken);
            } catch (Exception e) {
                log.error("Access Token이 유효하지 않음: {}", e.getMessage());
            }
        }

        if (cookies == null) {
            if (usernameAccess != null) {
                redisService.delete(usernameAccess);
            }
            return reissueMap;
        }

        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null) {
            if (usernameAccess != null) {
                redisService.delete(usernameAccess);
            }
            return reissueMap;
        }

        try {
            String username = jwtUtil.getUsername(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            String accessToken = jwtUtil.createJwt("access", username, role, 3600000L);

            reissueMap.put("checkReissue", accessToken);
        } catch (Exception e) {
            log.error("Refresh Token 검증 실패: {}", e.getMessage());
        }

        return reissueMap;
    }


}
