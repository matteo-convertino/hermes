package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.config.HermesPythonApiClient;
import com.convertino.hermesspringapi.dto.python_api.TelegramInitLoginRequestDTO;
import com.convertino.hermesspringapi.dto.python_api.TelegramLoginVerifyCodeRequestDTO;
import com.convertino.hermesspringapi.dto.response.UserResponseDTO;
import com.convertino.hermesspringapi.service.JwtService;
import com.convertino.hermesspringapi.service.definition.AuthService;
import com.convertino.hermesspringapi.service.definition.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Service
@Transactional(rollbackOn = Exception.class)
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final HermesPythonApiClient hermesPythonApiClient;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService, HermesPythonApiClient hermesPythonApiClient, ObjectMapper objectMapper, JwtService jwtService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.hermesPythonApiClient = hermesPythonApiClient;
        this.jwtService = jwtService;
    }

    private ResponseEntity<String> telegramLogin(ResponseEntity<String> response) {
        if (response.getStatusCode().isSameCodeAs(HttpStatus.OK) && response.getBody() != null) {
            UserResponseDTO userResponseDto;

            try {
                userResponseDto = objectMapper.readValue(response.getBody(), UserResponseDTO.class);
                userService.save(userResponseDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            String jwt = jwtService.generateToken(userResponseDto);
            Map<String, Object> map = objectMapper.convertValue(userResponseDto, Map.class);
            map.put("jwt", jwt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(APPLICATION_JSON);

            try {
                return ResponseEntity.ok().headers(headers).body(objectMapper.writeValueAsString(map));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }

    @Override
    public ResponseEntity<String> telegramInitLogin(TelegramInitLoginRequestDTO telegramInitLoginRequestDto) {
        return telegramLogin(hermesPythonApiClient.sendPost("/init-login", telegramInitLoginRequestDto));
    }

    @Override
    public ResponseEntity<String> telegramLoginVerify(TelegramLoginVerifyCodeRequestDTO telegramLoginVerifyCodeRequestDto) {
        return telegramLogin(hermesPythonApiClient.sendPost("/login-verify-code", telegramLoginVerifyCodeRequestDto));
    }
}
