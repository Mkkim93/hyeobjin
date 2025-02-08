package com.hyeobjin.web.admin.main.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "COMMON_MAIN", description = "메인 폼 랜더링 관련 API")
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminMainApiController {

    @GetMapping
    public Map<String, String> adminP(@RequestParam(value = "redirect", required = false) String redirectPath) {
        Map<String, String> response = new HashMap<>();

        if (redirectPath != null && !redirectPath.isEmpty()) {
            response.put("redirectUrl", redirectPath);
        } else {
            response.put("redirectUrl", "/admin");
        }

        return response;
    }
}
