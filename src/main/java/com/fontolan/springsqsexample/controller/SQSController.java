package com.fontolan.springsqsexample.controller;

import com.fontolan.springsqsexample.service.SQSService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sqs")
public class SQSController {
    private final SQSService sqsService;

    public SQSController(SQSService sqsService) {
        this.sqsService = sqsService;
    }

    @PostMapping
    public void sendMessage(@RequestParam String message) {
        sqsService.sendMessage(message);
    }
}
