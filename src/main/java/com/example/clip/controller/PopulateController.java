package com.example.clip.controller;

import com.example.clip.model.Payment;
import com.example.clip.model.User;
import com.example.clip.response.PopulationResponse;
import com.example.clip.service.PopulateService;
import com.example.clip.util.PopulationUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

import static com.example.clip.util.MiscellaneousUtil.UNEXPECTED_MSG_SERVICE;

@Slf4j
@RestController
@RequestMapping("/api/clip/populate")
@Validated
public class PopulateController {

    private final PopulateService populateService;

    @Autowired
    public PopulateController(PopulateService populateService) {
        this.populateService = populateService;
    }

    @GetMapping("/users")
    @ApiOperation("Return a list of Users after they were created")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully response with list of Users after they were created",
                    response = User.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Something is wrong with the request to create Users"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<List<User>> createUsers(@RequestParam(required = false)
                                                  @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_USERS_MESSAGE)
                                                  @Max(value = 50, message = PopulationUtil.MAXIMUM_NUMBER_OF_USERS_MESSAGE)
                                                  @ApiParam(name = "generateNewUsers",
                                                          type = "Integer",
                                                          value = "Number of new Users to create",
                                                          example = "10"
                                                  ) Integer generateNewUsers) {
        return new ResponseEntity<>(populateService.createUsers(generateNewUsers), HttpStatus.OK);
    }

    @GetMapping("/payments")
    @ApiOperation("Return a list of Payments after they were created")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully response with list of Payments after they were created",
                    response = Payment.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Something is wrong with the request to create Payments"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<List<Payment>> createPayments(@RequestParam(required = false)
                                                        @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                        @Max(value = 10, message = PopulationUtil.MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                        @ApiParam(name = "paymentsPerUser",
                                                                type = "Integer",
                                                                value = "Number of new Payments per User" +
                                                                        " to Create",
                                                                example = "5"
                                                        )
                                                                Integer paymentsPerUser,
                                                        @RequestParam(required = false)
                                                        @Min(value = 1, message = PopulationUtil.MINIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                        @Max(value = 100, message = PopulationUtil.MAXIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                        @ApiParam(name = "maximumPaymentAmount",
                                                                type = "Integer",
                                                                value = "Number of maximum Payment " +
                                                                        "Amount that can be set to a" +
                                                                        " payment, this amount is a " +
                                                                        "random Integer between 1 and " +
                                                                        "the maximumPaymentAmount",
                                                                example = "100"
                                                        )
                                                                Integer maximumPaymentAmount) {
        return new ResponseEntity<>(populateService.createPayments(paymentsPerUser, maximumPaymentAmount, Optional.empty()),
                HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Return a Population response object containing the number of users that were created and the " +
            "number of payments that were created per each of those users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully response the information of users and payments per user " +
                    "created",
                    response = PopulationResponse.class),
            @ApiResponse(code = 400, message = "Something is wrong with the request to populate users and payments"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<PopulationResponse> createUsersAndPayments(@RequestParam(required = false)
                                                                     @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_USERS_MESSAGE)
                                                                     @Max(value = 50, message = PopulationUtil.MAXIMUM_NUMBER_OF_USERS_MESSAGE)
                                                                     @ApiParam(name = "generateNewUsers",
                                                                             type = "Integer",
                                                                             value = "Number of new Users to create",
                                                                             example = "10"
                                                                     )
                                                                             Integer generateNewUsers,
                                                                     @RequestParam(required = false)
                                                                     @Min(value = 1, message = PopulationUtil.MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                                     @Max(value = 10, message = PopulationUtil.MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE)
                                                                     @ApiParam(name = "paymentsPerUser",
                                                                             type = "Integer",
                                                                             value = "Number of new Payments per User" +
                                                                                     " to Create",
                                                                             example = "5"
                                                                     )
                                                                             Integer paymentsPerUser,
                                                                     @RequestParam(required = false)
                                                                     @Min(value = 1, message = PopulationUtil.MINIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                                     @Max(value = 100, message = PopulationUtil.MAXIMUM_PAYMENT_AMOUNT_MESSAGE)
                                                                     @ApiParam(name = "maximumPaymentAmount",
                                                                             type = "Integer",
                                                                             value = "Number of maximum Payment " +
                                                                                     "Amount that can be set to a" +
                                                                                     " payment, this amount is a " +
                                                                                     "random Integer between 1 and " +
                                                                                     "the maximumPaymentAmount",
                                                                             example = "100"
                                                                     )
                                                                             Integer maximumPaymentAmount) {
        return new ResponseEntity<>(populateService.createUsersWithPayments(generateNewUsers, paymentsPerUser,
                maximumPaymentAmount), HttpStatus.OK);
    }

}
