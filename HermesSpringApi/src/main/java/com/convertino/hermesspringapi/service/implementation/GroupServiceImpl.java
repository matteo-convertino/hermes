package com.convertino.hermesspringapi.service.implementation;

import com.convertino.hermesspringapi.config.HermesDockerApiClient;
import com.convertino.hermesspringapi.config.HermesPythonApiClient;
import com.convertino.hermesspringapi.dto.docker_api.UpdateGroupsDTO;
import com.convertino.hermesspringapi.dto.request.GroupRequestDTO;
import com.convertino.hermesspringapi.dto.response.GroupResponseDTO;
import com.convertino.hermesspringapi.exceptions.EntityCreationException;
import com.convertino.hermesspringapi.exceptions.EntityDeletionException;
import com.convertino.hermesspringapi.exceptions.EntityDuplicateException;
import com.convertino.hermesspringapi.exceptions.EntityNotFoundException;
import com.convertino.hermesspringapi.mapper.GroupMapper;
import com.convertino.hermesspringapi.model.Group;
import com.convertino.hermesspringapi.model.User;
import com.convertino.hermesspringapi.repository.GroupRepository;
import com.convertino.hermesspringapi.service.definition.GroupService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final HermesDockerApiClient hermesDockerApiClient;
    private final HermesPythonApiClient hermesPythonApiClient;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper, HermesDockerApiClient hermesDockerApiClient, HermesPythonApiClient hermesPythonApiClient) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.hermesDockerApiClient = hermesDockerApiClient;
        this.hermesPythonApiClient = hermesPythonApiClient;
    }

    private void updateGroups(User user, String deviceId) {
        List<Group> groups = groupRepository.findAllByUser(user);

        UpdateGroupsDTO updateGroupsDTO = new UpdateGroupsDTO();
        updateGroupsDTO.setPhoneNumber(user.getPhoneNumber());
        updateGroupsDTO.setUserId(user.getId());
        updateGroupsDTO.setDeviceId(deviceId);
        updateGroupsDTO.setGroups(groups.stream().map(Group::getGroupId).toList());

        hermesDockerApiClient.sendPost("/update-groups", updateGroupsDTO);
    }

    @Override
    public GroupResponseDTO delete(GroupRequestDTO groupRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String deviceId = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Group group = groupRepository.findByGroupId(groupRequestDto.getGroupId()).orElseThrow(() -> EntityNotFoundException.run("group", "groupId", groupRequestDto.getGroupId()));

        try {
            groupRepository.deleteByGroupId(groupRequestDto.getGroupId());
        } catch (Exception e) {
            throw EntityDeletionException.run("group", "groupId", groupRequestDto.getGroupId());
        }

        updateGroups(user, deviceId);

        return groupMapper.mapToGroupResponseDto(group);
    }

    @Override
    public GroupResponseDTO save(GroupRequestDTO groupRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String deviceId = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Group group = groupMapper.mapToGroup(groupRequestDto);

        group.setUser(user);

        try {
            groupRepository.save(group);
        } catch (DataIntegrityViolationException e) {
            throw EntityDuplicateException.run("group", "groupId", group.getGroupId());
        } catch (Exception e) {
            throw EntityCreationException.run("group");
        }

        updateGroups(user, deviceId);

        return groupMapper.mapToGroupResponseDto(group);
    }

    @Override
    public ResponseEntity<String> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String deviceId = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        String queryParams = "phone_number=" + user.getPhoneNumber() + "&" + "device_id=" + deviceId;
        return hermesPythonApiClient.sendGet("/get-groups", queryParams);
    }

    @Override
    public List<GroupResponseDTO> getAllActive() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return groupMapper.mapToGroupResponseDto(groupRepository.findAllByUser(user));
    }
}
