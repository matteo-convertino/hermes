package com.convertino.hermesspringapi.repository;

import com.convertino.hermesspringapi.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
