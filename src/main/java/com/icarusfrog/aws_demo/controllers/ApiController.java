package com.icarusfrog.aws_demo.controllers;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @Timed(value = "api.ping")
    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }
}
