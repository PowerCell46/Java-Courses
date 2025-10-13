package com.springweb.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequiredArgsConstructor
public class DefaultController {


    @GetMapping("/")
    public String getHomePage(HttpServletRequest request) {
        return "Hello there!";
    }
}
