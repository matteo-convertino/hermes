package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.request.DeviceRequestDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.DeviceResponseDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeviceService {
    DeviceResponseDTO update(DeviceRequestDTO deviceRequestDTO);
}
