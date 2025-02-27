package com.convertino.hermesspringapi.repository;

import com.convertino.hermesspringapi.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
