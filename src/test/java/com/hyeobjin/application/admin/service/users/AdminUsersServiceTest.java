package com.hyeobjin.application.admin.service.users;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminUsersServiceTest {

    @Autowired
    AdminUsersService adminUsersService;

    @Test
    @DisplayName("관리자 정보 상세 조회")
    void findByUserDetail() {
        Long userId = 1L;
        FindUsersDTO result = adminUsersService.detail(userId);

        System.out.println("result.getUsersId = " + result.getUsersId());
        System.out.println("result.getName = " + result.getName());
        System.out.println("result.getUsername = " + result.getUsername());
        System.out.println("result.getRole = " + result.getRole());
        System.out.println("result.getUserMail = " + result.getUserMail());
        System.out.println("result.getUserTel() = " + result.getUserTel());

        Assertions.assertThat(result.getUsersId()).isEqualTo(userId);
    }
}