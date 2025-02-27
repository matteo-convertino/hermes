package com.convertino.hermesspringapi.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class UserResponseDTO {
    private int id;
    private String deviceId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String username;
    private String photo;
}
