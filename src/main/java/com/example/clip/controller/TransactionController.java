package com.example.clip.controller;


import com.example.clip.model.Payment;
import com.example.clip.request.PaymentRequest;
import com.example.clip.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping ("/createPayload")
    public ResponseEntity<Payment> create(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(transactionService.createPayment(paymentRequest), HttpStatus.CREATED);
    }


}
