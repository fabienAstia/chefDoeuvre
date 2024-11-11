package com.personalities.auth;

import com.personalities.validators.UserCreateUniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank @Size(max = 20) String username,

        @Pattern(regexp = "^(?=.{1,64}@)[\\w-]+([.-]?[\\w-]+)*@(?=.{4,252}$)[\\w-]+([.-]?[\\w-]+)*(\\.[a-zA-Z]{2,4})+$",
                message = "Adresse email invalide")
        @UserCreateUniqueEmail String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])\\S{8,16}$ ",
                message = "Password invalide")
        String password
){}

