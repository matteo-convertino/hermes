package com.convertino.hermesspringapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class GroupRequestDTO {
    @NotNull(message = "groupId cannot be null")
    private Long groupId;
}
