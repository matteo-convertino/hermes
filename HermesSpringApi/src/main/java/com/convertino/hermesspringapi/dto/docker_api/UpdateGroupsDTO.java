package com.convertino.hermesspringapi.dto.docker_api;

import lombok.Data;

import java.util.List;

@Data
public class UpdateGroupsDTO {
    private String phoneNumber;
    private long userId;
    private String deviceId;
    private List<Long> groups;
}
