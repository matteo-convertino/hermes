package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.python_api.TelegramInitLoginRequestDTO;
import com.convertino.hermesspringapi.dto.python_api.TelegramLoginVerifyCodeRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface AuthService {
    ResponseEntity<String> telegramInitLogin(TelegramInitLoginRequestDTO telegramInitLoginRequestDTO);

    ResponseEntity<String> telegramLoginVerify(TelegramLoginVerifyCodeRequestDTO telegramLoginVerifyCodeRequestDTO);
}
