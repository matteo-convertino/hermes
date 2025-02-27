package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.dto.response.UserResponseDTO;
import com.convertino.hermesspringapi.exceptions.EntityCreationException;
import com.convertino.hermesspringapi.exceptions.EntityDuplicateException;
import com.convertino.hermesspringapi.mapper.UserMapper;
import com.convertino.hermesspringapi.model.Device;
import com.convertino.hermesspringapi.repository.DeviceRepository;
import com.convertino.hermesspringapi.repository.UserRepository;
import com.convertino.hermesspringapi.service.definition.UserService;
import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DeviceRepository deviceRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> EntityNotFoundException.run("user", "id", id));
    }

    public void save(UserResponseDTO userResponseDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userResponseDto.getUsername());
        User user = optionalUser.orElseGet(() -> userMapper.mapToUser(userResponseDto));

        Device device = new Device(userResponseDto.getDeviceId(), user);

        user.addDevice(device);

        try {
            userRepository.save(user);
            deviceRepository.save(device);
        } catch (DataIntegrityViolationException e) {
            throw EntityDuplicateException.run("user", "username", user.getUsername());
        } catch (Exception e) {
            throw EntityCreationException.run("user");
        }
    }
}