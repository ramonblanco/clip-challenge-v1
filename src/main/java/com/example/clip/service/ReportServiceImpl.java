package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.UserRepository;
import com.example.clip.response.ReportResponse;
import com.example.clip.util.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;

    @Autowired
    public ReportServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ReportResponse getReportServiceByUserId(Long userId) {
        User userById = userRepository.findByIdEquals(userId);
        ReportResponse reportResponse = ReportResponse.builder()
                .userId(userId)
                .userName(userById.getUserName())
                .build();
        List<Payment> userPayments = userById.getPayments();
        Map<String, List<Payment>> mapOfPaymentsByPaymentStatus = userPayments.stream().collect(groupingBy(Payment::getStatus));
        List<Payment> flatListOfAllPayments = mapOfPaymentsByPaymentStatus.values().stream().flatMap(List::stream).collect(Collectors.toList());
        reportResponse.setAllPaymentsSumAmount(getSumOfPaymentListAmount(flatListOfAllPayments));
        List<Payment> listOfNewPayments = mapOfPaymentsByPaymentStatus.get(PaymentStatus.NEW.name());
        reportResponse.setNewPaymentsQuantity(listOfNewPayments == null ? 0 : listOfNewPayments.size() );
        reportResponse.setNewPaymentsSumAmount(getSumOfPaymentListAmount(listOfNewPayments));
        return reportResponse;
    }

    private BigDecimal getSumOfPaymentListAmount(List<Payment> paymentListToSum) {
        if (paymentListToSum == null) {
            return BigDecimal.ZERO;
        }
        return paymentListToSum.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
