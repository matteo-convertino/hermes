package com.convertino.hermesspringapi.dto.python_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TelegramInitLoginRequestDTO {
    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 20, message = "phoneNumber cannot be longer than 20 character")
    private String phoneNumber;
}
