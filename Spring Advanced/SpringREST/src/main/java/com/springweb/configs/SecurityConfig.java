//package com.springweb.configs;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(
//                        // Setup which URLs are available to who
//                        authorizeRequests ->
//                                authorizeRequests
//                                        // all static resources
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        // some recourses - for all users
//                                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
//                                        // all other URLs - for authenticated only
//                                        .anyRequest().authenticated()
//                )
////                .formLogin(formLogin ->
////                        formLogin.loginPage("/users/login")
////                                .usernameParameter("username")
////                                .passwordParameter("password")
////                                .defaultSuccessUrl("/")
////                                .failureForwardUrl("/users/login-error"))
//                .logout(
//                        logout ->
//                                logout.logoutUrl("/users/logout")
//                                        .logoutSuccessUrl("/")
//                                        .invalidateHttpSession(true)
//                )
//                .build()
//    }
//}
