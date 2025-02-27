package com.convertino.hermesspringapi.controller;

import com.convertino.hermesspringapi.dto.request.FeedbackRequestDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.FeedbackResponseDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;
import com.convertino.hermesspringapi.service.definition.FeedbackService;
import com.convertino.hermesspringapi.service.definition.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> add(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        return ResponseEntity.ok(feedbackService.save(feedbackRequestDTO));
    }
}
