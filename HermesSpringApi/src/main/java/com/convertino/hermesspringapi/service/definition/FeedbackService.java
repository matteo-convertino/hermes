package com.convertino.hermesspringapi.service.definition;


import com.convertino.hermesspringapi.dto.request.FeedbackRequestDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.FeedbackResponseDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {
    FeedbackResponseDTO save(FeedbackRequestDTO feedbackRequestDTO);
}
