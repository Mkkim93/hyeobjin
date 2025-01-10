package com.hyeobjin.web.restcontroller;

import com.hyeobjin.application.dto.login.LoginDTO;
import com.hyeobjin.application.service.login.JwtUserDetailsService;
import com.hyeobjin.application.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiLoginController {

    private final LoginService loginService;

    @Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인 (ROLE_ADMIN)")
    @PostMapping("/api/login")
    public ResponseEntity<?> loginApi(@RequestBody LoginDTO loginDTO,
                                   HttpServletResponse response) throws IOException {

        String token = loginService.login(loginDTO);
        response.setHeader("Authorization", token);
        return ResponseEntity.ok().body("login success");
    }
}
