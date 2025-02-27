package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.dto.request.UserTopicRequestDTO;
import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;
import com.convertino.hermesspringapi.dto.response.UserTopicResponseDTO;
import com.convertino.hermesspringapi.exceptions.EntityCreationException;
import com.convertino.hermesspringapi.exceptions.EntityDuplicateException;
import com.convertino.hermesspringapi.exceptions.EntityNotFoundException;
import com.convertino.hermesspringapi.mapper.TopicMapper;
import com.convertino.hermesspringapi.mapper.UserTopicMapper;
import com.convertino.hermesspringapi.model.Topic;
import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.model.UserTopic;
import com.convertino.hermesspringapi.repository.TopicRepository;
import com.convertino.hermesspringapi.repository.UserRepository;
import com.convertino.hermesspringapi.repository.UserTopicRepository;
import com.convertino.hermesspringapi.service.definition.TopicService;
import com.convertino.hermesspringapi.service.definition.UserTopicService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    @Override
    public List<TopicResponseDTO> getAll() {
        return topicMapper.mapToTopicResponseDto(topicRepository.findAll());
    }
}
