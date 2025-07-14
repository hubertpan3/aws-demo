package com.icarusfrog.aws_demo.controllers;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
public class ApiController {

    @Timed(value = "api.ping")
    @GetMapping("/ping")
    public String ping() {
        log.info("ping called");
        return "OK";
    }
}
