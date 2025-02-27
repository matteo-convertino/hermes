package com.convertino.hermesspringapi.controller;

import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;
import com.convertino.hermesspringapi.service.definition.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDTO>> getAll() {
        return ResponseEntity.ok(topicService.getAll());
    }
}
