package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.python_api.TelegramGetGroupsRequestDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.model.User;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GroupService {
    GroupResponseDTO delete(GroupRequestDTO groupRequestDTO);
    GroupResponseDTO save(GroupRequestDTO groupRequestDTO);
    ResponseEntity<String> getAll();
    List<GroupResponseDTO> getAllActive();
}
