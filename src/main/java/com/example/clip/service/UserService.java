package com.example.clip.service;

import com.example.clip.model.User;
import com.example.clip.request.UserRequest;

public interface UserService {
    User createUser(UserRequest userRequest);
}
