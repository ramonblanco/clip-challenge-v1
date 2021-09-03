package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.response.PopulationResponse;

import java.util.List;
import java.util.Optional;

public interface PopulateService {

    List<User> createUsers(Integer numberOfUsersToCreate);

    List<Payment> createPayments(Integer numberOfPaymentsPerUserToCreate, Integer maximumAllowedAmountPerPayment,
                                 Optional<List<User>> optionalUserList);

    PopulationResponse createUsersWithPayments(Integer numberOfUsersToCreate, Integer numberOfPaymentsPerUserToCreate
            , Integer maximumAllowedAmountPerPayment);
}
