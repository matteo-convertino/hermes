package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;

import java.util.List;

public interface TopicService {
    List<TopicResponseDTO> getAll();
}
