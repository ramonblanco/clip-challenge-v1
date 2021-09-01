package com.example.clip.controller;

import com.example.clip.model.User;
import com.example.clip.service.DisbursementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clip/disbursements")
public class DisbursementController {

    private final DisbursementService disbursementService;

    @Autowired
    public DisbursementController(DisbursementService disbursementService) {
        this.disbursementService = disbursementService;
    }

    @GetMapping
    public ResponseEntity<List<User>> triggerBulkDisbursement() {
        return new ResponseEntity<>(disbursementService.processAllDisbursements(), HttpStatus.OK);
    }
}
