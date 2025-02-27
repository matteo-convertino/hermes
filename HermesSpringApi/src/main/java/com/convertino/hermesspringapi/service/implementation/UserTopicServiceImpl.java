package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.dto.request.UserTopicRequestDTO;
import com.convertino.hermesspringapi.dto.response.UserTopicResponseDTO;
import com.convertino.hermesspringapi.exceptions.EntityCreationException;
import com.convertino.hermesspringapi.exceptions.EntityDeletionException;
import com.convertino.hermesspringapi.exceptions.EntityDuplicateException;
import com.convertino.hermesspringapi.exceptions.EntityNotFoundException;
import com.convertino.hermesspringapi.mapper.UserTopicMapper;
import com.convertino.hermesspringapi.model.Topic;
import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.model.UserTopic;
import com.convertino.hermesspringapi.repository.TopicRepository;
import com.convertino.hermesspringapi.repository.UserTopicRepository;
import com.convertino.hermesspringapi.service.definition.UserTopicService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserTopicServiceImpl implements UserTopicService {
    private final UserTopicRepository userTopicRepository;
    private final TopicRepository topicRepository;
    private final UserTopicMapper userTopicMapper;

    public UserTopicServiceImpl(UserTopicRepository userTopicRepository, UserTopicMapper userTopicMapper, TopicRepository topicRepository) {
        this.userTopicRepository = userTopicRepository;
        this.userTopicMapper = userTopicMapper;
        this.topicRepository = topicRepository;
    }

    @Override
    public UserTopicResponseDTO delete(long topicId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserTopic userTopic = userTopicRepository.findByTopicIdAndUser(topicId, user).orElseThrow(() -> EntityNotFoundException.run("userTopic", "topicId", topicId));

        try {
            userTopicRepository.delete(userTopic);
        } catch (Exception e) {
            throw EntityDeletionException.run("userTopic", "topicId", topicId);
        }

        return userTopicMapper.mapToUserTopicResponseDto(userTopic);
    }

    @Override
    public UserTopicResponseDTO save(UserTopicRequestDTO userTopicRequestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topic topic = topicRepository.findById(userTopicRequestDTO.getTopicId()).orElseThrow(() -> EntityNotFoundException.run("topic", "id", userTopicRequestDTO.getTopicId()));

        UserTopic userTopic = userTopicRepository.findByTopicIdAndUser(userTopicRequestDTO.getTopicId(), user).orElse(userTopicMapper.mapToUserTopic(userTopicRequestDTO));

        userTopic.setUser(user);
        userTopic.setTopic(topic);
        userTopic.setValue(userTopicRequestDTO.getIsFavourite() ? 0.25 : -0.25);

        try {
            userTopicRepository.save(userTopic);
        } catch (DataIntegrityViolationException e) {
            throw EntityDuplicateException.run("userTopic", "topicId", userTopic.getTopic().getId());
        } catch (Exception e) {
            throw EntityCreationException.run("userTopic");
        }

        return userTopicMapper.mapToUserTopicResponseDto(userTopic);
    }

    @Override
    public List<UserTopicResponseDTO> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userTopicMapper.mapToUserTopicResponseDto(userTopicRepository.findAllByUser(user));
    }
}
