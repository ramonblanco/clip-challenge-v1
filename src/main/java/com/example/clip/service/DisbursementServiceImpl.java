package com.example.clip.service;

import com.example.clip.model.Disbursement;
import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.DisbursementRepository;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.repository.UserRepository;
import com.example.clip.util.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DisbursementServiceImpl implements DisbursementService {

    private static final BigDecimal FEE_PERCENTAGE = new BigDecimal("3.5");
    private static final BigDecimal WHOLE_PERCENTAGE = new BigDecimal("100");

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final PaymentRepository paymentRepository;
    private final DisbursementRepository disbursementRepository;
    private final UserRepository userRepository;

    @Autowired
    public DisbursementServiceImpl(PaymentRepository paymentRepository, DisbursementRepository disbursementRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.disbursementRepository = disbursementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> processAllDisbursements() {
        log.info("Processing all disbursements...");
        List<Payment> newStatusPaymentList = paymentRepository.findByStatusEquals(PaymentStatus.NEW.name());
        List<Disbursement> disbursementList = new ArrayList<>();
        Set<Long> userIdList = new HashSet<>();
        for (Payment newStatusPayment : newStatusPaymentList) {
            long userId = newStatusPayment.getUser().getId();
            userIdList.add(userId);
            BigDecimal originalPaymentAmount = newStatusPayment.getAmount();
            BigDecimal disbursementAmount =
                    FEE_PERCENTAGE.divide(WHOLE_PERCENTAGE, MathContext.UNLIMITED)
                            .multiply(originalPaymentAmount)
                            .setScale(2, RoundingMode.FLOOR);
            Disbursement disbursement = Disbursement.builder()
                    .user(User.builder()
                            .id(userId)
                            .build())
                    .amount(disbursementAmount)
                    .payment(Payment.builder()
                            .id(newStatusPayment.getId())
                            .build()).build();
            disbursementList.add(disbursement);
            BigDecimal newPaymentAmount = originalPaymentAmount.subtract(disbursementAmount).setScale(2, RoundingMode.FLOOR);
            newStatusPayment.setAmount(newPaymentAmount);
            newStatusPayment.setStatus(PaymentStatus.PROCESSED.name());
        }
        log.info("Applying disbursement for {} Users on {} Payments", userIdList.size(), newStatusPaymentList.size());
        paymentRepository.saveAll(newStatusPaymentList);
        disbursementRepository.saveAll(disbursementList);
        return userRepository.findByIdIn(userIdList);
    }

}
