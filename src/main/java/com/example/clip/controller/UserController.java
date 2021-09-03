package com.example.clip.controller;

import com.example.clip.model.User;
import com.example.clip.request.UserRequest;
import com.example.clip.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

import static com.example.clip.util.MiscellaneousUtil.UNEXPECTED_MSG_SERVICE;

@RestController
@RequestMapping("/api/clip/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation("Return a response with the recently created User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created User", response = User.class),
            @ApiResponse(code = 400, message = "Something is wrong with the request to create a User"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation("Return a paginated response with existing Users where we can choose if we want to retrieve users " +
            "with or without payments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Users", response = User.class),
            @ApiResponse(code = 400, message = "Something is wrong with the request retrieved existing users"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<Page<User>> retrieveUsers(@RequestParam(required = false) Boolean withPayments,
                                                    @RequestParam(required = false)
                                                    @Min(value = 0,message = "Minimum page value is 0")
                                                            Integer pageNumber,
                                                    @Min(value = 0,message = "Minimum page size is 1")
                                                    @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(userService.retrieveUsers(withPayments, pageNumber, pageSize), HttpStatus.OK);
    }
}
