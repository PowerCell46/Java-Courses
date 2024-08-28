package bg.softuni.mvcworkshop.controllers;

import bg.softuni.mvcworkshop.DTOs.UserLoginDTO;
import bg.softuni.mvcworkshop.DTOs.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {

    @GetMapping("/users/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/users/login")
    public String loginHandler(@Valid UserLoginDTO userLoginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // error handling
        }

        System.out.println(userLoginDTO);
        return "home";
    }

    @GetMapping("/users/register")
    public String registerPage() {
        return "user/register";
    }

    @PostMapping("/users/register")
    public String registerHandler(UserRegisterDTO userRegisterDTO) {
        System.out.println(userRegisterDTO);
        return "home";
    }
}
