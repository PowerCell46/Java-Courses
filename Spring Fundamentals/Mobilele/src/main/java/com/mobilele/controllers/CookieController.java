package com.mobilele.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CookieController {

    @GetMapping("/cookies")
    public String cookies(
            @CookieValue(name = "lang", defaultValue = "English") String lang,
            Model model
    ) {

        model.addAttribute("language", lang);

        return "cookies";
    }

    @PostMapping("/cookies")
    public String cookiesHandler(@RequestParam("language") String lang, HttpServletResponse response) {
        Cookie langCookie = new Cookie("lang", lang);
        response.addCookie(langCookie);

        return "redirect:/cookies";
    }
}
