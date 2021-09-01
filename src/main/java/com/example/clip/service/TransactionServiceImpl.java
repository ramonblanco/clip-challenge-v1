package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.request.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    private final PaymentRepository paymentRepository;

    @Autowired
    public TransactionServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        User user = payment.getUser();
        if (user == null) {
            user = new User();
            user.setId(paymentRequest.getUserId());
            payment.setUser(user);
        }
        Payment save = paymentRepository.save(payment);
        log.info("Payload Created Successfully");
        return save;

    }
}
