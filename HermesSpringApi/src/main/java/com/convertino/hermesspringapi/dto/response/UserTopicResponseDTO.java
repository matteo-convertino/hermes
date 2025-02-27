package com.convertino.hermesspringapi.dto.response;

import lombok.Data;


@Data
public class UserTopicResponseDTO {
    private Double value;
    private long topicId;
    private String topicName;
}
