package com.convertino.hermesspringapi.mapper;

import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;
import com.convertino.hermesspringapi.model.Topic;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
//    @InheritInverseConfiguration
//    TopicResponseDTO mapToTopicResponseDto(UserTopic userTopic);

    // UserTopic mapToUserTopic(UserTopicRequestDTO userTopicRequestDTO);

    List<TopicResponseDTO> mapToTopicResponseDto(List<Topic> topics);
}
