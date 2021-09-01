package com.example.clip.repository;

import com.example.clip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByPaymentsIsNotNull();
}
