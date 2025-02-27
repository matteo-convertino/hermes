package com.convertino.hermesspringapi.mapper;

import com.convertino.hermesspringapi.dto.request.UserTopicRequestDTO;
import com.convertino.hermesspringapi.dto.response.UserTopicResponseDTO;
import com.convertino.hermesspringapi.model.UserTopic;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTopicMapper {
    @InheritInverseConfiguration
    @Mappings({
        @Mapping(source = "topic.id", target = "topicId"),
        @Mapping(source = "topic.name", target = "topicName")
    })
    UserTopicResponseDTO mapToUserTopicResponseDto(UserTopic userTopic);

    UserTopic mapToUserTopic(UserTopicRequestDTO userTopicRequestDTO);

    List<UserTopicResponseDTO> mapToUserTopicResponseDto(List<UserTopic> userTopics);
}
