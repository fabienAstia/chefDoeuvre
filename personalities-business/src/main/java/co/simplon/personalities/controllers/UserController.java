package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.UserAuthenticate;
import co.simplon.personalities.dtos.UserCreate;
import co.simplon.personalities.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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


