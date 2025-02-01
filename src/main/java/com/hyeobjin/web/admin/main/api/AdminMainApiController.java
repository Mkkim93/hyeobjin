package com.hyeobjin.web.admin.main.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "COMMON_MAIN", description = "메인 폼 랜더링 관련 API")
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminMainApiController {

    @GetMapping
    public String adminP() {
        return "/admin";
    }
}
