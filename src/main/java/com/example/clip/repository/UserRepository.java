package com.example.clip.repository;

import com.example.clip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findByPaymentsIsNotNull();

    Set<User> findByIdIn(List<Long> userIdList);

    Set<User> findByDisbursementsIsNotNull();

    User findByIdEquals(Long id);
}
