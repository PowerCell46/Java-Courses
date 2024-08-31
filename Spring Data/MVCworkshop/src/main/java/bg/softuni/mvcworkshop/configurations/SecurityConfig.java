//package bg.softuni.mvcworkshop.configurations;
//
//import bg.softuni.mvcworkshop.services.interfaces.UserService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
//public class SecurityConfig {
//    private UserService myUserDetailsService;
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//            .authorizeHttpRequests(request -> {
//                request
//                    .requestMatchers("/css/*", "/js/*").permitAll()
//                    .requestMatchers("/", "/users/login", "/users/register", "/login").anonymous()
//                    .anyRequest().authenticated();
//        })
//        .formLogin(login -> {
//            login.loginPage("/users/login")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/users/login?error");
//        })
//         .userDetailsService(myUserDetailsService);
//
//        return http.build();
//    }
//}