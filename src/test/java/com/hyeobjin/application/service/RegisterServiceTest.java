package com.hyeobjin.application.service;

import com.hyeobjin.application.dto.register.RegisterDTO;
import com.hyeobjin.application.service.register.RegisterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Test
    @DisplayName("service register test")
    void test11() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("king00314@naver.com");
        registerDTO.setPassword("1234");
        registerDTO.setName("테스터");
        registerDTO.setRole("ROLE_ADMIN");
        registerService.register(registerDTO);
        Assertions.assertThat(registerDTO.getUsername()).isEqualTo("king00314@naver.com");
    }
}