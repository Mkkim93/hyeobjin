package com.hyeobjin.application.admin.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@Slf4j
@SpringBootTest
class AdminAuthServiceTest {

    @Autowired
    AdminAuthService adminAuthService;

    @Test
    @DisplayName("관리자 전체 조회")
    void findAll() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        adminAuthService.findAll(pageRequest);
    }
}