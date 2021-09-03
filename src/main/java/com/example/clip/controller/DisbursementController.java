package com.example.clip.controller;

import com.example.clip.model.User;
import com.example.clip.service.DisbursementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.clip.util.MiscellaneousUtil.UNEXPECTED_MSG_SERVICE;

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
    @ApiOperation("Return a list of Users after disbursements were applied to all their payments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully response with list of Users with disbursements applied ", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Something is wrong with the request to retrieve the disbursement respose"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<List<User>> triggerDisbursements() {
        return new ResponseEntity<>(disbursementService.processAllDisbursements(), HttpStatus.OK);
    }
}
