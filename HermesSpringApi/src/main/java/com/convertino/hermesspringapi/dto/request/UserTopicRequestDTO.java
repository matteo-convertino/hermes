package com.convertino.hermesspringapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserTopicRequestDTO {
    @NotNull(message = "isFavourite cannot be null")
    private Boolean isFavourite;

    @NotNull(message = "topicId cannot be null")
    private Long topicId;
}
