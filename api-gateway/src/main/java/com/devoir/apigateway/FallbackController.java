package com.devoir.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public String fallback() {
        return ("⚠️ Service unavailable. Please try again later (Fallback Mechanism Active).");
    }
}