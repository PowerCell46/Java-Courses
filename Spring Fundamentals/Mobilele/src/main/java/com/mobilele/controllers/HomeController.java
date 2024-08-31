package com.mobilele.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("")
    public ModelAndView homeView() {
        return new ModelAndView("index");
    }
}
