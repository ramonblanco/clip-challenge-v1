package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.repository.UserRepository;
import com.example.clip.response.PopulationResponse;
import com.example.clip.util.MiscellaneousUtil;
import com.example.clip.util.PaymentStatus;
import com.example.clip.util.PopulationUtil;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PopulateServiceImpl implements PopulateService {

    private static final int DEFAULT_NUMBER_OF_USERS = 10;
    private static final int DEFAULT_NUMBER_OF_PAYMENTS_PER_USER = 5;
    private static final int DEFAULT_MAXIMUM_PAYMENT_AMOUNT = 100;

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final Faker faker;

    @Autowired
    public PopulateServiceImpl(UserRepository userRepository, PaymentRepository paymentRepository, Faker faker) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.faker = faker;
    }

    @Override
    public List<User> createUsers(Integer numberOfUsersToCreate) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < (numberOfUsersToCreate == null ? DEFAULT_NUMBER_OF_USERS : MiscellaneousUtil.getIntegerValueFromParamOrFallback(numberOfUsersToCreate, DEFAULT_NUMBER_OF_USERS)); i++) {
            userList.add(User.builder().userName(faker.name().fullName()).build());
        }
        log.info("Persisting {} new Users", userList.size());
        return MiscellaneousUtil.getListFromIterable(userRepository.saveAll(userList));
    }

    @Override
    public List<Payment> createPayments(Integer numberOfPaymentsPerUserToCreate,
                                        Integer maximumAllowedAmountPerPayment, Optional<List<User>> optionalUserList) {

        int paymentsToCreate = MiscellaneousUtil.getIntegerValueFromParamOrFallback(numberOfPaymentsPerUserToCreate,
                DEFAULT_NUMBER_OF_PAYMENTS_PER_USER);
        int maximumPaymentAmount = MiscellaneousUtil.getIntegerValueFromParamOrFallback(maximumAllowedAmountPerPayment,
                DEFAULT_MAXIMUM_PAYMENT_AMOUNT);

        log.info("{} payments per each user will be inserted", paymentsToCreate);


        List<Payment> paymentList;
        paymentList = optionalUserList.map(users -> getPaymentListToPersist(users, paymentsToCreate, maximumPaymentAmount)).orElseGet(() -> getPaymentListToPersist(userRepository.findAll(), paymentsToCreate, maximumPaymentAmount));
        log.info("Persisting {} new Payments with maximum permited payment amount: {}", paymentList.size(), maximumPaymentAmount);
        return paymentRepository.saveAll(paymentList);
    }

    @Override
    public PopulationResponse createUsersWithPayments(Integer numberOfUsersToCreate,
                                                      Integer numberOfPaymentsPerUserToCreate,
                                                      Integer maximumAllowedAmountPerPayment) {
        List<User> persistedUsers = createUsers(numberOfUsersToCreate);
        List<Payment> payments = createPayments(numberOfPaymentsPerUserToCreate, maximumAllowedAmountPerPayment, Optional.of(persistedUsers));
        return PopulationResponse.builder().createdUsers(persistedUsers.size()).createdPaymentsPerUser(payments.size()).build();
    }

    private List<Payment> getPaymentListToPersist(Iterable<User> existingUsers, int paymentsToCreate, int maximumPaymentAmount) {
        List<Payment> paymentList = new ArrayList<>();
        for (User existingUser : existingUsers) {
            for (int j = 0; j < paymentsToCreate; j++) {
                Payment payment =
                        Payment.builder().amount(new BigDecimal(PopulationUtil.amountPerPayment(maximumPaymentAmount)))
                                .status(PaymentStatus.NEW.name()).build();
                payment.setUser(User.builder().id(existingUser.getId()).build());
                paymentList.add(payment);
            }
        }
        return paymentList;
    }

}
