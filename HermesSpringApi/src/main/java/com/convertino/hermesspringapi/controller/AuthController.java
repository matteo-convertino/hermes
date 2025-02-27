package com.convertino.hermesspringapi.controller;


import com.convertino.hermesspringapi.dto.python_api.TelegramInitLoginRequestDTO;
import com.convertino.hermesspringapi.dto.python_api.TelegramLoginVerifyCodeRequestDTO;
import com.convertino.hermesspringapi.service.definition.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/ping")
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/init-login")
    public ResponseEntity<String> initLogin(@Valid @RequestBody TelegramInitLoginRequestDTO telegramInitLoginRequestDto) {
        return authService.telegramInitLogin(telegramInitLoginRequestDto);
    }

    @PostMapping("/login-verify-code")
    public ResponseEntity<String> loginVerifyCode(@Valid @RequestBody TelegramLoginVerifyCodeRequestDTO telegramLoginVerifyCodeRequestDto) {
        return authService.telegramLoginVerify(telegramLoginVerifyCodeRequestDto);
    }
}
