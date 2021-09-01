package com.example.clip.service;

import com.example.clip.model.User;
import com.example.clip.repository.UserRepository;
import com.example.clip.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserRequest userRequest) {
        User user = User.builder().userName(userRequest.getUserName()).build();
        User savedUser = userRepository.save(user);
        log.info("Successfully created user with username: {} and id: {}", savedUser.getUserName(), savedUser.getId());
        return savedUser;
    }
}
