package com.hyeobjin.web.admin.main.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminMainApiController {

    @GetMapping("/admin")
    public String adminP() {
        return "/admin";
    }
}
