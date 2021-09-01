package com.example.clip.controller;

import com.example.clip.model.User;
import com.example.clip.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {

        return new ResponseEntity<>(new User(), HttpStatus.CREATED);
    }
}
