package com.convertino.hermesspringapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device {
    public Device(String deviceId, User user) {
        this.deviceId = deviceId;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "device_id", unique = true, nullable = false, length = 50)
    private String deviceId;

    @Column(name = "firebase_token")
    private String firebaseToken;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
