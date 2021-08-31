package com.example.clip.controller;


import com.example.clip.model.Payment;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.request.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PersistenceException;


@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

    @Autowired
    PaymentRepository paymentRepository;


    @PostMapping ("/createPayload")
    public ResponseEntity create(@RequestBody PaymentRequest paymentRequest) {

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setUserId(paymentRequest.getUserId());

        try {
            paymentRepository.save(payment);
            log.info("Payload Created Successfully");
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
