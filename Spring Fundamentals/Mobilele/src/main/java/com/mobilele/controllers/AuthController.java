package com.mobilele.controllers;

import com.mobilele.models.DTOs.UserLoginDTO;
import com.mobilele.models.DTOs.UserRegisterDTO;
import com.mobilele.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @ModelAttribute("registerDTO")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("auth-login");
    }

    @PostMapping("/login")
    public ModelAndView login(UserLoginDTO userLoginDTO) {

        userService.login(userLoginDTO);

        return new ModelAndView("redirect:/");
    }

//    @PostMapping("/login")
//    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);
//        return new ModelAndView("redirect:/");
//    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        return new ModelAndView("auth-register");
    }

    @PostMapping("/register")
    public ModelAndView register(UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);

        return new ModelAndView("redirect:/");
    }
}
