package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.response.UserResponseDTO;
import com.convertino.hermesspringapi.model.User;

public interface UserService {
    User findUserById(long id);

    void save(UserResponseDTO userResponseDto);
}