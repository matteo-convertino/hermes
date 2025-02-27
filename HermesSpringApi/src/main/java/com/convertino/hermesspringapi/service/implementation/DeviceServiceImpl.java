package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.dto.request.DeviceRequestDTO;
import com.convertino.hermesspringapi.dto.response.DeviceResponseDTO;
import com.convertino.hermesspringapi.dto.response.TopicResponseDTO;
import com.convertino.hermesspringapi.exceptions.EntityCreationException;
import com.convertino.hermesspringapi.exceptions.EntityDuplicateException;
import com.convertino.hermesspringapi.exceptions.EntityNotFoundException;
import com.convertino.hermesspringapi.mapper.DeviceMapper;
import com.convertino.hermesspringapi.mapper.TopicMapper;
import com.convertino.hermesspringapi.model.Device;
import com.convertino.hermesspringapi.repository.DeviceRepository;
import com.convertino.hermesspringapi.repository.TopicRepository;
import com.convertino.hermesspringapi.service.definition.DeviceService;
import com.convertino.hermesspringapi.service.definition.TopicService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public DeviceResponseDTO update(DeviceRequestDTO deviceRequestDTO) {
        String deviceId = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Device device = deviceRepository.findByDeviceId(deviceId).orElseThrow(() -> EntityNotFoundException.run("device", "deviceId", deviceId));
        device.setFirebaseToken(deviceRequestDTO.getFirebaseToken());

        try {
            deviceRepository.save(device);
        } catch (Exception e) {
            throw EntityCreationException.run("device");
        }

        return deviceMapper.mapToDeviceResponseDto(device);
    }
}
