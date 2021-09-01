package com.example.clip.controller;

import com.example.clip.response.ReportResponse;
import com.example.clip.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clip/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReportResponse> getReportResponseByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(reportService.getReportServiceByUserId(userId), HttpStatus.OK);
    }

}
