package com.hyeobjin.application.service.redis;

import com.hyeobjin.application.common.service.redis.RedisService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("redis db save test")
    void save() {
        String key = "testKey";
        String value = "testValue";

        redisService.save(key, value, 100L);
        assertThat(redisService.find(key)).isEqualTo(value);

        redisService.delete(key);
        assertThat(redisService.find(key)).isNull();
    }
}