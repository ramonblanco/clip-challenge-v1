package com.example.clip.controller;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.repository.UserRepository;
import com.example.clip.request.UserRequest;
import com.example.clip.service.UserService;
import com.example.clip.util.ListUtil;
import com.example.clip.util.PaymentStatus;
import com.github.javafaker.Faker;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/clip/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<User>> retrieveUsers(@RequestParam(required = false) Boolean withPayments,
                                                    @RequestParam(required = false, defaultValue = "0") String pageNumber,
                                                    @RequestParam(required = false, defaultValue = "10") String pageSize) {
        return new ResponseEntity<>(userService.retrieveUsers(withPayments, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/populate")
    public ResponseEntity<Void> populateDb() {
        Faker faker = new Faker();
        Random userRandom = new Random();
        int limit = userRandom.nextInt(49) + 1;
        Random paymentRandom = new Random();
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            userList.add(User.builder().userName(faker.name().fullName()).build());
        }
        List<User> persistedUserList = ListUtil.getListFromIterable(userRepository.saveAll(userList));

        List<Payment> paymentList = new ArrayList<>();
        for (User persistedUser : persistedUserList) {
            for (int j = 0; j < 3; j++) {
                Payment payment =
                        Payment.builder().amount(new BigDecimal(paymentRandom.nextInt(99) + 1)).status(PaymentStatus.NEW.name()).build();
                payment.setUser(User.builder().id(persistedUser.getId()).build());
                paymentList.add(payment);
            }
        }
        paymentRepository.saveAll(paymentList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
