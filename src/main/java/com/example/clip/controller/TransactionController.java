package com.example.clip.controller;


import com.example.clip.model.Payment;
import com.example.clip.request.PaymentRequest;
import com.example.clip.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clip.util.MiscellaneousUtil.UNEXPECTED_MSG_SERVICE;


@Slf4j
@RestController
@RequestMapping("/api/clip/payments")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ApiOperation("Return a response with the recently created Payment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created Payment", response = Payment.class),
            @ApiResponse(code = 400, message = "Something is wrong with the request to create a Payment"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<Payment> createUser(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(transactionService.createPayment(paymentRequest), HttpStatus.CREATED);
    }


}
