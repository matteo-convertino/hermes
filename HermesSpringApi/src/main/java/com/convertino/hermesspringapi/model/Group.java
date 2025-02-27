package com.convertino.hermesspringapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "groups",
    uniqueConstraints={
            @UniqueConstraint(columnNames = {"group_id", "user_id"})
    }
)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_id", nullable = false)
    private long groupId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
