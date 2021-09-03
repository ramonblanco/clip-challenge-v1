package com.example.clip.controller;

import com.example.clip.response.ReportResponse;
import com.example.clip.service.ReportService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static com.example.clip.util.MiscellaneousUtil.UNEXPECTED_MSG_SERVICE;

@RestController
@RequestMapping("/api/clip/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{userId}")
    @ApiOperation("Return a report response with information about the user, their payments and disbursements")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved report data", response = ReportResponse.class),
            @ApiResponse(code = 400, message = "Something is wrong with the request to retrieve the Report"),
            @ApiResponse(code = 500, message = UNEXPECTED_MSG_SERVICE)})
    public ResponseEntity<ReportResponse> getReportResponseByUserId(@PathVariable
                                                                    @NotNull(message = "userId is required")
                                                                            Long userId) {
        return new ResponseEntity<>(reportService.getReportServiceByUserId(userId), HttpStatus.OK);
    }

}
