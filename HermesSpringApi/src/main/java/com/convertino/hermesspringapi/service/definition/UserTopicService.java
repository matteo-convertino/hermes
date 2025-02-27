package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.request.UserTopicRequestDTO;
import com.convertino.hermesspringapi.dto.response.UserTopicResponseDTO;
import com.convertino.hermesspringapi.model.User;

import java.util.List;

public interface UserTopicService {
    UserTopicResponseDTO delete(long topicId);
    UserTopicResponseDTO save(UserTopicRequestDTO userTopicRequestDTO);
    List<UserTopicResponseDTO> getAll();
}
