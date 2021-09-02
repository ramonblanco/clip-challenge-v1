package com.example.clip.controller;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.repository.UserRepository;
import com.example.clip.util.ListUtil;
import com.example.clip.util.PaymentStatus;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/clip/populate")
public class PopulateController {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PopulateController(UserRepository userRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public ResponseEntity<Void> populateDb() {
        Faker faker = new Faker();
        Random userRandom = new Random();
        int limit = 5;
        Random paymentRandom = new Random();
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            userList.add(User.builder().userName(faker.name().fullName()).build());
        }
        log.info("Persisting {} new Users", userList.size());
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
        log.info("Persisting {} new Payments", paymentList.size());
        paymentRepository.saveAll(paymentList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
