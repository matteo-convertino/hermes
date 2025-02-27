package com.convertino.hermesspringapi.dto.python_api;

import lombok.Data;

@Data
public class TelegramLoginResponseDTO {
    private int userId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String username;
    private String photo;
}
