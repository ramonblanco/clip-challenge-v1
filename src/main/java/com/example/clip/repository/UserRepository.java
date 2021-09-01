package com.example.clip.repository;

import com.example.clip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findByPaymentsIsNotNull();
}
