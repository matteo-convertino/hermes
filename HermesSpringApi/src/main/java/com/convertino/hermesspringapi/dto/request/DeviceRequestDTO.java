package com.convertino.hermesspringapi.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class DeviceRequestDTO {
    @NotNull(message = "firebaseToken cannot be null")
    @NotEmpty(message = "firebaseToken cannot be empty")
    private String firebaseToken;
}
