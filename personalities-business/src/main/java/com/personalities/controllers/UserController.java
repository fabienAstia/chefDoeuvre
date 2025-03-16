package com.personalities.controllers;

import com.personalities.dtos.UserAuthenticate;
import com.personalities.dtos.UserCreate;
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
    public void create(@Valid @RequestBody UserCreate inputs) {
        userService.create(inputs);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public Object authenticate(@Valid @RequestBody UserAuthenticate inputs) {
        return userService.authenticate(inputs);
    }
}


