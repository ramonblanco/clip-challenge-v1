package com.example.clip.service;

import com.example.clip.model.User;
import com.example.clip.request.UserRequest;
import org.springframework.data.domain.Page;

public interface UserService {
    User createUser(UserRequest userRequest);

    Page<User> retrieveUsers(Boolean withPayments, Integer pageNumber, Integer pageSize);
}
