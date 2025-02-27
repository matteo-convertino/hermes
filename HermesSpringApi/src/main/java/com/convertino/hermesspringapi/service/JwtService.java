package com.convertino.hermesspringapi.service;

import com.convertino.hermesspringapi.dto.response.UserResponseDTO;
import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.service.definition.UserService;
import com.convertino.hermesspringapi.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final UserService userService;
    private final UserMapper userMapper;

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.token_days_duration}")
    private int tokenDaysDuration;

    public JwtService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserResponseDTO userResponseDto) {
        return generateToken(userMapper.mapToUser(userResponseDto), userResponseDto.getDeviceId());
    }

    public String generateToken(User user, String deviceId) {
        long tokenDaysDurationMillis = 1000L * 60 * 60 * 24 * tokenDaysDuration;

        return Jwts.builder().claims()
                .subject(deviceId)
                .add("userId", user.getId())
                .add("username", user.getUsername())
                .add("phoneNumber", user.getPhoneNumber())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenDaysDurationMillis))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(generateKey())
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }

    public String getSubject(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Object getFieldFromToken(String token, String field) {
        return getClaimsFromToken(token).get(field);
    }

    public User getUserFromToken(String token) {
        Integer userId = (Integer) getFieldFromToken(token, "userId");
        return userService.findUserById(userId);
    }
}
