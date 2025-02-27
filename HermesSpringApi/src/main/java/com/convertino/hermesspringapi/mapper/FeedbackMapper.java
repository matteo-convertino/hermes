package com.convertino.hermesspringapi.mapper;

import com.convertino.hermesspringapi.dto.request.FeedbackRequestDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.FeedbackResponseDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.model.Feedback;
import com.convertino.hermesspringapi.model.Group;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @InheritInverseConfiguration
    FeedbackResponseDTO mapToFeedbackResponseDTO(Feedback feedback);

    Feedback mapToFeedback(FeedbackRequestDTO feedbackRequestDTO);

    // List<GroupResponseDTO> mapToGroupResponseDto(List<Group> groups);
}
