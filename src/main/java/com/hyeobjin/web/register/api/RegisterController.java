package com.hyeobjin.web.register.api;

import com.hyeobjin.application.dto.register.RegisterDTO;
import com.hyeobjin.application.service.register.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Register", description = "관리자 등록 관련 API")
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
