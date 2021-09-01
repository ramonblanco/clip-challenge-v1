package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.request.PaymentRequest;

public interface TransactionService {

    Payment createPayment(PaymentRequest paymentRequest);
}
