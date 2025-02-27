package com.convertino.hermesspringapi.mapper;

import com.convertino.hermesspringapi.dto.response.DeviceResponseDTO;
import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;
import com.convertino.hermesspringapi.model.Device;
import com.convertino.hermesspringapi.model.Topic;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    @InheritInverseConfiguration
    DeviceResponseDTO mapToDeviceResponseDto(Device device);
}
