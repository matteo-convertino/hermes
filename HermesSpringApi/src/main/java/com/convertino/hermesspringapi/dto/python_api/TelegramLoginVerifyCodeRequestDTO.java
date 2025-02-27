package com.convertino.hermesspringapi.dto.python_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TelegramLoginVerifyCodeRequestDTO {
    @NotBlank(message = "deviceId cannot be blank")
    @Size(max = 50, message = "deviceId cannot be longer than 50 character")
    private String deviceId;

    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 20, message = "phoneNumber cannot be longer than 20 character")
    private String phoneNumber;

    @NotBlank(message = "code cannot be blank")
    @Size(max = 5, message = "code cannot be longer than 5 character")
    private String code;

    private String password;
}
