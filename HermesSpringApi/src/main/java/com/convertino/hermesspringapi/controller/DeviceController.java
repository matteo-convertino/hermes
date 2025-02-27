package com.convertino.hermesspringapi.controller;

import com.convertino.hermesspringapi.dto.request.DeviceRequestDTO;
import com.convertino.hermesspringapi.dto.response.DeviceResponseDTO;
import com.convertino.hermesspringapi.service.definition.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PutMapping("/update-firebase-token")
    public ResponseEntity<DeviceResponseDTO> updateFirebaseToken(@Valid @RequestBody DeviceRequestDTO deviceRequestDTO) {
        return ResponseEntity.ok(deviceService.update(deviceRequestDTO));
    }
}
