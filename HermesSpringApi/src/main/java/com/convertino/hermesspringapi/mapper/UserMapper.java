package com.convertino.hermesspringapi.mapper;

import com.convertino.hermesspringapi.dto.response.UserResponseDTO;
import com.convertino.hermesspringapi.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @InheritInverseConfiguration
    UserResponseDTO mapToUserResponseDto(User user);

    User mapToUser(UserResponseDTO userResponseDto);

    List<UserResponseDTO> mapToUserResponseDto(List<User> users);
}
