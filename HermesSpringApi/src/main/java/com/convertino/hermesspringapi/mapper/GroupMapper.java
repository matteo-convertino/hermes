package com.convertino.hermesspringapi.mapper;

import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.model.Group;
import com.convertino.hermesspringapi.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @InheritInverseConfiguration
    @Mappings({
        @Mapping(source = "user.id", target = "userId")
    })
    GroupResponseDTO mapToGroupResponseDto(Group group);

    Group mapToGroup(GroupRequestDTO groupRequestDTO);

    List<GroupResponseDTO> mapToGroupResponseDto(List<Group> groups);
}
