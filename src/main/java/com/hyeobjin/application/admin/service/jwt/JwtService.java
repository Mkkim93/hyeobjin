package com.hyeobjin.application.admin.service.jwt;

import com.hyeobjin.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    public Map<String, String> jwtTokenReissue(Cookie[] cookies) {

        HashMap<String, String> reissueMap = new HashMap<>();
        String refreshToken = null;

        if (cookies == null) {
         reissueMap.put("checkReissue", "cookie is empty");
         return reissueMap;
        }

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refreshToken = cookie.getValue();

                String username = jwtUtil.getUsername(refreshToken);
                String role = jwtUtil.getRole(refreshToken);

                String accessToken = jwtUtil.createJwt("access", username, role, 3600000L);

                reissueMap.put("checkReissue", accessToken);
            }
        }
        return reissueMap;
    }
}
