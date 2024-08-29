package bg.softuni.mvcworkshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class AuthenticationController {

//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

    @GetMapping("/users/login")
    public String loginPage() {
        return "user/login";
    }

//    @PostMapping("/users/login")
//        public String loginHandler(@Valid UserLoginDTO userLoginDTO, BindingResult bindingResult) {
//            if (bindingResult.hasErrors()) {
//                // Return to the login page with errors
//                return "user/login";
//            }
//
//            try {
//                // Create a UsernamePasswordAuthenticationToken using the provided username and password
//                Authentication auth = new UsernamePasswordAuthenticationToken(
//                    userLoginDTO.getUsername(),
//                    userLoginDTO.getPassword()
//                );
//
//                // Authenticate the user using the AuthenticationManager
//                Authentication authenticated = authenticationManager.authenticate(auth);
//
//                // If authentication is successful, set the authentication in the SecurityContext
//                SecurityContextHolder.getContext().setAuthentication(authenticated);
//
//                // Redirect to the home page after successful login
//                return "redirect:/home";
//
//            } catch (AuthenticationException e) {
//                // If authentication fails, redirect back to the login page with an error
//                return "redirect:/users/login?error=true";
//            }
//        }

    @GetMapping("/users/register")
    public String registerPage() {
        return "user/register";
    }

//    @PostMapping("/users/register")
//    public String registerHandler(UserRegisterDTO userRegisterDTO) {
//        User user = new User();
//        user.setEmail(userRegisterDTO.getEmail());
//        user.setUsername(userRegisterDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//        user.setRole("ROLE_USER");
//
//        userService.saveUser(user);
//        return "redirect:/users/login";
//    }

}
