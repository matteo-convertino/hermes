package com.convertino.hermesspringapi.repository;

import com.convertino.hermesspringapi.model.Group;
import com.convertino.hermesspringapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByUser(User user);

    Optional<Group> findByGroupId(long groupId);

    void deleteByGroupId(long groupId);
}
