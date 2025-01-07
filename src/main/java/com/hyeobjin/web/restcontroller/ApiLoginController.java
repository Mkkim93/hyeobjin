package com.hyeobjin.web.restcontroller;

import com.hyeobjin.application.dto.login.LoginDTO;
import com.hyeobjin.application.service.login.JwtUserDetailsService;
import com.hyeobjin.application.service.login.LoginService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiLoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public ResponseEntity<?> loginApi(@RequestBody LoginDTO loginDTO,
                                   HttpServletResponse response) {

        String token = loginService.login(loginDTO);
        response.setHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok().body("login successful");
    }
}
