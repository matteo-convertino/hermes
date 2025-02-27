package com.convertino.hermesspringapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 4096)
    private String text;

    @Column(name = "text_id", nullable = false)
    private long textId;

    @Column(name = "group_name", nullable = false)
    private String group;

    @Column(name = "group_id", nullable = false)
    private long groupId;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false, length = 30)
    private String topic;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
