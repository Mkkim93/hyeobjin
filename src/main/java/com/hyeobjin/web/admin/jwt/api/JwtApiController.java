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

    /** TODO 서버에서 현재 쿠키에 REFRESH 토큰 존재여부 확인해서 클라이언트로 응답값 보내줘야함
        클라이언트에서는 해당 응답값을 받아서 refresh 가 없으면 access 를 remove 시키고 /login 폼으로 강제 이동
        why ? 브라우저에서 특정 이벤트나 알수없는 오류 발생으로 access 토큰은 남아있고 refresh 토큰만 사라질 수 있기 때무에
        이런 경우 다시 재로그인 해서 토큰을 재발급 해줘야함
        어차피 redis 에 토큰이 남아 있어도 재로그인을하면 덮어 씌워지기 때문에 redis 는 따로 delete 할 필요 없을 듯
     */
    @GetMapping("/checkrefresh")
    public ResponseEntity<String> checkedRefreshToken(HttpServletRequest request) {

        String refresh = null;

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
                log.info("쿠키에 존재하는 refresh token ={}", refresh);
            }

            if (refresh == null) {
//                return new ResponseEntity<>("refresh token null!", )
            }
        }
        return null;
    }

    // TODO 아래의 로직의 데이터를 내부 서비스로 숨겨야 함 현재 리플래시 토큰 만료 관련 로직 미구현
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

//         String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        response.setHeader("Authorization", newAccess);
//         response.addCookie(createCookie("refresh", newRefresh));
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
