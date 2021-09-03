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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/clip/populate")
@Validated
public class PopulateController {

    private static final int DEFAULT_NUMBER_OF_USERS = 10;
    private static final int DEFAULT_NUMBER_OF_PAYMENTS_PER_USER = 5;
    private static final int DEFAULT_MAXIMUM_PAYMENT_AMOUNT = 100;

    private static final String MINIMUM_NUMBER_OF_USERS_MESSAGE = "The minimum number of new users to create must " +
            "be at least 1";
    private static final String MAXIMUM_NUMBER_OF_USERS_MESSAGE =  "The maximum number of new users to create can " +
            "be maximum 50";
    private static final String MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE = "The minimum number of payments per user to " +
            "create must be at least 1";
    private static final String MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE =  "The maximum number of payments per user to " +
            "create can be maximum 50";
    private static final String MINIMUM_PAYMENT_AMOUNT_MESSAGE = "The minimum payment amount must be at least 1 pesos";
    private static final String MAXIMUM_PAYMENT_AMOUNT_MESSAGE =  "The maximum payment amount can be maximum 100 pesos";

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PopulateController(UserRepository userRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public ResponseEntity<Void> populateDb(@RequestParam(required = false)
                                           @Min(value = 1, message = MINIMUM_NUMBER_OF_USERS_MESSAGE)
                                           @Max(value = 50, message = MAXIMUM_NUMBER_OF_USERS_MESSAGE)
                                                   Integer generateNewUsers,
                                           @RequestParam(required = false)
                                           @Min(value = 1, message = MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                           @Max(value = 10, message = MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                   Integer paymentsPerUser,
                                           @RequestParam(required = false)
                                               @Min(value = 1, message = MINIMUM_PAYMENT_AMOUNT_MESSAGE)
                                               @Max(value = 10, message = MAXIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                       Integer maximumPaymentAmount) {
        Faker faker = new Faker();
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < (generateNewUsers == null ? DEFAULT_NUMBER_OF_USERS : generateNewUsers); i++) {
            userList.add(User.builder().userName(faker.name().fullName()).build());
        }
        log.info("Persisting {} new Users", userList.size());
        List<User> persistedUserList = ListUtil.getListFromIterable(userRepository.saveAll(userList));

        if (paymentsPerUser == null) {
            paymentsPerUser = DEFAULT_NUMBER_OF_PAYMENTS_PER_USER;
        }
        log.info("{} payments per each user will be inserted", paymentsPerUser);
        List<Payment> paymentList = new ArrayList<>();
        if (maximumPaymentAmount == null) {
            maximumPaymentAmount = DEFAULT_MAXIMUM_PAYMENT_AMOUNT;
        }
        log.info("Maximum payment amount: {}", maximumPaymentAmount);
        for (User persistedUser : persistedUserList) {
            for (int j = 0; j < paymentsPerUser; j++) {
                Payment payment =
                        Payment.builder().amount(new BigDecimal(amountPerPayment(maximumPaymentAmount)))
                                .status(PaymentStatus.NEW.name()).build();
                payment.setUser(User.builder().id(persistedUser.getId()).build());
                paymentList.add(payment);
            }
        }
        log.info("Persisting {} new Payments", paymentList.size());
        paymentRepository.saveAll(paymentList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private int amountPerPayment(Integer maximumPaymentAmount) {

        Random randomPaymentAmount = new Random();
        int paymentAmount = randomPaymentAmount.nextInt(maximumPaymentAmount);
        if (paymentAmount == 0) {
            return 1;
        }
        return paymentAmount;
    }
}
