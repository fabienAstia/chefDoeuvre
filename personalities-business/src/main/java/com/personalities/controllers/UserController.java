package com.personalities.controllers;

import com.personalities.dto.UserAuthenticate;
import com.personalities.dto.UserCreate;
import com.personalities.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody UserCreate inputs) {
        return userService.create(inputs);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public Object authenticate(@Valid @RequestBody UserAuthenticate inputs) {
        return userService.authenticate(inputs);
    }
}


