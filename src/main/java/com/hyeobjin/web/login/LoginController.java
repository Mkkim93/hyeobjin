package com.hyeobjin.web.login;

import com.hyeobjin.application.dto.login.LoginDTO;
import com.hyeobjin.application.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        log.info("welcome login page");
        return "/login";
    }

    @PostMapping("/loginProc")
    public String loginProc(LoginDTO loginDTO) {
        loginService.login(loginDTO);
        return "redirect:/home";
    }
}
