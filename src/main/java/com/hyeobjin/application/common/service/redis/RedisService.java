package com.hyeobjin.application.common.service.redis;

import com.hyeobjin.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value, long timeout) {
        log.info("refresh token redis db save complete");
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public Object find(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String username) {
        Object value = redisTemplate.opsForValue().get("refresh:" + username);

        String savedRefreshToken = value.toString();

        String savedUsername = jwtUtil.getUsername(savedRefreshToken);

        if (username.equals(savedUsername)) {

            redisTemplate.delete("refresh:" + username);
        }
    }

    public boolean isRefreshExist(String username) {
        Object userToken = redisTemplate.opsForValue().get("refresh:" + username);

        String savedRefreshToken = userToken.toString(); // 저장되어 있는 refresh Token

        String savedUsername = jwtUtil.getUsername(savedRefreshToken); // 저장된 토큰에서 username 추출

        if (username.equals(savedUsername)) { // 저장된 username 과 파라미터로 받은 username 과 비교
            return true;
        }
        return false;
    }
}
