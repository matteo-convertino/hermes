package com.convertino.hermesspringapi.repository;

import com.convertino.hermesspringapi.model.Device;
import com.convertino.hermesspringapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByDeviceId(String deviceId);
}
