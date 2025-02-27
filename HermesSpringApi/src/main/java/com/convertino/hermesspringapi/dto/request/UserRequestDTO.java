package com.convertino.hermesspringapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserRequestDTO {
    @NotBlank(message = "name cannot be blank")
    @Size(max = 30, message = "name cannot be longer than 30 character")
    private String name;

    @Size(max = 30, message = "surname cannot be longer than 30 character")
    private String surname;

    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 20, message = "phoneNumber cannot be longer than 20 character")
    private String phoneNumber;

    private String profileImagePath;
}
