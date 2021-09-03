package com.example.clip.controller;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.response.PopulationResponse;
import com.example.clip.service.PopulateService;
import com.example.clip.util.PopulationUtil;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/clip/populate")
@Validated
public class PopulateController {

    private final PopulateService populateService;

    @Autowired
    public PopulateController(PopulateService populateService) {
        this.populateService = populateService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> createUsers(@RequestParam(required = false)
                                                      @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_USERS_MESSAGE)
                                                      @Max(value = 50, message = PopulationUtil.MAXIMUM_NUMBER_OF_USERS_MESSAGE)
                                                              Integer generateNewUsers) {
        return new ResponseEntity<>(populateService.createUsers(generateNewUsers), HttpStatus.OK);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> createPayments(@RequestParam(required = false)
                                                                     @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                                     @Max(value = 10, message = PopulationUtil.MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                                             Integer paymentsPerUser,
                                                        @RequestParam(required = false)
                                                                     @Min(value = 1, message = PopulationUtil.MINIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                                     @Max(value = 100, message = PopulationUtil.MAXIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                                             Integer maximumPaymentAmount) {
        return new ResponseEntity<>(populateService.createPayments(paymentsPerUser, maximumPaymentAmount, Optional.empty()),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PopulationResponse> createUsersAndPayments(@RequestParam(required = false)
                                                         @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_USERS_MESSAGE)
                                                         @Max(value = 50, message = PopulationUtil.MAXIMUM_NUMBER_OF_USERS_MESSAGE)
                                                                 Integer generateNewUsers,
                                                                     @RequestParam(required = false)
                                                         @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                         @Max(value = 10, message = PopulationUtil.MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                                 Integer paymentsPerUser,
                                                                     @RequestParam(required = false)
                                                         @Min(value = 1, message = PopulationUtil.MINIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                         @Max(value = 100, message = PopulationUtil.MAXIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                                 Integer maximumPaymentAmount) {
        return new ResponseEntity<>(populateService.createUsersWithPayments(generateNewUsers, paymentsPerUser,
                maximumPaymentAmount), HttpStatus.OK);
    }

}
