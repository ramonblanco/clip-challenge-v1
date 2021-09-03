package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.repository.DisbursementRepository;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DisbursementServiceTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private DisbursementRepository disbursementRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DisbursementServiceImpl disbursementService;

    private List<Payment> newStatusPaymentList;

    @Before
    public void setUp() throws Exception {
        newStatusPaymentList = MAPPER.readValue(new File("src/test/resources/newStatusPaymentList.json"), new TypeReference<>() {
        });

        int userId = 1;
        int counter = 1;
        for (Payment payment : newStatusPaymentList) {
            for (int j = 0; j < 3; j++) {
                payment.setUser(User.builder().id(userId).build());
            }
            if (counter == 3) {
                userId++;
                counter = 0;
            }
            counter++;
        }
    }

    @Test
    public void processAllDisbursements_Happy_Path_Test() {
        when(paymentRepository.findByStatusEquals(anyString())).thenReturn(newStatusPaymentList);
        disbursementService.processAllDisbursements();
        verify(paymentRepository, times(1)).findByStatusEquals(anyString());
        verify(paymentRepository, times(1)).saveAll(anyIterable());
        verify(disbursementRepository, times(1)).saveAll(anyIterable());
        verify(userRepository, times(1)).findByIdIn(anySet());
    }
}