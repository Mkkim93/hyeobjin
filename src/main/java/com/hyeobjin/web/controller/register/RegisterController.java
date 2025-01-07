package com.hyeobjin.web.controller.register;

import com.hyeobjin.application.dto.register.RegisterDTO;
import com.hyeobjin.application.service.register.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @GetMapping
    public ResponseEntity<String> register() {

        return ResponseEntity.ok("회원가입 페이지 이동");
    }

    @PostMapping
    public ResponseEntity<String> registerProc(@RequestBody RegisterDTO registerDTO) {

        Boolean isRegistered = registerService.register(registerDTO);

        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("register front success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("register front failed");
        }
    }
}
