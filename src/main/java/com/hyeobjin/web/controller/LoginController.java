package com.hyeobjin.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String hello() {
        return "hello";
    }
}
