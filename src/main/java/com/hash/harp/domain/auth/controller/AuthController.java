package com.hash.harp.domain.auth.controller;

import com.hash.harp.domain.auth.service.GoogleLoginService;
import com.hash.harp.global.jwt.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final GoogleLoginService googleLoginService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestParam(name = "code") String code) {
        return googleLoginService.login(code);
    }
}
