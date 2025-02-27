package com.convertino.hermesspringapi.controller;

import com.convertino.hermesspringapi.dto.request.UserTopicRequestDTO;
import com.convertino.hermesspringapi.dto.response.UserTopicResponseDTO;
import com.convertino.hermesspringapi.service.definition.UserTopicService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-topics")
public class UserTopicController {
    private final UserTopicService userTopicService;

    public UserTopicController(UserTopicService userTopicService) {
        this.userTopicService = userTopicService;
    }

    @PostMapping
    public ResponseEntity<UserTopicResponseDTO> add(@Valid @RequestBody UserTopicRequestDTO userTopicRequestDTO) {
        return ResponseEntity.ok(userTopicService.save(userTopicRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserTopicResponseDTO>> getAll() {
        return ResponseEntity.ok(userTopicService.getAll());
    }

    @DeleteMapping
    public ResponseEntity<UserTopicResponseDTO> delete(@RequestParam long topicId) {
        return ResponseEntity.ok(userTopicService.delete(topicId));
    }
}
