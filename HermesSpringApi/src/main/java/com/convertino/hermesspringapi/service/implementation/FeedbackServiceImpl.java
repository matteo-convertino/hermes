package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.config.HermesDockerApiClient;
import com.convertino.hermesspringapi.config.HermesPythonApiClient;
import com.convertino.hermesspringapi.dto.docker_api.UpdateGroupsDTO;
import com.convertino.hermesspringapi.dto.request.FeedbackRequestDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.FeedbackResponseDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.exceptions.EntityCreationException;
import com.convertino.hermesspringapi.exceptions.EntityDeletionException;
import com.convertino.hermesspringapi.exceptions.EntityDuplicateException;
import com.convertino.hermesspringapi.exceptions.EntityNotFoundException;
import com.convertino.hermesspringapi.mapper.FeedbackMapper;
import com.convertino.hermesspringapi.mapper.GroupMapper;
import com.convertino.hermesspringapi.model.Feedback;
import com.convertino.hermesspringapi.model.Group;
import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.repository.FeedbackRepository;
import com.convertino.hermesspringapi.repository.GroupRepository;
import com.convertino.hermesspringapi.service.definition.FeedbackService;
import com.convertino.hermesspringapi.service.definition.GroupService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public FeedbackResponseDTO save(FeedbackRequestDTO feedbackRequestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Feedback feedback = feedbackMapper.mapToFeedback(feedbackRequestDTO);

        feedback.setUser(user);

        try {
            feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw EntityCreationException.run("feedback");
        }

        return feedbackMapper.mapToFeedbackResponseDTO(feedback);
    }
}
