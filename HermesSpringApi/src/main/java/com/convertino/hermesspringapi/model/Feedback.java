package com.convertino.hermesspringapi.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private int label;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
