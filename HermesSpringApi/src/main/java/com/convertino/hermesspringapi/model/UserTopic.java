package com.convertino.hermesspringapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "topics_preferences",
    uniqueConstraints={
            @UniqueConstraint(columnNames = {"topic_id", "user_id"})
    }
)
public class UserTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "UserTopic{" +
                "id=" + id +
                ", value=" + value +
                ", topic=" + topic.getId() +
                ", user=" + user.getId() +
                '}';
    }
}
