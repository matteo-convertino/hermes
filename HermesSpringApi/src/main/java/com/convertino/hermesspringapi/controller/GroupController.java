package com.convertino.hermesspringapi.controller;

import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.service.definition.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> add(@Valid @RequestBody GroupRequestDTO groupRequestDto) {
        return ResponseEntity.ok(groupService.save(groupRequestDto));
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        return groupService.getAll();
    }

    @GetMapping("/active")
    public ResponseEntity<List<GroupResponseDTO>> getAllActive() {
        return ResponseEntity.ok(groupService.getAllActive());
    }

    @DeleteMapping
    public ResponseEntity<GroupResponseDTO> delete(@Valid @RequestBody GroupRequestDTO groupRequestDto) {
        return ResponseEntity.ok(groupService.delete(groupRequestDto));
    }
}
