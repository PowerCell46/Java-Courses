package com.springweb.controllers;

import com.springweb.models.DTOs.UserRequestDTO;
import com.springweb.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<UserRequestDTO> getUserById( // ?id=10
            @RequestParam int id
    ) {
        System.out.println(id);

        return ResponseEntity.ok(userService.getUserById(id));
    }
}
