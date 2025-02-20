package com.hyeobjin.application.admin.service.auth;

import com.hyeobjin.application.admin.dto.users.CheckUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminAuthServiceTest {

    @Autowired
    AdminAuthService adminAuthService;

    @Test
    @DisplayName("아이디와 비밀번호 입력후 나의 정보 조회")
    void findMyProfile() {
        String username = "king00314@naver.com";
        String password = "1234";

        CheckUserDTO myProfile = adminAuthService.findMyProfile(username, password);

        assertThat(myProfile.getUsername()).isEqualTo(username);
    }
}