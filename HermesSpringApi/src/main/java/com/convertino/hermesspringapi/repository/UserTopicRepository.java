package com.convertino.hermesspringapi.repository;

import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.model.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {
    Optional<UserTopic> findByTopicIdAndUser(long topicId, User user);
    List<UserTopic> findAllByUser(User user);
}
