package com.example.clip.service;

import com.example.clip.error.GenericException;
import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.request.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

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
        if (user != null) {
                Payment save = paymentRepository.save(payment);
                LOGGER.info("Payload Created Successfully");
                return save;

        } else {
            throw new GenericException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("There's no user with the user id: %d", paymentRequest.getUserId()));
        }

    }
}
