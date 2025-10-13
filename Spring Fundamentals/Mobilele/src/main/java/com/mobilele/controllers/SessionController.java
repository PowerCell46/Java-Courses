package com.mobilele.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    @GetMapping("/session")
    public String session(
            HttpSession session,
            Model model
    ) {
        Object lang = session.getAttribute("lang");

        model.addAttribute("language", lang != null ? lang : "English");

        return "sessions";
    }

    @PostMapping("/session")
    public String sessionHandler(@RequestParam("language") String lang, HttpSession session) {

        session.setAttribute("lang", lang);

        return "redirect:/session";
    }
}
