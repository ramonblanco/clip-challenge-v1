package com.example.clip.service;

import com.example.clip.model.User;
import com.example.clip.request.UserRequest;

import java.util.List;

public interface UserService {
    User createUser(UserRequest userRequest);

    List<User> retrieveUsers(Boolean withPayments);
}
